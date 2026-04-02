package ro.ac.castravetii;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Container singleton pentru entitatea player
 */
public final class Player {
    /**
     * Variabila pentru a verifica daca a fost instantiata clasa player
     */
    private static Player INSTANCE = null;
    /**
     * Variabila pentru componenta de transform. O declar aici ca sa o pot accesa din snapCamera();
     */

    Entity playerEntity;
    TransformComponent transformComponent;
    TextureComponent textureComponent;
    PlayerComponent playerComponent;
    MovementComponent movementComponent;
    AnimationComponent animationComponent;

    /**
     * Constructor ascuns
     */
    private Player() {
        // Creez o entitate pentru player.
        playerEntity = Services.engine.createEntity();

        // Creez componente pentru player si le atasez la entitate.
        transformComponent = new TransformComponent();
        transformComponent.position = new Vector2(
            Services.camera.viewportWidth/2f,
            Services.camera.viewportHeight/2f
        );

        playerEntity.add(transformComponent);

        textureComponent = new TextureComponent();
        textureComponent.region = Services.textureAtlas.findRegion("castravete");
        playerEntity.add(textureComponent);

        playerComponent = new PlayerComponent();
        playerComponent.health = 250;
        playerEntity.add(playerComponent);

        movementComponent = new MovementComponent();
        movementComponent.max_vel = 150f;
        movementComponent.acceleration = movementComponent.max_vel * 0.085f; // 8.5% din viteza maxima
        movementComponent.deceleration = movementComponent.max_vel * 0.05f; // 5% din viteza maxima
        playerEntity.add(movementComponent);

        animationComponent = new AnimationComponent();
        animationComponent.movingAnim = Utils.createAnimation(64, 0.045f, "castravete-moving");
        animationComponent.idleSprite = textureComponent.region;
        playerEntity.add(animationComponent);

        // Adaug entitatea la engine.
        Services.engine.addEntity(playerEntity);
    }

    /**
     * Metoda pentru crearea instantei singleton a clasei Player.
     */
    @SuppressWarnings("UnusedReturnValue")
    public static void create() {

        // Daca a fost creat deja un player, intoarce null.
        if (INSTANCE != null) {
            return;
        }

        // Marchez crearea player-ului.
        INSTANCE = new Player();
    }

    public static void snapCamera() {
        Vector2 camPos = new Vector2(Services.camera.position.x, Services.camera.position.y);
        Vector2 playerPos = Player.INSTANCE.transformComponent.position;

        if (!camPos.epsilonEquals(playerPos)) {
            Services.camera.position.lerp(new Vector3(playerPos.x, playerPos.y, 0), 6f * Gdx.graphics.getDeltaTime());
            Services.camera.update();

            System.out.println("Player pos: " + playerPos);
            System.out.println("Camera pos: " + camPos);
        }
    }
}
