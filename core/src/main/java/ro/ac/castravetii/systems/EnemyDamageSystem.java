package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.EntitySystem;
import ro.ac.castravetii.components.HealthComponent;
import ro.ac.castravetii.events.EnemyDamageEvent;
import ro.ac.castravetii.events.GameEvent;
import ro.ac.castravetii.events.GameEventQueue;

public class EnemyDamageSystem extends EntitySystem {
    private final GameEventQueue queue;

    public EnemyDamageSystem(GameEventQueue queue){
        this.queue = queue;
    }

    public void update(float deltaTime){

        GameEvent event;
        HealthComponent health;

        java.util.Queue<GameEvent> temp = new java.util.LinkedList<>();
        int size = queue.size();

        for(int i = 0; i< size; i++) {
            event = queue.poll();

            if (event instanceof EnemyDamageEvent dmg) {

                health = dmg.target.getComponent(HealthComponent.class);

                if (health != null) {
                    health.currentHealth -= dmg.amount;
                }
            } else {
                temp.add(event);
            }
        }

        while (!temp.isEmpty()){
            queue.add(temp.poll());
        }
    }

}
