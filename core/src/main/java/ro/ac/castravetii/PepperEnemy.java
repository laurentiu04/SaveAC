package ro.ac.castravetii;

import ro.ac.castravetii.components.PolygonColliderComponent;

public class PepperEnemy extends Enemy{

    private final PolygonColliderComponent collider;
    private Knife knife;

    public PepperEnemy(){
        super();
        textureC.region = Services.textureAtlas.findRegion("Pepper");
        transformC.origin.set(0.5f, 0.20f);

        animationC.movingAnim = Utils.createAnimation(32, 0.05f, "Pepper-moving");
        animationC.idleSprite = textureC.region;
        animationC.animationDuration = 50f;

        healthC.maxHealth = 40;
        healthC.currentHealth = 40;
        enemyC.xpValue = 10;

        movementC.speed = 40f;

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
        collider.offset.set(-12f, 0f);
        collider.polygon.setOrigin(12f, 16f);
//        collider.show = true;
        entity.add(collider);
    }

}
