package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.EntitySystem;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.events.EnemyDamageEvent;
import ro.ac.castravetii.events.GameEvent;
import ro.ac.castravetii.events.GameEventQueue;
import ro.ac.castravetii.events.PlayerDamageEvent;

import java.util.ArrayDeque;

public class EnemyDamageSystem extends EntitySystem {
    private final GameEventQueue queue;

    public EnemyDamageSystem(GameEventQueue queue, int priority){
        super(priority);
        this.queue = queue;
    }

    public void update(float deltaTime){

        ArrayDeque<GameEvent> events = queue.getEvents(EnemyDamageEvent.class);

        for(GameEvent event : events){
            if(event instanceof EnemyDamageEvent dmg){
                if(dmg.target == Player.getInstance().getEntity()){
                    queue.post(new PlayerDamageEvent(dmg.amount));
                }
            }

            //noinspection SuspiciousMethodCalls
            queue.remove(event);
        }
    }

}
