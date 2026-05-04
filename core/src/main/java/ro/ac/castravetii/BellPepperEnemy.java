package ro.ac.castravetii;

public class BellPepperEnemy extends Enemy{

    public BellPepperEnemy(){
        super();
        //modificati fratilor aici sa nu mai fie glontele
        texture.region = Services.textureAtlas.findRegion("BellPepper");
        animation.movingAnim = Utils.createAnimation(48, 0.05f, "BellPepper-moving");
        animation.idleSprite = texture.region;
        animation.animationDuration = 50f;
        health.maxHealth = 200;
        enemyC.damage = 45;
        movement.speed = 60f;
    }
}
