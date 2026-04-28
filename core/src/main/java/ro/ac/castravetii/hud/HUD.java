package ro.ac.castravetii.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.tenpatch.TenPatchDrawable;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.PlayerStatsComponent;

public class HUD implements Disposable {

    public final Stage stage;

    private final ProgressBar healtbarTex;
    private final Label healthBarLabel;

    private final ProgressBar xpbarTex;
    private final Label xpBarLabel;

    private final StatDisplayManager statsManager;

    public HUD() {

        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.pad(10);

        healtbarTex = new ProgressBar(0, 100, 1, false, Services.skin, "healthbar");
        ProgressBar.ProgressBarStyle style = healtbarTex.getStyle();
        style.background.setMinHeight(50);
        style.knobBefore.setMinHeight(50);

        healtbarTex.pack();
        healthBarLabel = new Label("", Services.skin, "healthbar");
        healthBarLabel.setAlignment(Align.center, Align.center);
        Stack healthBar = new Stack();
        healthBar.add(healtbarTex);
        healthBar.add(new Container<>(healthBarLabel).padBottom(4));


        xpbarTex = new ProgressBar(0, 100, 1, false, Services.skin, "xpbar");
        style = xpbarTex.getStyle();
        style.background.setMinHeight(50);
        style.knobBefore.setMinHeight(50);
        xpBarLabel = new Label("", Services.skin, "healthbar");
        xpBarLabel.setAlignment(Align.center, Align.center);
        Stack xpBar = new Stack(xpbarTex, xpBarLabel);

        statsManager = new StatDisplayManager();

        table.add().expand().colspan(3);
        table.row();

        VerticalGroup leftGroup = new VerticalGroup();
        leftGroup.space(25);
        leftGroup.addActor(new Container<>(healthBar).width(300).padBottom(-20));
        leftGroup.addActor(new Container<>(xpBar).width(300));
        table.add(leftGroup).expandX().left().minWidth(300);

        Table centerGroup = new Table();
        table.add(centerGroup).space(20).expandX().center().minWidth(centerGroup.getPrefWidth());

        HorizontalGroup rightGroup = new HorizontalGroup();
        rightGroup.addActor(statsManager);
        rightGroup.align(Align.right);
        table.add(rightGroup).expandX().right().minWidth(300);
//        table.setDebug(true);
    }

    public void updateHealthBar(int health, int maxHealth) {
        healtbarTex.setValue(health);
        healtbarTex.setRange(0, maxHealth);
        TenPatchDrawable tenPatchDrawable = (TenPatchDrawable) healtbarTex.getStyle().knobBefore;

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
            healtbarTex.getX() + healtbarTex.getWidth() / 2f - healthBarLabel.getWidth()/2f,
            healtbarTex.getY() + healtbarTex.getHeight()/2 - healthBarLabel.getHeight()/2f + 1);
    }

    public void updateXPBar(int xp, int level, int levelUpXp) {
        if (levelUpXp != xpbarTex.getMaxValue()) {
            xpbarTex.setRange(0, levelUpXp);
        }

        xpbarTex.setValue(xp);
        xpBarLabel.setText("LVL " + level);
        xpBarLabel.pack();
        xpBarLabel.setPosition(
            xpbarTex.getX() + xpbarTex.getWidth()/2f - xpBarLabel.getWidth()/2f,
            xpbarTex.getY() + xpbarTex.getHeight()/2f - xpBarLabel.getHeight()/2f);

        if (level == 40) {
            xpbarTex.setStyle(Services.skin.get("maxLVL", ProgressBar.ProgressBarStyle.class));
        }
    }

    public void updateStatsDisplay(PlayerStatsComponent stats) {
        statsManager.update(stats);
    }

    public void render() {

        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
