package ro.ac.castravetii;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import org.spongepowered.noise.module.source.Perlin;
import ro.ac.castravetii.components.TextureComponent;
import ro.ac.castravetii.components.TransformComponent;
import java.util.Random;

public class MapGenerator implements Disposable {

    TiledMap map;
    TiledMapTileLayer baseLayer;
    TiledMapTileLayer rocksLayer;
    OrthogonalTiledMapRenderer mapRenderer;
    Random rnd;

    TextureAtlas tileAtlas;

    public void createMap(int mapWidth, int mapHeight, int tileSize) {

        Perlin perlin = new Perlin();

        perlin.setSeed(new Random().nextInt(100000));
        perlin.setFrequency(0.5);
        perlin.setOctaveCount(4);
        perlin.setPersistence(0.5);

        //TODO: Generare procedurala a mapei.

        map = new TiledMap();
        baseLayer = new TiledMapTileLayer(mapWidth, mapHeight, tileSize, tileSize);
        rocksLayer = new TiledMapTileLayer(mapWidth, mapHeight, tileSize, tileSize);

        tileAtlas = new TextureAtlas(Gdx.files.internal("atlas/tiles.atlas"));

        rnd = new Random();

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                int ind = rnd.nextInt(3);
                StaticTiledMapTile tile = new StaticTiledMapTile(tileAtlas.findRegion("mid" + (ind + 1)));
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(tile);
                baseLayer.setCell(x, y, cell);

                ind = rnd.nextInt(1000);
                if (ind % 44 == 0 && (ind | 1) > ind) {

                     Entity rock = Services.engine.createEntity();
                     TransformComponent trans = new TransformComponent();
                     trans.position = new Vector3(tileSize*x + tileSize/2f, tileSize*y + tileSize/2f, 0);
                     rock.add(trans);
                    TextureComponent text = new TextureComponent();
                    text.region = tileAtlas.findRegion("rock" + (rnd.nextInt(2) + 1));
                    rock.add(text);
                    Services.engine.addEntity(rock);
                }
            }
        }

        map.getLayers().add(baseLayer);
        map.getLayers().add(rocksLayer);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    public void render() {
       mapRenderer.setView(Services.camera);
       mapRenderer.render();
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();

    }
}
