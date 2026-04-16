package ro.ac.castravetii.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.ray3k.tenpatch.TenPatchDrawable;
import ro.ac.castravetii.MapGenerator;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.Services;

import static com.badlogic.gdx.Gdx.gl;

public class GameScreen implements Screen {

    private final Game game;
    private Stage stage;
    private ProgressBar playerHealthBar;
    private Label healthBarLabel;
    private ProgressBar xpBar;
    private Label xpBarLabel;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        MapGenerator.createMap();

        Player.create();

        stage = new Stage(new FitViewport(
            Gdx.graphics.getWidth()/Services.uiScale,
            Gdx.graphics.getHeight()/Services.uiScale
        ));

        Gdx.input.setInputProcessor(stage);

        playerHealthBar = new ProgressBar(0, Player.INSTANCE.getHealth(), 1, false, Services.skin, "healthbar");
        playerHealthBar.setBounds(
            10,
            50,
            300,
            32
        );
        stage.addActor(playerHealthBar);

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

    @Override
    public void render(float delta) {

        // Curat ecranul inainte sa desenez noul frame
        gl.glClearColor(0.696f, 0.733f, 0.780f, 1f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Services.batch.begin();
        MapGenerator.render();
        Services.batch.end();

        Services.engine.update(Gdx.graphics.getDeltaTime());

        Player.snapCamera();

        Services.batch.setProjectionMatrix(Services.camera.combined);

        Services.shapeRenderer.setProjectionMatrix(Services.camera.combined);

        stage.act(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Player.INSTANCE.getHealth() > 0) {
            Player.INSTANCE.takeDamage(1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Player.INSTANCE.getHealth() < Player.INSTANCE.getMaxHealth()) {
            Player.INSTANCE.heal(1);
        }

        playerHealthBar.setValue(Player.INSTANCE.getHealth());
        playerHealthBar.setRange(0, Player.INSTANCE.getMaxHealth());
        TenPatchDrawable tenPatchDrawable = (TenPatchDrawable) playerHealthBar.getStyle().knobBefore;

        float healthPercent = (float)Player.INSTANCE.getHealth() / Player.INSTANCE.getMaxHealth();
        if (healthPercent > 0.5f) {
            tenPatchDrawable.setColor(new Color(Color.valueOf("7abb44")).lerp(Color.YELLOW, (1 - healthPercent)/0.5f));
        }
        else {
            tenPatchDrawable.setColor(Color.YELLOW.cpy().lerp(Color.RED, 1 - healthPercent/0.5f));
        }
        healthBarLabel.setText(Player.INSTANCE.getHealth() + "/" + Player.INSTANCE.getMaxHealth());
        healthBarLabel.pack();
        healthBarLabel.setPosition(
            playerHealthBar.getX() + playerHealthBar.getWidth() / 2f - healthBarLabel.getWidth()/2f,
            playerHealthBar.getY() + playerHealthBar.getHeight()/2 - healthBarLabel.getHeight()/2f + 1);

        xpBar.setValue(70);
        xpBarLabel.setText(" LVL 0");
        xpBarLabel.pack();
        xpBarLabel.setPosition(
            xpBar.getX() + xpBar.getWidth()/2f - xpBarLabel.getWidth()/2f,
            xpBar.getY() + xpBar.getHeight()/2f - xpBarLabel.getHeight()/2f);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Cand se modifica dimensiunea ferestrei, modific si dimensiunea vederii camerei.
        Services.camera.viewportWidth = width;
        Services.camera.viewportHeight = height;

        // Fac update la camera ca sa ia noile dimensiuni
        Services.camera.update();

        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        MapGenerator.disposeMap();
        Services.dispose();
        stage.dispose();
    }
}
