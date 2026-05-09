package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.EntitySystem;
import ro.ac.castravetii.components.EnemyComponent;
import ro.ac.castravetii.components.HealthComponent;
import ro.ac.castravetii.events.AttackEvent;
import ro.ac.castravetii.events.GameEvent;
import ro.ac.castravetii.events.GameEventQueue;

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
                return;
            }
                // accesare componenta sanatate de pe tinta
                HealthComponent health = hm.get(attackEvent.target());

                if(health != null){
                    // scadere viata
                    health.currentHealth -= attackEvent.damage();

                    // eliminare daca viata e zero
                    if(health.currentHealth <= 0){
                        System.out.println("MORT");
                        queue.post(new ro.ac.castravetii.events.PlayerXPGainEvent(em.get(attackEvent.target()).xpValue));
                        getEngine().removeEntity(attackEvent.target());
                    }
                }
            // eliminare eveniment din coada dupa procesare
            //noinspection SuspiciousMethodCalls
            queue.remove(event);
        }
    }
}
