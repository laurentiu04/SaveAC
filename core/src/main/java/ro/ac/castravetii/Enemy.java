package ro.ac.castravetii;

// TODO: @Andrei Creare Enemy

import com.badlogic.ashley.core.Entity;

// am nevoie ca al meu Enemy:
/*
    - sa fie creat (import design ul de l am facut pentru Enemy)
    - am nevoie de atribute viata
    - sistem de orientare (este deja implementat MovementComponent \ System)
    - am nevoie de animatie
 */
public class Enemy {
    // Private hidden constructor
    private Enemy() {}

    public static Entity create(){

        //creez o entitate pentru enemy
        Entity entity = Services.engine.createEntity();

        //adaug o textura in entitatea mea enemy
        TextureComponent texture = new TextureComponent();
        texture.region = Services.textureAtlas.findRegion("castravetii");
        entity.add(texture);

        //componenta mea enemy care initializeaza campurile health si damage
        EnemyComponent enemyC = new EnemyComponent();
        enemyC.health = 100;
        enemyC.damage = 20;
        entity.add(enemyC);

        return entity;
    }
}
