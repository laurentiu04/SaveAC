package ro.ac.castravetii;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.tenpatch.TenPatchDrawable;

public class HUD implements Disposable {

    public Stage stage;
    private Table table;
    private Stack healthBar;
    private ProgressBar healtbarTex;
    private Label healthBarLabel;
    private Stack xpBar;
    private ProgressBar xpbarTex;
    private Label xpBarLabel;
    private AttributeWidget strengthAttrib;
    private AttributeWidget speedAttrib;
    private AttributeWidget healthAttrib;
    private AttributeWidget xpAttrib;


    public void init() {
        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.pad(10);

        healtbarTex = new ProgressBar(0, 100, 1, false, Services.skin, "healthbar") {
            @Override
            public float getPrefHeight() {
                return 40;
            }
        };
        Label.LabelStyle style = new Label.LabelStyle(Services.font20, Color.WHITE);
        healthBarLabel = new Label("", style);
        healthBarLabel.setAlignment(Align.center, Align.center);
        healthBar = new Stack(healtbarTex, healthBarLabel);


        xpbarTex = new ProgressBar(0, 100, 1, false, Services.skin, "xpbar") {
            @Override
            public float getPrefHeight() {
                return 40;
            }
        };
        xpBarLabel = new Label("", style);
        xpBarLabel.setAlignment(Align.center, Align.center);
        xpBar = new Stack(xpbarTex, xpBarLabel);

        strengthAttrib = new AttributeWidget(Services.skin.getDrawable("strength_stat"));
        speedAttrib = new AttributeWidget(Services.skin.getDrawable("speed_stat"));
        healthAttrib = new AttributeWidget(Services.skin.getDrawable("health_stat"));
        xpAttrib = new AttributeWidget(Services.skin.getDrawable("xp_stat"));

        table.add().expand().colspan(3);
        table.row();

        VerticalGroup leftGroup = new VerticalGroup();
        leftGroup.space(5);
        leftGroup.addActor(healthBar);
        leftGroup.addActor(xpBar);
        leftGroup.grow();
        table.add(leftGroup).expandX().left().minWidth(300);

        Table centerGroup = new Table();
        table.add(centerGroup).spaceLeft(20).spaceRight(20).expandX().center().minWidth(centerGroup.getPrefWidth());

        HorizontalGroup rightGroup = new HorizontalGroup();
        rightGroup.addActor(healthAttrib);
        rightGroup.addActor(strengthAttrib);
        rightGroup.addActor(speedAttrib);
        rightGroup.addActor(xpAttrib);
        rightGroup.space(10);
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

    public void render() {

        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
