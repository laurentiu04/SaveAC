package ro.ac.castravetii;

import ro.ac.castravetii.components.PolygonColliderComponent;
import ro.ac.castravetii.components.TransformComponent;

public class PepperEnemy extends Enemy{

    private final Knife knife;

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

        movementC.speed = 70f;

        PolygonColliderComponent collider = Services.engine.createComponent(PolygonColliderComponent.class);
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

        knife = new Knife();
        knife.getComponent(TransformComponent.class).parent = transformC;
        knife.getComponent(TransformComponent.class).position.set(-2f, 8f);

        this.add(collider);
    }

    @Override
    public void die() {
        super.die();

        Services.engine.removeEntity(knife);
        textureC .region = Services.textureAtlas.findRegion("Pepper-dead");
    }

    public Knife getKnife() {
        return knife;
    }

}
