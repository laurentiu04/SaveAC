package ro.ac.castravetii;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Null;

/**
 * Container singleton pentru entitatea player
 */
public final class Player {
    private static boolean INSTANCE = false;

    private Player() {}

    @SuppressWarnings("UnusedReturnValue")
    public static Entity create() {
        if (INSTANCE) {
            return null;
        }

        INSTANCE = true;
        Entity playerEntity = Services.engine.createEntity();

        // Creez componente pentru player si le atasez la entitate.
        TransformComponent transform = new TransformComponent();
        transform.position = new Vector2(
            Services.camera.viewportWidth/2f,
            Services.camera.viewportHeight/2f
        );

        transform.scale = new Vector2(3f, 3f);
        playerEntity.add(transform);

        TextureComponent text = new TextureComponent();
        text.region = Services.textureAtlas.findRegion("castravete");
        playerEntity.add(text);

        PlayerComponent p = new PlayerComponent();
        p.health = 250;
        playerEntity.add(p);

        MovementComponent move = new MovementComponent();
        playerEntity.add(move);

        Services.engine.addEntity(playerEntity);

        return playerEntity;
    }
}
