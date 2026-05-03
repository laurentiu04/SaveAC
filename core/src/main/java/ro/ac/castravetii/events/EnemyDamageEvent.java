package ro.ac.castravetii.events;

import com.badlogic.ashley.core.Entity;

public class EnemyDamageEvent  {
    public Entity target;
    public int amount;
    public Entity source;

    public EnemyDamageEvent(Entity target, int amount, Entity source){
        this.target = target;
        this.amount = amount;
        this.source = source;
    }

}
