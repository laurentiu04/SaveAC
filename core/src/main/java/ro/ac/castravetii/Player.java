package ro.ac.castravetii;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ro.ac.castravetii.components.*;
import ro.ac.castravetii.events.*;
import ro.ac.castravetii.systems.PlayerInputSystem;

/**
 * Container singleton pentru entitatea player
 */
public final class Player {
    /**
     * Variabila pentru a verifica daca a fost instantiata clasa player
     */
    private static Player INSTANCE = null;

    Entity playerEntity;
    TransformComponent transformComponent;
    TextureComponent textureComponent;
    PlayerComponent playerComponent;
    PlayerStatsComponent statsComponent;
    MovementComponent movementComponent;
    AnimationComponent animationComponent;
    ColliderComponent colliderComponent;
    HealthComponent healthComponent;
    LevelComponent levelComponent;

    GameEventQueue queue;

    /**
     * Constructor ascuns
     */
    private Player(GameEventQueue queue) {
        this.queue = queue;

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
        playerEntity.add(transformComponent);

        textureComponent = new TextureComponent();
        textureComponent.region = Services.textureAtlas.findRegion("castravete");
        playerEntity.add(textureComponent);

        movementComponent = new MovementComponent();
        movementComponent.max_vel = statsComponent.maxVel;
        movementComponent.acceleration = movementComponent.max_vel * 0.085f; // 8.5% din viteza maxima
        movementComponent.deceleration = movementComponent.max_vel * 0.05f; // 5% din viteza maxima
        playerEntity.add(movementComponent);

        animationComponent = new AnimationComponent();
        animationComponent.movingAnim = Utils.createAnimation(64, 0.035f, "castravete-moving");
        animationComponent.idleSprite = textureComponent.region;
        playerEntity.add(animationComponent);

        colliderComponent = new ColliderComponent();
        colliderComponent.height = 43f;
        colliderComponent.with = 18f;
        colliderComponent.offsetX = -colliderComponent.with/2;
        colliderComponent.offsetY = 15f;
        colliderComponent.shape = ColliderShape.ELLIPSE;
        colliderComponent.type = CollisionType.PLAYER;
        colliderComponent.show = false;
        playerEntity.add(colliderComponent);

        healthComponent = new HealthComponent();
        healthComponent.maxHealth = statsComponent.maxHealth;
        healthComponent.currentHealth = statsComponent.maxHealth;
        playerEntity.add(healthComponent);

        levelComponent = new LevelComponent();
        levelComponent.xpGain = statsComponent.xpGain;
        playerEntity.add(levelComponent);

        Services.engine.addSystem(new PlayerInputSystem(1));

        // Adaug entitatea la engine.
        Services.engine.addEntity(playerEntity);

    }

    /**
     * Metoda pentru crearea instantei singleton a clasei Player.
     */
    @SuppressWarnings("UnusedReturnValue")
    public static Player create(GameEventQueue queue) {

        // Daca a fost creat deja un player, intoarce null.
        if (INSTANCE != null) {
            return null;
        }

        // Marchez crearea player-ului.
        INSTANCE = new Player(queue);

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
        Vector3 playerPos = Player.INSTANCE.transformComponent.position;

        Vector3 newCamPos = new Vector3(playerPos.x, playerPos.y + 32, 0);

        if (playerPos.x > Services.maxLimitX) newCamPos.x = Services.maxLimitX;
        else if (playerPos.x < Services.minLimitX) newCamPos.x = Services.minLimitX;
        if (playerPos.y + 32 > Services.maxLimitY) newCamPos.y = Services.maxLimitY;
        else if (playerPos.y + 32 < Services.minLimitY) newCamPos.y = Services.minLimitY;

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

    public void setMaxHealth(int amount) {
        healthComponent.maxHealth = amount;
    }

    public void takeDamage(int amount) {
        healthComponent.currentHealth -= amount;
    }

    public void heal(int amount) {
        healthComponent.currentHealth += amount;
        queue.add(new PlayerHealEvent(30));
    }

    public LevelComponent getLevelComponent() {
        return this.levelComponent;
    }


}
