package ro.ac.castravetii;

public class PepperEnemy extends Enemy{
    public PepperEnemy(){
        super();
        texture.region = Services.textureAtlas.findRegion("Pepper");
        animation.movingAnim = Utils.createAnimation(32, 0.05f, "Pepper-moving");
        animation.idleSprite = texture.region;
        animation.animationDuration = 50f;
        health.maxHealth = 100;
        enemyC.damage = 20;
        movement.speed = 50f;
    }
}
