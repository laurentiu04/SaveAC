package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.EntitySystem;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.EnemyComponent;
import ro.ac.castravetii.components.HealthComponent;
import ro.ac.castravetii.components.MovementComponent;
import ro.ac.castravetii.components.TransformComponent;
import ro.ac.castravetii.events.AttackEvent;
import ro.ac.castravetii.events.GameEvent;
import ro.ac.castravetii.events.GameEventQueue;
import ro.ac.castravetii.events.PlayerXPGainEvent;

import java.util.ArrayDeque;

public class EnemyDamageSystem extends EntitySystem {
    private final GameEventQueue queue;
    private final ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    private final ComponentMapper<EnemyComponent> em = ComponentMapper.getFor(EnemyComponent.class);

    public EnemyDamageSystem(GameEventQueue queue, int priority){
        super(priority);
        this.queue = queue;
    }

    @Override
    public void update(float deltaTime){
        // preluare lista evenimente damage
        ArrayDeque<GameEvent> events = queue.getEvents(AttackEvent.class);

        if (events.isEmpty()) return;

        for(GameEvent event : events){
            AttackEvent attackEvent = (AttackEvent) event;
            if (attackEvent.target().getComponent(EnemyComponent.class) == null) {
                continue;
            }
                // accesare componenta sanatate de pe tinta
                HealthComponent health = hm.get(attackEvent.target());
                TransformComponent transform = attackEvent.target().getComponent(TransformComponent.class);

                float dx = transform.position.x - Player.getInstance().getTransformComponent().position.x;
                float dy = transform.position.y - Player.getInstance().getTransformComponent().position.y;

                float length = (float) Math.sqrt(dx * dx + dy * dy);
                if(length != 0){
                    dx /= length;
                    dy /= length;
                }

                MovementComponent move = attackEvent.target().getComponent(MovementComponent.class);
                //forta cu cat il impinge pe inamic
                float force = 100f;
                move.knockbackX = dx * force;
                move.knockbackY = dy * force;

                if(health != null){
                    // scadere viata
                    health.currentHealth -= attackEvent.damage();

                    //adaugare sunet inamic lovit
                    Services.soundSystem.play("enemyHit", 1f);

                    // eliminare daca viata e zero
                    if(health.currentHealth <= 0) {
                        EnemyComponent enemy = em.get(attackEvent.target());
                        queue.post(new PlayerXPGainEvent(enemy.xpValue));

                        attackEvent.target().remove(EnemyComponent.class);
                        attackEvent.target().remove(MovementComponent.class);

                        // Animatie de fade dupa care remove
                        Services.soundSystem.play("enemyDead", 1.2f);
                    }
                }
        }
    }
}
