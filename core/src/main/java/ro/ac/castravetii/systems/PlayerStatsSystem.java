package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ro.ac.castravetii.Gun;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.components.*;
import ro.ac.castravetii.events.*;

import java.util.ArrayDeque;

/**
 * Acest sistem se va ocupa cu modiicarea stat-urilor player-ului
 * maxHealth, speed, damage, xpGain.
 */
public class PlayerStatsSystem extends EntitySystem {

    private final HealthComponent healthC;
    private final LevelComponent levelC;
    private final PlayerStatsComponent statsC;
    private final MovementComponent movementC;
    private final GameEventQueue queue;
    private final Gun gun;

    public PlayerStatsSystem(GameEventQueue queue, int priority){
        super(priority);
        this.queue = queue;

        Player player = Player.getInstance();

        healthC = player.getHealthComponent();
        levelC = player.getLevelComponent();
        statsC = player.getPlayerStats();
        movementC = player.getMovementComponent();
        gun = player.getGun();
    }

    @Override
    public void update(float delta) {
        ArrayDeque<GameEvent> events = queue.getEvents(AttackEvent.class, PlayerXPGainEvent.class, PlayerEvent.class);

        // Tratez evenimentele legate de player daca exista
        if (!events.isEmpty()) {
            for (GameEvent event : events ){
                switch (event) {
                    case AttackEvent e -> {
                        // Daca nu a fost atacat player-ul, trecem peste
                        if (e.target().getComponent(PlayerComponent.class) == null) continue;

                        healthC.currentHealth -= e.damage();

                        if (healthC.currentHealth <= 0) {
                            healthC.currentHealth = 0;

                            Gdx.app.exit();

                            queue.post(PlayerEvent.died); // Adaug in coada un event ca player-ul a murit
                        }
                        queue.post(UpdateHUDEvent.healthBar);
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

                    default -> throw new IllegalStateException("Unexpected value: " + event);
                }
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
                    break;
                }
            }

        }
    }
}
