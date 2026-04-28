package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.EntitySystem;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.events.*;

import java.util.ArrayDeque;

/**
 * Acest sistem se va ocupa cu modificarea componentelor player-ului, exceptand `MovementComponent` pentru care
 * este PlayerInputSystem
 */
public class PlayerSystem extends EntitySystem {

    private final GameEventQueue queue;
    private final Player player;

    public PlayerSystem(GameEventQueue queue, Player player){
        this.queue = queue;
        this.player = player;
    }

    @Override
    public void update(float delta) {
        ArrayDeque<GameEvent> events = queue.getEventsOfType(PlayerDamageEvent.class);

        if (!events.isEmpty()) {
            for (GameEvent event : events ){

                switch (event) {
                    case PlayerDamageEvent e -> {
                        player.takeDamage(e.i());
                        queue.add(UpdateHUDEvent.healthBar);
                    }

                    default -> throw new IllegalStateException("Unexpected value: " + event);
                }

                queue.remove(event);
            }
        }
    }
}
