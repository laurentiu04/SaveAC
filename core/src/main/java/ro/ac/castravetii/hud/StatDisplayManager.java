package ro.ac.castravetii.hud;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.PlayerStatsComponent;

public class StatDisplayManager extends HorizontalGroup {

    private final Player player;

    private final StatWidget strengthAttrib;
    private final StatWidget speedAttrib;
    private final StatWidget healthAttrib;
    private final StatWidget xpAttrib;
    private final PointCounter pointCounter;

    public StatDisplayManager(Player player) {
        this.player = player;

        strengthAttrib = new StatWidget(Services.skin.getDrawable("strength_stat"), Services.skin.getDrawable("key1"));
        speedAttrib = new StatWidget(Services.skin.getDrawable("speed_stat"), Services.skin.getDrawable("key2"));
        healthAttrib = new StatWidget(Services.skin.getDrawable("health_stat"), Services.skin.getDrawable("key3"));
        xpAttrib = new StatWidget(Services.skin.getDrawable("xp_stat"), Services.skin.getDrawable("key4"));
        pointCounter = new PointCounter(Services.skin.getDrawable("point_counter"));

        this.addActor(pointCounter);
        this.addActor(strengthAttrib);
        this.addActor(speedAttrib);
        this.addActor(healthAttrib);
        this.addActor(xpAttrib);

        this.space(10);

        update();
    }

    public void update() {

        PlayerStatsComponent stats = player.getPlayerStats();

        pointCounter.update(stats.upgradePoints);
        strengthAttrib.update(stats.strengthLevel);
        speedAttrib.update(stats.speedLevel);
        healthAttrib.update(stats.healthLevel);
        xpAttrib.update(stats.xpGainLevel);
    }
}
