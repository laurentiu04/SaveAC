package ro.ac.castravetii;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ro.ac.castravetii.animations.CubicBezier;
import ro.ac.castravetii.animations.PlayMode;
import ro.ac.castravetii.animations.RotationAnimation;
import ro.ac.castravetii.animations.ScaleXAnimation;
import ro.ac.castravetii.components.*;

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
    private final SpriteAnimationComponent animationComponent;
    private final PolygonColliderComponent colliderComponent;
    private final HealthComponent healthComponent;
    private final LevelComponent levelComponent;
    private final Gun gun;
    public final ScaleXAnimation stunAnimationScale;
    public final RotationAnimation stunAnimationRotation;

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
        transformComponent.origin.set(0.52f, 0.20f);
        playerEntity.add(transformComponent);

        textureComponent = new TextureComponent();
        textureComponent.region = Services.textureAtlas.findRegion("castravete");
        playerEntity.add(textureComponent);

        movementComponent = new MovementComponent();
        movementComponent.speed = 100f;
        playerEntity.add(movementComponent);

        animationComponent = new SpriteAnimationComponent();
        animationComponent.movingAnim = Utils.createAnimation(64, 0.035f, "castravete-moving");
        animationComponent.idleSprite = textureComponent.region;
        playerEntity.add(animationComponent);

        colliderComponent = new PolygonColliderComponent();
        colliderComponent.vertices = new float[] {
            16.00f,  0.00f,
            11.31f, 25.00f,
            0.00f, 35.36f,
            -11.31f, 25.00f,
            -16.00f,  0.00f,
            -11.31f,-25.00f,
            0.00f,-35.36f,
            11.31f,-25.00f,
        };
//        colliderComponent.offset.x = -colliderComponent.;
        colliderComponent.offset.y = -20f;
        colliderComponent.show = true;
        playerEntity.add(colliderComponent);

        healthComponent = new HealthComponent();
        playerEntity.add(healthComponent);

        levelComponent = new LevelComponent();
        playerEntity.add(levelComponent);

        gun = new Gun();
        gun.getTransformComponent().parent = transformComponent;
        gun.getTransformComponent().position.set(-4, 20);

        stunAnimationScale = new ScaleXAnimation(transformComponent, 0.9f, 1.1f, 1f, CubicBezier.EASE_IN_OUT);
        stunAnimationScale.setPlayMode(PlayMode.PING_PONG);
        stunAnimationScale.setDelay(0.25f);

        stunAnimationRotation = new RotationAnimation(transformComponent, 5f, -5f, 1f, CubicBezier.EASE_IN_OUT);
        stunAnimationRotation.setPlayMode(PlayMode.PING_PONG);

        // Adaug entitatea la engine.
        Services.engine.addEntity(playerEntity);
    }

    // Resetam playerul
    public static void resetInstance() {
        INSTANCE = null;
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
        Vector3 tagetPos = new Vector3(playerPos.x, playerPos.y + textureComponent.region.getRegionHeight()*transformComponent.origin.y, 0f);

        if (playerPos.x > Services.maxLimitX) tagetPos.x = Services.maxLimitX;
        else if (playerPos.x < Services.minLimitX) tagetPos.x = Services.minLimitX;
        if (playerPos.y > Services.maxLimitY) tagetPos.y = Services.maxLimitY;
        else if (playerPos.y < Services.minLimitY) tagetPos.y = Services.minLimitY;

        if (!camPos.epsilonEquals(new Vector2(tagetPos.x, tagetPos.y))) {
            Services.camera.position.lerp(tagetPos, 6f * Gdx.graphics.getDeltaTime());
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
