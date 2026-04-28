package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.HealthComponent;
import ro.ac.castravetii.components.LevelComponent;
import ro.ac.castravetii.components.PlayerComponent;
import ro.ac.castravetii.components.PlayerStatsComponent;
import ro.ac.castravetii.events.*;
import ro.ac.castravetii.hud.HUD;

public class HUDSystem extends EntitySystem {

    private final HUD hud;
    private final GameEventQueue queue;
    private final Entity player;

    public HUDSystem(HUD hud, GameEventQueue queue, int priority) {
        super(priority);

        this.hud = hud;
        this.queue = queue;
        this.player = Services.engine.getEntitiesFor(Family.one(PlayerComponent.class).get()).first();

        // Initializez toate elementele cu datele de inceput ale playerului
        hud.getStatsManager().update(player.getComponent(PlayerStatsComponent.class));
        hud.getHealthBar().update(player.getComponent(HealthComponent.class));
        hud.getLevelBar().update(player.getComponent(LevelComponent.class));
    }

    @Override
    public void update(float delta) {

        for (GameEvent event : queue.getEventsOfType(UpdateHUDEvent.class)) {
            switch (event){
                case UpdateHUDEvent.stats -> hud.getStatsManager().update(player.getComponent(PlayerStatsComponent.class));
                case UpdateHUDEvent.healthBar -> hud.getHealthBar().update(player.getComponent(HealthComponent.class));
                default -> throw new IllegalStateException("Unexpected value: " + event);
            }

            queue.remove(event);
        }
    }

}
