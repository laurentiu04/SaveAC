package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.EntitySystem;
import ro.ac.castravetii.events.GameEventQueue;

public class EnemyDamageSystem extends EntitySystem {
    private final GameEventQueue queue;

    public EnemyDamageSystem(GameEventQueue queue){
        this.queue = queue;
    }

    public void update(float deltaTime){

    }

}
