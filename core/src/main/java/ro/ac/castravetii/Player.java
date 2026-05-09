package ro.ac.castravetii;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ro.ac.castravetii.components.*;
import ro.ac.castravetii.events.*;

/**
 * Container singleton pentru entitatea player
 */
public final class  Player {
    /**
     * Variabila pentru a verifica daca a fost instantiata clasa player
     */
    private static Player INSTANCE = null;

    private final Entity playerEntity;
    private final TransformComponent transformComponent;
    private final TextureComponent textureComponent;
    private final PlayerComponent playerComponent;
    private final PlayerStatsComponent statsComponent;
    private final MovementComponent movementComponent;
    private final AnimationComponent animationComponent;
    private final EllipseColliderComponent colliderComponent;
    private final HealthComponent healthComponent;
    private final LevelComponent levelComponent;
    private final Gun gun;

    /**
     * Constructor ascuns
     */
    private Player() {

        // Creez o entitate pentru player.
        playerEntity = Services.engine.createEntity();

        playerComponent = new PlayerComponent();
        playerEntity.add(playerComponent);

        statsComponent = new PlayerStatsComponent();
        playerEntity.add(statsComponent);

        // Creez componente pentru player si le atasez la entitate.
        transformComponent = new TransformComponent();
        transformComponent.position.x = Services.MAP_WIDTH*16;
        transformComponent.position.y = Services.MAP_HEIGHT*16;
        transformComponent.origin.x = 0.52f;
        transformComponent.origin.y = 0.5f;
        playerEntity.add(transformComponent);

        textureComponent = new TextureComponent();
        textureComponent.region = Services.textureAtlas.findRegion("castravete");
        playerEntity.add(textureComponent);

        movementComponent = new MovementComponent();
        movementComponent.speed = 100f;
        playerEntity.add(movementComponent);

        animationComponent = new AnimationComponent();
        animationComponent.movingAnim = Utils.createAnimation(64, 0.035f, "castravete-moving");
        animationComponent.idleSprite = textureComponent.region;
        playerEntity.add(animationComponent);

        colliderComponent = new EllipseColliderComponent();
        colliderComponent.height = 44f;
        colliderComponent.width = 18f;
        colliderComponent.offset.x = -colliderComponent.width/2;
        colliderComponent.offset.y = -20f;
//        colliderComponent.show = true;
        playerEntity.add(colliderComponent);

        healthComponent = new HealthComponent();
        playerEntity.add(healthComponent);

        levelComponent = new LevelComponent();
        playerEntity.add(levelComponent);

        gun = new Gun();

        // Adaug entitatea la engine.
        Services.engine.addEntity(playerEntity);
    }

    /**
     * Metoda pentru crearea instantei singleton a clasei Player.
     */
    @SuppressWarnings("UnusedReturnValue")
    public static Player create() {

        // Daca a fost creat deja un player, intoarce null.
        if (INSTANCE != null) {
            return null;
        }

        // Marchez crearea player-ului.
        INSTANCE = new Player();

        Services.camera.translate(INSTANCE.transformComponent.position.x, INSTANCE.transformComponent.position.y);
        return INSTANCE;
    }

    //modificari facute de ANDREI, ARE NEVOIE LA Enemy pentru ai
    public static Player getInstance(){
        return INSTANCE;
    }

    public TransformComponent  getTransformComponent(){
        return transformComponent;
    }

    public void snapCamera() {

        Vector2 camPos = new Vector2(Services.camera.position.x, Services.camera.position.y);
        Vector2 playerPos = Player.INSTANCE.transformComponent.position;

        Vector3 newCamPos = new Vector3(playerPos.x, playerPos.y, 0);

        if (playerPos.x > Services.maxLimitX) newCamPos.x = Services.maxLimitX;
        else if (playerPos.x < Services.minLimitX) newCamPos.x = Services.minLimitX;
        if (playerPos.y > Services.maxLimitY) newCamPos.y = Services.maxLimitY;
        else if (playerPos.y < Services.minLimitY) newCamPos.y = Services.minLimitY;

        if (!camPos.epsilonEquals(new Vector2(newCamPos.x, newCamPos.y))) {
            Services.camera.position.lerp(newCamPos, 6f * Gdx.graphics.getDeltaTime());
            Services.camera.update();
        }
    }

    public HealthComponent getHealthComponent() {
        return this.healthComponent;
    }

    public PlayerStatsComponent getPlayerStats() {
        return this.statsComponent;
    }

    public LevelComponent getLevelComponent() {
        return this.levelComponent;
    }

    public MovementComponent getMovementComponent() {
        return movementComponent;
    }

    public Gun getGun() { return gun; }

    public TextureComponent getTextureComponent() {
        return textureComponent;
    }

    public Entity getEntity() {
        return playerEntity;
    }
}
