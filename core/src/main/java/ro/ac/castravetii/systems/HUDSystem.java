package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.*;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.components.HealthComponent;
import ro.ac.castravetii.components.LevelComponent;
import ro.ac.castravetii.components.PlayerStatsComponent;
import ro.ac.castravetii.events.*;
import ro.ac.castravetii.hud.HUD;

public class HUDSystem extends EntitySystem {

    private final LevelComponent levelComp;
    private final HealthComponent healthComp;
    private final PlayerStatsComponent statsComp;

    private final HUD hud;
    private final GameEventQueue queue;

    public HUDSystem(HUD hud, GameEventQueue queue, int priority) {
        super(priority);

        this.hud = hud;
        this.queue = queue;

        healthComp = Player.getInstance().getHealthComponent();
        levelComp = Player.getInstance().getLevelComponent();
        statsComp = Player.getInstance().getPlayerStats();

        // Initializez toate elementele cu datele de inceput ale playerului
        hud.getStatsManager().update(statsComp);
        hud.getHealthBar().update(healthComp);
        hud.getLevelBar().update(levelComp);
    }

    @Override
    public void update(float delta) {

        for (GameEvent event : queue.getEventsOfType(UpdateHUDEvent.class)) {
            switch (event){
                case UpdateHUDEvent.stats -> hud.getStatsManager().update(statsComp);
                case UpdateHUDEvent.healthBar -> hud.getHealthBar().update(healthComp);
                case UpdateHUDEvent.levelBar -> hud.getLevelBar().update(levelComp);
                default -> throw new IllegalStateException("Unexpected value: " + event);
            }

            queue.remove(event);
        }
    }

}
