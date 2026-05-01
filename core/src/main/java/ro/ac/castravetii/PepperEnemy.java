package ro.ac.castravetii;

public class PepperEnemy extends Enemy{
    public PepperEnemy(){
        super();
        texture.region = Services.textureAtlas.findRegion("Pepper");

        health.maxHealth = 100;
        enemyC.damage = 20;
        movement.speed = 50f;
    }
}
