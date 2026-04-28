package ro.ac.castravetii.hud;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.PlayerStatsComponent;

public class StatDisplayManager extends HorizontalGroup {

    private final StatWidget strengthAttrib;
    private final StatWidget speedAttrib;
    private final StatWidget healthAttrib;
    private final StatWidget xpAttrib;
    private final PointCounter pointCounter;

    public StatDisplayManager() {

        strengthAttrib = new StatWidget(Services.skin.getDrawable("strength_stat"), Services.skin.getDrawable("key1"));
        speedAttrib = new StatWidget(Services.skin.getDrawable("speed_stat"), Services.skin.getDrawable("key2"));
        healthAttrib = new StatWidget(Services.skin.getDrawable("health_stat"), Services.skin.getDrawable("key3"));
        xpAttrib = new StatWidget(Services.skin.getDrawable("xp_stat"), Services.skin.getDrawable("key4"));
        pointCounter = new PointCounter(Services.skin.getDrawable("point_counter"), Services.skin.getDrawable("point_counter_on"));

        this.addActor(pointCounter);
        this.addActor(strengthAttrib);
        this.addActor(speedAttrib);
        this.addActor(healthAttrib);
        this.addActor(xpAttrib);

        this.space(10);
    }

    public void update(PlayerStatsComponent stats) {
        pointCounter.update(stats.upgradePoints);
        strengthAttrib.update(stats.strengthLevel);
        speedAttrib.update(stats.speedLevel);
        healthAttrib.update(stats.healthLevel);
        xpAttrib.update(stats.xpGainLevel);
    }
}
