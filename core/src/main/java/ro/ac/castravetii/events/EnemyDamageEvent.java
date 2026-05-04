package ro.ac.castravetii.events;

import com.badlogic.ashley.core.Entity;
import ro.ac.castravetii.Enemy;
import ro.ac.castravetii.Player;

public class EnemyDamageEvent implements GameEvent {
    public Entity target;
    public int amount;
    public Entity source;

    public EnemyDamageEvent(Entity target, int amount, Entity source){
        this.target = target;
        this.amount = amount;
        this.source = source;
    }

}
