package ro.ac.castravetii.events;

import com.badlogic.ashley.core.Entity;

public class EnemyKilledEvent implements GameEvent {
    public int points;

    public EnemyKilledEvent(int points) {
        this.points = points;
    }
}
