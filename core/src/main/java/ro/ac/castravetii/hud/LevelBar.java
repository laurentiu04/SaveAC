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
        ProgressBar.ProgressBarStyle style = bar.getStyle();
        style.background.setMinHeight(44);
        style.knobBefore.setMinHeight(44);

        label = new Label("", Services.skin, "levelBar");
        label.setAlignment(Align.center, Align.center);
        this.add(bar);
        this.add(new Container<>(label).padBottom(7f));
    }

    public void update(LevelComponent component) {
        bar.setRange(0, component.levelUpTarget);
        bar.setValue(component.xp);
        label.setText("LVL " + component.level);
        label.pack();

        if (component.level == 40) {
            bar.setStyle(Services.skin.get("maxLVL", ProgressBar.ProgressBarStyle.class));
        }
    }
}
