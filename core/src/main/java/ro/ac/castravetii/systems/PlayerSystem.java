package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.*;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.HealthComponent;
import ro.ac.castravetii.components.LevelComponent;
import ro.ac.castravetii.components.PlayerComponent;
import ro.ac.castravetii.components.PlayerStatsComponent;
import ro.ac.castravetii.events.*;

import java.util.ArrayDeque;

/**
 * Acest sistem se va ocupa cu modificarea componentelor player-ului, exceptand `MovementComponent` pentru care
 * este PlayerInputSystem
 */
public class PlayerSystem extends EntitySystem {

    ComponentMapper<LevelComponent> lvl = ComponentMapper.getFor(LevelComponent.class);
    ComponentMapper<HealthComponent> health = ComponentMapper.getFor(HealthComponent.class);

    private final GameEventQueue queue;
    private final Entity player;

    public PlayerSystem(GameEventQueue queue){
        this.queue = queue;
        this.player = Services.engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
    }

    @Override
    public void update(float delta) {
        ArrayDeque<GameEvent> events = queue.getEventsOfType(PlayerDamageEvent.class, PlayerXPGainEvent.class);

        if (!events.isEmpty()) {
            for (GameEvent event : events ){

                switch (event) {
                    case PlayerDamageEvent e -> {
                        health.get(player).currentHealth -= e.damage();
                        queue.add(UpdateHUDEvent.healthBar);
                    }

                    case PlayerXPGainEvent e -> {
                        lvl.get(player).xp += e.xp();
                        queue.add(UpdateHUDEvent.levelBar);
                    }

                    default -> throw new IllegalStateException("Unexpected value: " + event);
                }

                queue.remove(event);
            }
        }
    }
}
