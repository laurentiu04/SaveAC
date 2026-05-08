package ro.ac.castravetii;

import ro.ac.castravetii.components.PolygonColliderComponent;

public class PepperEnemy extends Enemy{

    public PolygonColliderComponent collider;

    public PepperEnemy(){
        super();
        texture.region = Services.textureAtlas.findRegion("Pepper");
        animation.movingAnim = Utils.createAnimation(32, 0.05f, "Pepper-moving");
        animation.idleSprite = texture.region;
        animation.animationDuration = 50f;
        health.maxHealth = 100;
        enemyC.damage = 20;
        movement.speed = 50f;

        collider = Services.engine.createComponent(PolygonColliderComponent.class);
        collider.vertices = new float[]{
            // clockwise from bottom-right stem tip
            20,  0,   // stem tip (bottom-right)
            17,  4,   // stem base right
            14,  6,   // body lower-right
            10, 18,   // top-right shoulder
            7, 20,   // tip top
            4, 18,   // top-left shoulder
            3, 12,   // body left upper
            5,  4,   // body left lower
            10,  0,   // body bottom-left
            16,  0,   // body bottom-right
        };

        collider.offset.set(-12f, 8f);
        collider.polygon.setOrigin(12f, 16f);
        collider.show = true;
        entityEnemy.add(collider);
    }
}
