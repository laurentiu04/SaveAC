package ro.ac.castravetii.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.viewport.FitViewport;
import ro.ac.castravetii.MapGenerator;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.Services;

import static com.badlogic.gdx.Gdx.gl;

public class GameScreen implements Screen {

    private  Game game;
    private Stage stage;
    private ProgressBar playerHealthBar;
    private Label healthBarLabel;

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

        playerHealthBar = new ProgressBar(0, Player.INSTANCE.getHealth(), 1, false, Services.skin);
        playerHealthBar.setBounds(
            Gdx.graphics.getWidth()/Services.uiScale/2f - 100,
            0,
            200,
            32
        );
        stage.addActor(playerHealthBar);

        Label.LabelStyle style = new Label.LabelStyle(Services.font, Color.WHITE);
        healthBarLabel = new Label("", style);
        healthBarLabel.setColor(Color.WHITE);
        healthBarLabel.setFontScale(0.5f);
        healthBarLabel.pack();
        stage.addActor(healthBarLabel);
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

        playerHealthBar.setValue(Player.INSTANCE.getHealth());
        healthBarLabel.setText(Player.INSTANCE.getHealth() + "/" + Player.INSTANCE.getMaxHealth());
        healthBarLabel.pack();
        healthBarLabel.setPosition(
            Gdx.graphics.getWidth()/4f - healthBarLabel.getWidth()/2f,
            playerHealthBar.getY() + playerHealthBar.getHeight()/2 - healthBarLabel.getHeight()/2f);
        stage.act(delta);
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
