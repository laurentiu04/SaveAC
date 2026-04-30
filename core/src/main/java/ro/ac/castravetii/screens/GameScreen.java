package ro.ac.castravetii.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import ro.ac.castravetii.*;
import ro.ac.castravetii.events.GameEventQueue;
import ro.ac.castravetii.events.PlayerDamageEvent;
import ro.ac.castravetii.events.PlayerXPGainEvent;
import ro.ac.castravetii.hud.HUD;
import ro.ac.castravetii.systems.*;

import static com.badlogic.gdx.Gdx.gl;

public class GameScreen implements Screen {

    private final Game game;
    private final GameEventQueue queue;
    private HUD hud;
    public Player player;
    public Enemy enemy;
    private MapGenerator mapGen;

    public GameScreen(Game game, GameEventQueue queue) {
        this.game = game;
        this.queue = queue;
    }

    @Override
    public void show() {
        mapGen = new MapGenerator();
        mapGen.createMap(Services.MAP_WIDTH, Services.MAP_HEIGHT, 32);

        player = Player.create(queue);
        enemy = Enemy.getInstance();

        hud = new HUD();

        Services.setCameraLimits(Services.MAP_WIDTH, Services.MAP_HEIGHT);

        Services.engine.addSystem(new PlayerSystem(queue));
        Services.engine.addSystem(new PlayerInputSystem(1));
        Services.engine.addSystem(new HealthbarSystem());
        Services.engine.addSystem(new LevelSystem(queue));
        Services.engine.addSystem(new HUDSystem(hud, queue, 10));
        Services.engine.addSystem(new RenderSystem());
        Services.engine.addSystem(new MovementSystem(2));
        Services.engine.addSystem(new AnimationControlSystem());
        Services.engine.addSystem(new ColliderRenderSystem());
        Services.engine.addSystem(new EnemyPathfindingSystem());

    }

    @Override
    public void render(float delta) {

        // Curat ecranul inainte sa desenez noul frame
        gl.glClearColor(0.696f, 0.733f, 0.780f, 1f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Services.batch.begin();
        mapGen.render();
        Services.batch.end();

        queue.add(new PlayerXPGainEvent(1));

        Services.engine.update(Gdx.graphics.getDeltaTime());

        player.snapCamera();

        Services.batch.setProjectionMatrix(Services.camera.combined);

        Services.shapeRenderer.setProjectionMatrix(Services.camera.combined);

        hud.render();

        queue.clear();
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
