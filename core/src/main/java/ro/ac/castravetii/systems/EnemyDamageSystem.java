package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.EntitySystem;
import ro.ac.castravetii.Enemy;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.EnemyComponent;
import ro.ac.castravetii.components.HealthComponent;
import ro.ac.castravetii.components.MovementComponent;
import ro.ac.castravetii.components.TransformComponent;
import ro.ac.castravetii.events.*;

import java.util.ArrayDeque;

public class EnemyDamageSystem extends EntitySystem {
    private final GameEventQueue queue;
    private final ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    private final ComponentMapper<EnemyComponent> em = ComponentMapper.getFor(EnemyComponent.class);

    public EnemyDamageSystem(GameEventQueue queue, int priority) {
        super(priority);
        this.queue = queue;
    }

    @Override
    public void update(float deltaTime) {
        // preluare lista evenimente damage
        ArrayDeque<GameEvent> events = queue.getEvents(AttackEvent.class);

        if (events.isEmpty()) return;

        AttackEvent attackEvent;
        for (GameEvent event : events) {
            attackEvent = (AttackEvent) event;
            if (attackEvent.target() instanceof Enemy) {

                Enemy enemy = (Enemy) attackEvent.target();

                // accesare componenta sanatate de pe tinta
                HealthComponent health = hm.get(enemy);
                TransformComponent transform = enemy.getComponent(TransformComponent.class);

                float dx = transform.position.x - Player.getInstance().getTransformComponent().position.x;
                float dy = transform.position.y - Player.getInstance().getTransformComponent().position.y;

                float length = (float) Math.sqrt(dx * dx + dy * dy);
                if (length != 0) {
                    dx /= length;
                    dy /= length;
                }

                MovementComponent move = enemy.getComponent(MovementComponent.class);
                //forta cu cat il impinge pe inamic
                float force = 100f;
                move.knockbackX = dx * force;
                move.knockbackY = dy * force;

                if (health != null) {
                    // scadere viata
                    health.currentHealth -= attackEvent.damage();

                    //adaugare sunet inamic lovit
                    Services.soundSystem.play("enemyHit", 1f);

                    // eliminare daca viata e zero
                    if (health.currentHealth <= 0) {
                        EnemyComponent enemyC = em.get(enemy);
                        queue.post(new PlayerXPGainEvent(enemyC.xpValue));
                        queue.post(new EnemyKilledEvent(enemyC.pointValue));

                        enemy.die();

                        // Animatie de fade dupa care remove
                        Services.soundSystem.play("enemyDead", 1.2f);
                    }
                }
            } else if (attackEvent.target() instanceof com.badlogic.ashley.core.Entity) {
                HealthComponent playerHealth = Player.getInstance().getHealthComponent();

                    playerHealth.currentHealth -= attackEvent.damage();


                    System.out.println("DEBUG: Player took damage! Current HP: " + playerHealth.currentHealth);


                    if (playerHealth.currentHealth <= 0) {
                        System.out.println("DEBUG: Player has died!");

                    }
                }

            }
        }
    }
