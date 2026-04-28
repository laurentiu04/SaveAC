package ro.ac.castravetii.hud;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.LevelComponent;

public class LevelBar extends Stack {
    private final ProgressBar bar;
    private final Label label;

    public LevelBar() {
        bar =  new ProgressBar(0, 100, 1, false, Services.skin, "xpbar");

        label = new Label("", Services.skin, "healthbar");
        label.setAlignment(Align.center, Align.center);
        this.add(bar);
        this.add(new Container<>(label).padBottom(4));
    }

    public void update(LevelComponent component) {
        bar.setRange(0, component.levelUpTarget);

        bar.setValue(component.xp);
        label.setText("LVL " + component.level);
        label.pack();
        label.setPosition(
            bar.getX() + bar.getWidth()/2f - label.getWidth()/2f,
            bar.getY() + bar.getHeight()/2f - label.getHeight()/2f);

        if (component.level == 40) {
            bar.setStyle(Services.skin.get("maxLVL", ProgressBar.ProgressBarStyle.class));
        }
    }
}
