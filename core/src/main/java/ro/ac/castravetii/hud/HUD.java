package ro.ac.castravetii.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ro.ac.castravetii.Services;

public class HUD implements Disposable {

    public final Stage stage;
    private final LevelBar levelBar;
    private final HealthBar healthBar;
    private final Label fpsDisplay;

    private final StatDisplayManager statsManager;

    public HUD() {

        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.pad(10);

        healthBar = new HealthBar();
        levelBar = new LevelBar();

        statsManager = new StatDisplayManager();

        fpsDisplay = new Label("", Services.skin, "levelBar");

        table.add(fpsDisplay).left().top().expand().colspan(3);
        table.row();

        VerticalGroup leftGroup = new VerticalGroup();
        leftGroup.space(5);
        leftGroup.addActor(new Container<>(healthBar).width(300));
        leftGroup.addActor(new Container<>(levelBar).width(300));
        table.add(leftGroup).expandX().left().minWidth(300);

        Table centerGroup = new Table();
        table.add(centerGroup).space(20).expandX().center().minWidth(centerGroup.getPrefWidth());

        HorizontalGroup rightGroup = new HorizontalGroup();
        rightGroup.addActor(statsManager);
        rightGroup.align(Align.right);
        table.add(rightGroup).expandX().right().minWidth(300);
//        table.setDebug(true);
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }

    public LevelBar getLevelBar() {
        return levelBar;
    }

    public StatDisplayManager getStatsManager() {
        return statsManager;
    }

    public void updateFPS() {
        fpsDisplay.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
    }

    public void render() {
        this.updateFPS();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
