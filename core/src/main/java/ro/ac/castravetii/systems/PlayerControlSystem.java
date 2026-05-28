package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ro.ac.castravetii.Gun;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.*;
import ro.ac.castravetii.events.*;
import com.badlogic.gdx.files.FileHandle;
import ro.ac.castravetii.screens.GameOverScreen;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayDeque;

/**
 * Acest sistem se va ocupa cu modiicarea stat-urilor player-ului
 * maxHealth, speed, damage, xpGain.
 */
public class PlayerControlSystem extends EntitySystem {

    private final HealthComponent healthC;
    private final LevelComponent levelC;
    private final PlayerStatsComponent statsC;
    private final MovementComponent movementC;
    private final GameEventQueue queue;
    private final Gun gun;
    private final Game game;

    public PlayerControlSystem(Game game, GameEventQueue queue, int priority){
        super(priority);
        this.queue = queue;
        this.game = game;


        Player player = Player.getInstance();

        healthC = player.getHealthComponent();
        levelC = player.getLevelComponent();
        statsC = player.getPlayerStats();
        movementC = player.getMovementComponent();
        gun = player.getGun();
    }
    //metoda publica pentru testare
    public void saveFinalScore(int finalScore) {
        try {
            FileHandle file = Gdx.files.local("../scores.txt");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String timestamp = dtf.format(LocalDateTime.now());
            file.writeString(timestamp + " - Scor Final: " + finalScore + "\n", true);
            Gdx.app.log("GameSave", "Scorul " + finalScore + " a fost salvat in scores.txt");
        } catch (Exception e) {
            Gdx.app.error("GameSave", "Eroare la salvarea scorului", e);
        }
    }

    @Override
    public void update(float delta) {
        ArrayDeque<GameEvent> events = queue.getEvents(AttackEvent.class, PlayerXPGainEvent.class, PlayerEvent.class, EnemyKilledEvent.class, PlayerHealEvent.class);

        // Tratez evenimentele legate de player daca exista
        if (!events.isEmpty()) {

            for (GameEvent event : events ){
                switch (event) {
                    case AttackEvent e -> {
                        // Daca nu a fost atacat player-ul, trecem peste
                        //if (e.target() instanceof Player) {

                            if (!(e.target() instanceof Entity playerEntity)) continue;
                            if (playerEntity.getComponent(PlayerComponent.class) == null) continue;

                        if (e.target() instanceof Player) {
                            healthC.currentHealth -= e.damage();
                            System.out.println("DEBUG: Jucatorul a luat " + e.damage() + " damage!");

                            TransformComponent playerPos = playerEntity.getComponent(TransformComponent.class);

                            if (e.source() instanceof Entity enemyEntity) {
                                TransformComponent enemyPos = enemyEntity.getComponent(TransformComponent.class);

                                if (playerPos != null && enemyPos != null) {
                                    float dirX = playerPos.position.x - enemyPos.position.x;
                                    float dirY = playerPos.position.y - enemyPos.position.y;

                                    float length = (float) Math.sqrt(dirX * dirX + dirY * dirY);
                                    if (length > 0) {
                                        dirX /= length;
                                        dirY /= length;

                                        float force = 400f;
                                        movementC.knockbackX = dirX * force;
                                        movementC.knockbackY = dirY * force;
                                    }
                                }
                            }



                            //poti pune aici ca player ul e ranit , un sunet...

                            if (healthC.currentHealth <= 0) {
                                healthC.currentHealth = 0;
                                saveFinalScore(statsC.score); // pt salvare scor la moarte
                                //trebuie adaugat un delay intai ca player ul sa moara intai si sa se auda sunetul si dupa sa se inchida
                                //jocul sau varianta aia cu meniul dupa ce maore player sa se deschida un meniu in care ai optiuni de restart,exit...
//                            Services.soundSystem.play("playerDead");
                                System.out.println("DEBUG: Se salveaza scorul!");

                                //Gdx.app.exit();
                                Gdx.app.postRunnable(() -> game.setScreen(new GameOverScreen(game, queue, statsC.score)));

                                queue.post(PlayerEvent.died); // Adaug in coada un event ca player-ul a murit
                            }
                            queue.post(UpdateHUDEvent.healthBar);
                        //}
                    }

                    case PlayerXPGainEvent e -> {
                        levelC.xp += (int) (e.xp() * levelC.xpGain);
                        // Fac level up cat timp am xp-ul necesar si nu am ajuns la nivelul maxim
                        while (levelC.xp >= levelC.levelUpTarget && levelC.level < levelC.maxLevel) {
                            levelC.xp -= levelC.levelUpTarget; // Scad din xp-ul curent valoarea pentru level up
                            levelC.level++;
                            statsC.upgradePoints++;
                            levelC.levelUpTarget = levelC.levelWeight + (int)Math.pow(levelC.level, 2); // setez un nou target pentru level up
                        }
                        queue.post(UpdateHUDEvent.levelBar);
                        queue.post(UpdateHUDEvent.stats);
                    }

                    case EnemyKilledEvent e -> {
                        statsC.score += e.points; // Folosește statsC
                        queue.post(UpdateHUDEvent.stats); // update HUD
                    }

                    default -> throw new IllegalStateException("Unexpected value: " + event);
                }
            }
        }

        if(healthC.currentHealth < healthC.maxHealth){
            if(healthC.regenTimer > 0){
                healthC.regenTimer -= delta;
            }else{
                    healthC.currentHealth += 1;

                    healthC.regenTimer = 1f;
                    if(healthC.currentHealth > healthC.maxHealth){
                        healthC.currentHealth = healthC.maxHealth;
                }
                queue.post(UpdateHUDEvent.healthBar);
            }
        }

        if (statsC.upgradePoints > 0) {

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {

                /* Upgrade gun */
                statsC.strengthLevel++;
                GunComponent gunC = gun.getGunComponent();
                // Upgrade gun damage +10%.
                gunC.damage += (int) (gunC.damage * 0.1);
                // Upgrade gun shooting speed -5%;
                gunC.shotDelay -= gunC.shotDelay * 0.05f;
            } else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {

                /* Update speed */
                statsC.speedLevel++;

                movementC.speed += 8;
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {

                /* Upgrade health */
                statsC.healthLevel++;
                healthC.maxHealth += 20;

            } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {

                /* Upgrade xp gain */
                statsC.xpGainLevel++;
                levelC.xpGain += 1.0f;

            }

            // 8, 9, 10, 11 sunt valorile int pentru numerele 1, 2, 3, 4 de pe tastatura
            for (int key : new int[]{8, 9, 10, 11}) {
                if (Gdx.input.isKeyJustPressed(key)) {
                    statsC.upgradePoints--;
                    queue.post(UpdateHUDEvent.stats);
                    queue.post(UpdateHUDEvent.healthBar);
                    Services.soundSystem.play("powerUp", 1f);
                    break;
                }
            }

        }


    }
}
