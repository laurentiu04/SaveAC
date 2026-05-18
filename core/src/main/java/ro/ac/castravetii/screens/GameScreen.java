package ro.ac.castravetii.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import ro.ac.castravetii.*;
import ro.ac.castravetii.animations.AnimationController;
import ro.ac.castravetii.events.GameEventQueue;
import ro.ac.castravetii.hud.HUD;
import ro.ac.castravetii.hud.PauseMenu;
import ro.ac.castravetii.systems.*;

import static com.badlogic.gdx.Gdx.gl;

public class GameScreen implements Screen {

    private final Game game;
    private final GameEventQueue queue;
    private HUD hud;
    public Player player;
    private MapGenerator mapGen;

    public enum GameState { RUNNING, PAUSED }
    private GameState state = GameState.RUNNING;
    private PauseMenu pauseMenu;

    public GameScreen(Game game, GameEventQueue queue) {
        this.game = game;
        this.queue = queue;
    }

    @Override
    public void show() {
        queue.clearAll();

        Services.engine.removeAllEntities();
        Services.engine.removeAllSystems();
        mapGen = new MapGenerator();
        mapGen.createMap(Services.MAP_WIDTH, Services.MAP_HEIGHT, 32);

        Player.resetInstance();
        player = Player.create();

        hud = new HUD();

        pauseMenu = new PauseMenu(this);

        Services.setCameraLimits(Services.MAP_WIDTH, Services.MAP_HEIGHT);

        Services.engine.addSystem(new RenderSystem(10));

        Services.engine.addSystem(new PlayerControlSystem(game, queue,2));
        Services.engine.addSystem(new PlayerInputSystem(1));
        Services.engine.addSystem(new GunRenderSystem(1));
        Services.engine.addSystem(new BulletSystem(queue));
        Services.engine.addSystem(new GunShootingSystem());
        Services.engine.addSystem(new AnimationController());

        Services.engine.addSystem(new HUDSystem(hud, queue, 10));
        Services.engine.addSystem(new HealthbarSystem(game, queue, 10));
        Services.engine.addSystem(new MovementSystem(2));
        Services.engine.addSystem(new AnimationControlSystem());
        Services.engine.addSystem(new ColliderRenderSystem(10));
        Services.engine.addSystem(new EnemyPathfindingSystem(queue));
        Services.engine.addSystem(new EnemyDamageSystem(queue, 2));
        Services.engine.addSystem(new EnemyWaveSystem());
        Services.engine.addSystem(new EnemyDamageSystem(queue,1));
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (state == GameState.RUNNING) {
                pauseGame();
            } else {
                resumeGame();
            }
        }

        // Curat ecranul inainte sa desenez noul frame
        gl.glClearColor(0.696f, 0.733f, 0.780f, 1f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Services.batch.begin();
        mapGen.render();
        Services.batch.end();

        float engineDelta = (state == GameState.RUNNING) ? delta : 0f;
        Services.engine.update(engineDelta);

        player.snapCamera();
        Services.batch.setProjectionMatrix(Services.camera.combined);
        Services.shapeRenderer.setProjectionMatrix(Services.camera.combined);

        hud.render();

        if (state == GameState.PAUSED) {
            pauseMenu.getStage().act(delta);
            pauseMenu.getStage().draw();
        }

        queue.clearAll();

    }

    public void pauseGame() {
        state = GameState.PAUSED;
        Gdx.input.setInputProcessor(pauseMenu.getStage());
        Services.engine.removeSystem(Services.engine.getSystem(GunShootingSystem.class));
    }

    public void resumeGame() {
        state = GameState.RUNNING;
        Gdx.input.setInputProcessor(null);
        Services.engine.addSystem(new GunShootingSystem());
    }


    @Override
    public void resize(int width, int height) {
        // Cand se modifica dimensiunea ferestrei, modific si dimensiunea vederii camerei.
        Services.camera.viewportWidth = width;
        Services.camera.viewportHeight = height;

        // Fac update la camera ca sa ia noile dimensiuni
        Services.camera.update();

        hud.stage.getViewport().update(width, height, true);
        Services.setCameraLimits(Services.MAP_WIDTH, Services.MAP_HEIGHT);
        pauseMenu.getStage().getViewport().update(width, height, true);

    }

    @Override
    public void pause() {
        if (state == GameState.RUNNING) {
            pauseGame();
        }
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
        pauseMenu.dispose();
    }
}
