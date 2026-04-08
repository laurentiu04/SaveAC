package ro.ac.castravetii;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import org.spongepowered.noise.module.source.Perlin;

import java.util.Random;

public class MapGenerator {

    public static void createMap() {

        Perlin perlin = new Perlin();

        perlin.setSeed(new Random().nextInt(100000));
        perlin.setFrequency(0.5);
        perlin.setOctaveCount(4);
        perlin.setPersistence(0.5);

        //TODO: Generare procedurala a mapei.

        Services.tilemapRenderer = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("maps/base_map.tmx"));
    }

    public static void render() {
        Services.tilemapRenderer.setView(Services.camera);
        Services.tilemapRenderer.render();
    }

    public static void disposeMap() {
        Services.tilemapRenderer.dispose();
    }


}
