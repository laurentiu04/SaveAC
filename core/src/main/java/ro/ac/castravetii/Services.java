package ro.ac.castravetii;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ray3k.stripe.FreeTypeSkin;
import ro.ac.castravetii.systems.SoundSystem;

/**
 * Clasa creata pentru toate variabilele ce ar trebui accesate global in proiect
 * @author Laurentiu (Sefu')
 */
public final class Services {
    /**
     * Engine-ul care se ocupa de entitati
     */
    public static PooledEngine engine;

    /**
     * Camera ortografica care o sa urmareasca player-ul
     */

    public static OrthographicCamera camera;
    public static int mapTileSize = 32;
    public static float cameraZoom = 2f;
    public static float minLimitX;
    public static float maxLimitX;
    public static float minLimitY;
    public static float maxLimitY;

    /**
     * Lot-ul care va grupa toate sprite-urile inainte sa le deseneze
     */
    public static SpriteBatch batch;

    /**
     * Atlas pentru texturi ca sa nu incarcam de mai multe ori o textura
     */
    public static TextureAtlas textureAtlas;

    /**
     * Manager de asset-uri pentru incarcare diferite fisiere si stocare pe parcusul programului
     */
    public static AssetManager assetManager;


    public static OrthogonalTiledMapRenderer tilemapRenderer;

    // Acest ShapeRenderer este pentru desenarea hitbox-urilor.
    public static ShapeRenderer shapeRenderer;

    public static BitmapFont font20;
    public static BitmapFont font15;
    public static Skin skin;

    public static int MAP_WIDTH     = 50;
    public static int MAP_HEIGHT    = 50;

    public static SoundSystem soundSystem;
    /**
     * Functie pentru eliminarea resurselor create
     */

    public static void init() {
        textureAtlas = new TextureAtlas("atlas/VeggieFightSprites.atlas");
        assetManager = new AssetManager();
        engine = new PooledEngine();

        // Iau dimensiunile ecranului
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        // Creez o camera pentru player cu dimensiunile ecranului
        Services.camera = new OrthographicCamera(width, height);
        Services.camera.zoom = 1 / Services.cameraZoom;
        Services.camera.update();

        // Creez batch-ul pentru sprite-uri.
        Services.batch = new SpriteBatch();
        Services.batch.setProjectionMatrix(Services.camera.combined);

        Services.shapeRenderer = new ShapeRenderer();
        Services.shapeRenderer.setProjectionMatrix(Services.camera.combined);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
            Gdx.files.internal("fonts/MILLENNIA.TTF")
        );
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 20;
        params.shadowOffsetX = 2;
        params.shadowOffsetY = 2;
        params.shadowColor = new Color(0, 0, 0, 0.9f); // black, semi-transparent

        font20 = generator.generateFont(params);
        params.size = 15;
        font15 = generator.generateFont(params);
        font20.setColor(Color.SLATE);
        font20.getRegion().getTexture().setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        );

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skins/uiskin.atlas"));
        atlas.getTextures().first().setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        );

        skin = new FreeTypeSkin(Gdx.files.internal("skins/uiskin.json"), atlas);

        soundSystem = new SoundSystem();
    }

    public static void setCameraLimits(int mapWidth, int mapHeight) {
        maxLimitX = (mapWidth*32 - camera.viewportWidth / cameraZoom /2f) - 1;
        minLimitX = camera.viewportWidth / cameraZoom /2;
        maxLimitY = (mapHeight*32 - camera.viewportHeight / cameraZoom /2f) - 32;
        minLimitY = camera.viewportHeight / cameraZoom /2;
    }

    public static void dispose() {
        batch.dispose();
        textureAtlas.dispose();
        assetManager.dispose();
        skin.dispose();
        shapeRenderer.dispose();
        font20.dispose();
        tilemapRenderer.dispose();
    }
}
