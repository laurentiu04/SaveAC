package ro.ac.castravetii.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;
import com.ray3k.tenpatch.TenPatchDrawable;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.HealthComponent;

public class HealthBar extends Stack {
    private final ProgressBar bar;
    private final TenPatchDrawable barFill;
    private final Label label;

    public HealthBar() {
        bar = new ProgressBar(0, 100, 1, false, Services.skin, "healthbar");
        ProgressBar.ProgressBarStyle style = bar.getStyle();
        style.background.setMinHeight(50);
        style.knobBefore.setMinHeight(50);

        label = new Label("", Services.skin, "healthbar");
        label.setAlignment(Align.center, Align.center);
        this.add(bar);
        this.add(new Container<>(label).padBottom(4));

        barFill =  (TenPatchDrawable) bar.getStyle().knobBefore;
    }

    public void update(HealthComponent component) {
        label.setText(component.currentHealth + "/" + component.maxHealth);
        bar.setRange(0, component.maxHealth);
        bar.setValue(component.currentHealth);

        float healthPercent = (float)component.currentHealth / component.maxHealth;
        if (healthPercent > 0.5f) {
            barFill.setColor(new Color(Color.valueOf("7abb44")).lerp(Color.YELLOW, (1 - healthPercent)/0.5f));
        }
        else {
            barFill.setColor(Color.YELLOW.cpy().lerp(Color.RED, 1 - healthPercent/0.5f));
        }

        label.pack();
        label.setPosition(
            bar.getX() + bar.getWidth() / 2f - bar.getWidth()/2f,
            bar.getY() + bar.getHeight()/2 - bar.getHeight()/2f + 1);
    }
}
