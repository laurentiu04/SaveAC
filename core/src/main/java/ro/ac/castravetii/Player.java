package ro.ac.castravetii;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

/**
 * Container singleton pentru entitatea player
 */
public final class Player {
    /**
     * Variabila pentru a verifica daca a fost instantiata clasa player
     */
    private static boolean INSTANCE = false;

    /**
     * Constructor ascuns
     */
    private Player() {}

    /**
     * Metoda pentru crearea instantei singleton a clasei Player.
     * @return Instanta singleton Player cu componente atasate.
     */
    @SuppressWarnings("UnusedReturnValue")
    public static Entity create() {

        // Daca a fost creat deja un player, intoarce null.
        if (INSTANCE) {
            return null;
        }

        // Marchez crearea player-ului.
        INSTANCE = true;

        // Creez o entitate pentru player.
        Entity playerEntity = Services.engine.createEntity();

        // Creez componente pentru player si le atasez la entitate.
        TransformComponent transform = new TransformComponent();
        transform.position = new Vector2(
            Services.camera.viewportWidth/2f,
            Services.camera.viewportHeight/2f
        );

        transform.scale = new Vector2(3f, 3f); // Setez scalarea
        playerEntity.add(transform);

        TextureComponent text = new TextureComponent();
        text.region = Services.textureAtlas.findRegion("rosie");
        playerEntity.add(text);

        PlayerComponent p = new PlayerComponent();
        p.health = 250;
        playerEntity.add(p);

        MovementComponent move = new MovementComponent();
        move.max_vel = 450f;
        move.acceleration = move.max_vel * 0.085f; // 8.5% din viteza maxima
        move.deceleration = move.max_vel * 0.05f; // 5% din viteza maxima
        playerEntity.add(move);

        AnimationComponent anim = new AnimationComponent();
        anim.movingAnim = Utils.createAnimation(64, 0.055f, "castravete-moving");
        anim.idleSprite = text.region;
//        playerEntity.add(anim);

        // Adaug entitatea la engine.
        Services.engine.addEntity(playerEntity);

        return playerEntity;
    }
}
