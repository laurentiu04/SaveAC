package ro.ac.castravetii.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import ro.ac.castravetii.*;
import ro.ac.castravetii.events.GameEventQueue;
import ro.ac.castravetii.hud.HUD;

import static com.badlogic.gdx.Gdx.gl;

public class GameScreen implements Screen {

    private final Game game;
    private HUD hud;
    public Player player;
    public Enemy enemy;
    private MapGenerator mapGen;
    private GameEventQueue eventQueue;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        mapGen = new MapGenerator();
        mapGen.createMap(Services.MAP_WIDTH, Services.MAP_HEIGHT, 32);

        player = Player.create();
        enemy = Enemy.getInstance();

        hud = new HUD(this);

        player.setListener(new StatsListener() {
            @Override
            public void onXpChange() {
                hud.updateXPBar(player.getLevelComponent().xp, player.getLevelComponent().level, player.getLevelComponent().levelUpTarget);
            }

            @Override
            public void onHealthChange() {
                hud.updateHealthBar(player.getHealthComponent().currentHealth, player.getHealthComponent().maxHealth);
            }
        });

        Services.setCameraLimits(Services.MAP_WIDTH, Services.MAP_HEIGHT);
    }

    @Override
    public void render(float delta) {

        // Curat ecranul inainte sa desenez noul frame
        gl.glClearColor(0.696f, 0.733f, 0.780f, 1f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Services.batch.begin();
        mapGen.render();
        Services.batch.end();

        Services.engine.update(Gdx.graphics.getDeltaTime());

        player.snapCamera();

        Services.batch.setProjectionMatrix(Services.camera.combined);

        Services.shapeRenderer.setProjectionMatrix(Services.camera.combined);

        hud.render();

        player.gainXP(2);
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
        mapGen.dispose();
        Services.dispose();
        hud.dispose();
    }
}
