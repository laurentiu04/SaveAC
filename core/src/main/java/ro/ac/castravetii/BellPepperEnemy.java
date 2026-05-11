package ro.ac.castravetii;

import ro.ac.castravetii.components.PolygonColliderComponent;

public class BellPepperEnemy extends Enemy{

    public BellPepperEnemy(){
        super();
        //modificati fratilor aici sa nu mai fie glontele
        texture.region = Services.textureAtlas.findRegion("BellPepper");

        animation.movingAnim = Utils.createAnimation(48, 0.05f, "BellPepper-moving");
        animation.idleSprite = texture.region;
        animation.animationDuration = 50f;

        health.maxHealth = 80;
        health.currentHealth = 80;

        enemyC.damage = 20;

        movement.speed = 30f;

        enemyTC.origin.set(0.5f, 0.2f);
        PolygonColliderComponent collider = Services.engine.createComponent(PolygonColliderComponent.class);
        collider.vertices = new float[]{
            3,  0,   // bottom-left
            19, 0,   // bottom-right
            22, 3,   // right-bottom
            22, 19,  // right-top
            19, 22,  // top-right
            3,  22,  // top-left
            0,  19,  // left-top
            0,  3,   // left-bottom
        };
        collider.polygon.setOrigin(11, 11);
        collider.offset.set(-12, 10);
//        collider.show = true;
        entityEnemy.add(collider);
    }
}
