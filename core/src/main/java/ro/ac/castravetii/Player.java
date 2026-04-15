package ro.ac.castravetii;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ro.ac.castravetii.components.*;
import ro.ac.castravetii.systems.PlayerInputSystem;

/**
 * Container singleton pentru entitatea player
 */
public final class Player {
    /**
     * Variabila pentru a verifica daca a fost instantiata clasa player
     */
    public static Player INSTANCE = null;
    /**
     * Variabila pentru componenta de transform. O declar aici ca sa o pot accesa din snapCamera();
     */

    Entity playerEntity;
    TransformComponent transformComponent;
    TextureComponent textureComponent;
    PlayerComponent playerComponent;
    MovementComponent movementComponent;
    AnimationComponent animationComponent;
    ColliderComponent colliderComponent;
    HealthComponent healthComponent;

    /**
     * Constructor ascuns
     */
    private Player() {
        // Creez o entitate pentru player.
        playerEntity = Services.engine.createEntity();

        // Creez componente pentru player si le atasez la entitate.
        transformComponent = new TransformComponent();
        transformComponent.position.x = 300;
        transformComponent.position.y = 300;
        playerEntity.add(transformComponent);

        textureComponent = new TextureComponent();
        textureComponent.region = Services.textureAtlas.findRegion("castravete");
        playerEntity.add(textureComponent);

        playerComponent = new PlayerComponent();
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

        colliderComponent = new ColliderComponent();
        colliderComponent.height = 43f;
        colliderComponent.with = 18f;
        colliderComponent.offsetX = -colliderComponent.with/2;
        colliderComponent.offsetY = 15f;
        colliderComponent.shape = ColliderShape.ELLIPSE;
//        colliderComponent.show = false;
        playerEntity.add(colliderComponent);

        healthComponent = new HealthComponent();
//        healthComponent.showHealthbar = false;
        playerEntity.add(healthComponent);

        Services.engine.addSystem(new PlayerInputSystem(1));

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
        Vector3 playerPos = Player.INSTANCE.transformComponent.position;

        if (!camPos.epsilonEquals(new Vector2(playerPos.x, playerPos.y + playerPos.z))) {
            Services.camera.position.lerp(new Vector3(playerPos.x, playerPos.y + playerPos.z + 32, 0), 6f * Gdx.graphics.getDeltaTime());
            Services.camera.update();
        }
    }

    public int getHealth() {
        return healthComponent.currentHealth;
    }

    public int getMaxHealth() {
        return healthComponent.maxHealth;
    }

    public void takeDamage(int amount) {
        healthComponent.currentHealth -= amount;
    }

    public void heal(int amount) {
        healthComponent.currentHealth += amount;
    }
}
