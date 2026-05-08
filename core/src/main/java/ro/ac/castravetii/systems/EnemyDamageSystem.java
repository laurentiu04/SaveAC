package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import ro.ac.castravetii.components.HealthComponent;
import ro.ac.castravetii.events.EnemyDamageEvent;
import ro.ac.castravetii.events.GameEvent;
import ro.ac.castravetii.events.GameEventQueue;

import java.util.ArrayDeque;

public class EnemyDamageSystem extends EntitySystem {
    private final GameEventQueue queue;
    private final ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);

    public EnemyDamageSystem(GameEventQueue queue, int priority){
        super(priority);
        this.queue = queue;
    }

    @Override
    public void update(float deltaTime){
        // preluare lista evenimente damage
        ArrayDeque<GameEvent> events = queue.getEvents(EnemyDamageEvent.class);

        if (events == null || events.isEmpty()) return;

        for(GameEvent event : events){
            if(event instanceof EnemyDamageEvent dmg){
                // accesare componenta sanatate de pe tinta
                HealthComponent health = hm.get(dmg.target);

                if(health != null){
                    // scadere viata
                    health.currentHealth -= dmg.amount;

                    // eliminare daca viata e zero
                    if(health.currentHealth <= 0){
                        queue.post(new ro.ac.castravetii.events.PlayerXPGainEvent(30));
                        getEngine().removeEntity(dmg.target);
                    }
                }
            }
            // eliminare eveniment din coada dupa procesare
            queue.remove(event);
        }
    }
}
