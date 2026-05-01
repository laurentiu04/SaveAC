package ro.ac.castravetii;

public class BellPepperEnemy extends Enemy{

    public BellPepperEnemy(){
        super();
        //modificati fratilor aici sa nu mai fie glontele
        texture.region = Services.textureAtlas.findRegion("bullet");

        health.maxHealth = 200;
        enemyC.damage = 45;
        movement.speed = 60f;
    }
}
