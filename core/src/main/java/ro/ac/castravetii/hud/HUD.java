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
    private final Label killDisplay;
    private final LevelBar levelBar;
    private final HealthBar healthBar;
    private final Label fpsDisplay;
    private final Label wave;

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

        fpsDisplay = new Label("", Services.skin, "levelbar");
        killDisplay = new Label("SCORE: 0", Services.skin, "healthbar");
        wave = new Label("", Services.skin);
        wave.setFontScale(1.15f);

        table.add(fpsDisplay).left().top().pad(10).expandX();
        table.add(killDisplay).right().top().pad(10).expandX();
        table.row();

        table.add();

        table.add(wave).right().top().padTop(70);
        table.row();

        wave.setVisible(false);

        VerticalGroup leftGroup = new VerticalGroup();
        leftGroup.space(5);
        leftGroup.addActor(new Container<>(healthBar).width(300));
        leftGroup.addActor(new Container<>(levelBar).width(300));
        //table.add(leftGroup).expandX().left().minWidth(300);
        table.add(leftGroup).left().bottom().pad(10).expand().colspan(2);

        Table centerGroup = new Table();
        table.add(centerGroup).space(20).expandX().center().minWidth(centerGroup.getPrefWidth());

        HorizontalGroup rightGroup = new HorizontalGroup();
        rightGroup.addActor(statsManager);
        rightGroup.align(Align.right);
        //table.add(rightGroup).expandX().right().minWidth(300);
        table.add(statsManager).right().bottom().pad(10);
//        table.setDebug(true);
    }

    public void updateKills(int points) {
        killDisplay.setText("SCORE: " + points);
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

    public Label getWaveLabel(){
        return wave;
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
