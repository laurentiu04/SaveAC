package ro.ac.castravetii.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import ro.ac.castravetii.*;

import static com.badlogic.gdx.Gdx.gl;

public class GameScreen implements Screen {

    private final Game game;
    private HUD hud;
    private Player player;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        MapGenerator.createMap();

        player = Player.create();

        hud = new HUD();

        hud.init();

        player.setListener(new StatsListener() {
            @Override
            public void onXpChange() {
                hud.updateXPBar(player.xpSystem.getXP(), player.xpSystem.getLevel(), player.xpSystem.getLevelUpXP());
            }

            @Override
            public void onHealthChange() {
                hud.updateHealthBar(player.getHealth(), player.getMaxHealth());
            }
        });
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

        player.snapCamera();

        Services.batch.setProjectionMatrix(Services.camera.combined);

        Services.shapeRenderer.setProjectionMatrix(Services.camera.combined);

        hud.render();

        player.gainXP(20);
    }

    @Override
    public void resize(int width, int height) {
        // Cand se modifica dimensiunea ferestrei, modific si dimensiunea vederii camerei.
        Services.camera.viewportWidth = width;
        Services.camera.viewportHeight = height;

        // Fac update la camera ca sa ia noile dimensiuni
        Services.camera.update();

        hud.stage.getViewport().update(width, height, true);
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
        hud.dispose();
    }
}
