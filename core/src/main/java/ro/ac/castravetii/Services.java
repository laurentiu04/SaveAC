package ro.ac.castravetii;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

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

    /**
     * Functie pentru eliminarea resurselor create
     */
    public static void dispose() {
        if (batch != null) batch.dispose();
        if (textureAtlas != null) textureAtlas.dispose();
        if (assetManager != null) assetManager.dispose();
    }
}
