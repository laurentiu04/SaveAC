package ro.ac.castravetii;

import ro.ac.castravetii.components.PolygonColliderComponent;

public class TomatoEnemy extends Enemy{

    public TomatoEnemy() {
        super();
        textureC.region = Services.textureAtlas.findRegion("rosie");

        animationC.movingAnim = Utils.createAnimation(64, 0.05f, "rosie-moving");
        animationC.idleSprite = textureC.region;
        animationC.animationDuration = 50f;

        healthC.maxHealth = 100;
        healthC.currentHealth = 100;

        enemyC.damage = 40;
        enemyC.xpValue = 40;
        movementC.speed = 60f;

        transformC.origin.set(0.5f, 0.2f);

        PolygonColliderComponent collider = Services.engine.createComponent(PolygonColliderComponent.class);
        collider.vertices = new float[]{
            6,  0,   // bottom-left
            16, 0,   // bottom-right
            22, 6,   // right-bottom
            22, 16,  // right-top
            16, 22,  // top-right
            6,  22,  // top-left
            0,  16,  // left-top
            0,  6,   // left-bottom
        };
        collider.polygon.setOrigin(11, 11);
        collider.offset.set(-12, 7);
        entity.add(collider);
    }
}
