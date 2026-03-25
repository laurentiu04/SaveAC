package ro.ac.castravetii;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import static com.badlogic.gdx.Gdx.gl;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Core implements ApplicationListener {

    @Override
    public void create() {

        // >>>>>>>>>>>>>>>>>>>>>> SETUP INITAL <<<<<<<<<<<<<<<<<<<<<<<< //

        // Setez atlasul pentru texturi
        Services.textureAtlas = new TextureAtlas("atlas/textures.atlas");

        // Iau dimensiunile ecranului
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        // Creez o camera pentru player cu dimensiunile ecranului
        Services.camera = new OrthographicCamera(width, height);

        // Fac update la camera (nu stiu de ce trebuie, dar fac).
        Services.camera.update();

        // Creez batch-ul pentru sprite-uri.
        Services.batch = new SpriteBatch();

        // Incarc textura de castravete in assetManager.
        Services.assetManager = new AssetManager();

        // Creez engine-ul care o sa se ocupe de gestionarea sistemelor si al entitatilor
        Services.engine = new PooledEngine();
        // Ii bag un sistem de randare care se ocupa cu randarea tuturor entitatilor
        Services.engine.addSystem(new RenderSystem());
        // Adaug si sistemul de miscare
        Services.engine.addSystem(new MovementSystem());
        Services.engine.addSystem(new PlayerInputSystem(0));

        // ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ //

        // Creez un player
        Player.create();
        // Testez daca functioneaza singleton-ul clasei player
        Player.create();
    }

    @Override
    public void resize(int width, int height) {

        // Cand se modifica dimensiunea ferestrei, modific si dimensiunea vederii camerei.
        Services.camera.viewportWidth = width;
        Services.camera.viewportHeight = height;

        // Fac update la camera ca sa ia noile dimensiuni
        Services.camera.update();
    }

    @Override
    public void render() {
        // Curat ecranul inainte sa desenez noul frame
        gl.glClearColor(0.396f, 0.333f, 0.380f, 1f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Incep partea de desenare a frame-ului
        // Intre begin() si end() se deseneaza toate elementele, altfel ele nu o sa apara pe ecran.

        Services.engine.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
        public void dispose() {
        // Fac dispose la tot ce am creat, ii gen delete() din C
        Services.dispose();
    }

}
