package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.PlayerComponent;
import ro.ac.castravetii.components.PlayerStatsComponent;
import ro.ac.castravetii.events.GameEvent;
import ro.ac.castravetii.events.GameEventQueue;
import ro.ac.castravetii.events.PlayerEvent;
import ro.ac.castravetii.hud.HUD;

public class HUDSystem extends EntitySystem {

    final HUD hud;
    final GameEventQueue queue;
    final Entity player;

    public HUDSystem(HUD hud, GameEventQueue queue) {
        this.hud = hud;
        this.queue = queue;
        this.player = Services.engine.getEntitiesFor(Family.one(PlayerComponent.class).get()).first();
        hud.updateStatsDisplay(player.getComponent(PlayerStatsComponent.class));
    }

    @Override
    public void update(float delta) {

        for (GameEvent event : queue.getEventsOfType(PlayerEvent.class)) {
            hud.updateStatsDisplay(player.getComponent(PlayerStatsComponent.class));
            queue.remove(event);
        }
    }

}
