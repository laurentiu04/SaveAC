package ro.ac.castravetii;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.ray3k.tenpatch.TenPatchDrawable;

public class HUD implements Disposable {

    public Stage stage;
    private ProgressBar healthBar;
    private Label healthBarLabel;
    private ProgressBar xpBar;
    private Label xpBarLabel;

    public void init() {
        stage = new Stage(new FitViewport(
            Gdx.graphics.getWidth()/Services.uiScale,
            Gdx.graphics.getHeight()/Services.uiScale
        ));

        Gdx.input.setInputProcessor(stage);

        healthBar = new ProgressBar(0, 100, 1, false, Services.skin, "healthbar");
        healthBar.setSize(300, 32);
        healthBar.setPosition(10, 50);
        stage.addActor(healthBar);

        Label.LabelStyle style = new Label.LabelStyle(Services.font, Color.WHITE);
        healthBarLabel = new Label("", style);
        healthBarLabel.setColor(Color.WHITE);
        healthBarLabel.setFontScale(1.4f);
        healthBarLabel.pack();
        stage.addActor(healthBarLabel);

        xpBar = new ProgressBar(0, 100, 1, false, Services.skin, "xpbar");
        xpBar.getStyle().background.setMinHeight(55);
        xpBar.getStyle().knobBefore.setMinHeight(45);
        xpBar.setSize(250, 32);
        xpBar.setPosition(10, 10);
        stage.addActor(xpBar);
        xpBarLabel = new Label("", style);
        xpBarLabel.setFontScale(1f);
        xpBarLabel.pack();
        stage.addActor(xpBarLabel);
    }

    public void updateHealthBar(int health, int maxHealth) {
        healthBar.setValue(health);
        healthBar.setRange(0, maxHealth);
        TenPatchDrawable tenPatchDrawable = (TenPatchDrawable) healthBar.getStyle().knobBefore;

        float healthPercent = (float)health / maxHealth;
        if (healthPercent > 0.5f) {
            tenPatchDrawable.setColor(new Color(Color.valueOf("7abb44")).lerp(Color.YELLOW, (1 - healthPercent)/0.5f));
        }
        else {
            tenPatchDrawable.setColor(Color.YELLOW.cpy().lerp(Color.RED, 1 - healthPercent/0.5f));
        }
        healthBarLabel.setText(health + "/" + maxHealth);
        healthBarLabel.pack();
        healthBarLabel.setPosition(
            healthBar.getX() + healthBar.getWidth() / 2f - healthBarLabel.getWidth()/2f,
            healthBar.getY() + healthBar.getHeight()/2 - healthBarLabel.getHeight()/2f + 1);

        System.out.println("Updated");
    }

    public void updateXPBar(int xp, int level, int levelUpXp) {
        if (levelUpXp != xpBar.getMaxValue()) {
            xpBar.setRange(0, levelUpXp);
        }

        xpBar.setValue(xp);
        xpBarLabel.setText(" LVL " + level);
        xpBarLabel.pack();
        xpBarLabel.setPosition(
            xpBar.getX() + xpBar.getWidth()/2f - xpBarLabel.getWidth()/2f,
            xpBar.getY() + xpBar.getHeight()/2f - xpBarLabel.getHeight()/2f);
    }

    public void render() {

        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
