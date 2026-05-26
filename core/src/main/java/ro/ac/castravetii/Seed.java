package ro.ac.castravetii;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import ro.ac.castravetii.components.*;

public class Seed extends Entity {

    public Seed(Vector2 spawnPosition, Vector2 targetPosition) {
        Services.engine.addEntity(this);

        // textura
        TextureComponent textureC = Services.engine.createComponent(TextureComponent.class);
        textureC.region = Services.textureAtlas.findRegion("seed");
        this.add(textureC);

        // spawnare seed
        TransformComponent transformC = Services.engine.createComponent(TransformComponent.class);

        transformC.position.set(spawnPosition.x + 11f, spawnPosition.y + 11f);
        transformC.origin.set(0.5f, 0.5f);
        this.add(transformC);

        // calcul directie
        float dx = targetPosition.x - (spawnPosition.x + 11f);
        float dy = targetPosition.y - (spawnPosition.y + 11f);
        float angle = (float) Math.toDegrees(Math.atan2(dy, dx));
        transformC.rotation = angle;

        // movement
        MovementComponent movementC = Services.engine.createComponent(MovementComponent.class);
        movementC.speed = 200f; // Viteza

        float radians = (float) Math.toRadians(angle);
        movementC.moveX = (float) Math.cos(radians) * movementC.speed;
        movementC.moveY = (float) Math.sin(radians) * movementC.speed;
        this.add(movementC);

        // collider
        PolygonColliderComponent collider = Services.engine.createComponent(PolygonColliderComponent.class);
        collider.vertices = new float[]{
            0, 0,
            4, 0,
            4, 4,
            0, 4,
            0, 0
        };
        collider.polygon.setVertices(collider.vertices);
        collider.polygon.setOrigin(2, 2);
        collider.offset.set(-2, -2);
        this.add(collider);

        ProjectileComponent bulletC = Services.engine.createComponent(ProjectileComponent.class);
        bulletC.isEnemy = true;  // Targets the player instead of enemies
        bulletC.lifeTime = 4.0f; // Automatically cleans up after 4 seconds if it misses
        this.add(bulletC);
    }
}
