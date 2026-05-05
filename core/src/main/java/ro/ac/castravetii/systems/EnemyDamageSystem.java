package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.EntitySystem;
import ro.ac.castravetii.components.HealthComponent;
import ro.ac.castravetii.events.EnemyDamageEvent;
import ro.ac.castravetii.events.GameEvent;
import ro.ac.castravetii.events.GameEventQueue;

import java.util.ArrayDeque;

public class EnemyDamageSystem extends EntitySystem {
    private final GameEventQueue queue;

    public EnemyDamageSystem(GameEventQueue queue){
        this.queue = queue;
    }

    public void update(float deltaTime){

        ArrayDeque<GameEvent> events = queue.getEvents(EnemyDamageEvent.class);

        for(GameEvent event : events){
            if(event instanceof EnemyDamageEvent dmg){
                HealthComponent health = dmg.target.getComponent(HealthComponent.class);

                if(health != null){
                    health.currentHealth -= dmg.amount;
                }
            }

            queue.remove(event);
        }
    }

}
