package ro.ac.castravetii;
import ro.ac.castravetii.components.*;
// TODO: @Andrei Creare Enemy

import com.badlogic.ashley.core.Entity;
import ro.ac.castravetii.components.EnemyComponent;
import ro.ac.castravetii.components.TextureComponent;

// am nevoie ca al meu Enemy:
/*
    - sa fie creat (import design ul de l am facut pentru Enemy)
    - am nevoie de atribute viata
    - sistem de orientare (este deja implementat MovementComponent \ System)
    - am nevoie de animatie
 */


public class Enemy {

    private static Enemy INSTANCE = null;

    TransformComponent enemyTC;

    /* TODO:
        Andrei, sterge partea de singleton pentru ca o sa fie mai multi inamici!
        Pentru fiecare tip nou de inamic va trebui sa extinzi clasa asta si sa schimbi datele din componente!
    */

    //Singleton design pattern : create an Enemy object
    public static Enemy  getInstance (){
        //If enemy was not created then create an enemy
        if(INSTANCE == null) {
            INSTANCE = new Enemy();
        }

        return INSTANCE;
    }
    // Private hidden constructor
    private Enemy() {

        //Creating an Enemy entity
        Entity entityEnemy = Services.engine.createEntity();

        enemyTC = new TransformComponent();
        enemyTC.position.x = 300;
        enemyTC.position.y = 300;
        entityEnemy.add(enemyTC);

        //Creating texture component for my Enemy object
        TextureComponent texture = new TextureComponent();
        texture.region = Services.textureAtlas.findRegion("Pepper"); //TODO fa design la enemy si adauga l aici .png
        entityEnemy.add(texture);

        //Creating a new EnemyComponent with 2 attributes health & damage that are initialized
        EnemyComponent enemyC = new EnemyComponent();
        HealthComponent health = new HealthComponent();
        health.maxHealth = 200;
        enemyC.damage = 20;
        entityEnemy.add(enemyC);

        //MovementComponent for Enemy : The attributes are going to have smaller values because I want my Enemy to be slower than Player
        //TODO pentru viitor pot implementa o functie care sa adapteze movement ul in timp real
        //TODO astfel incat enemy sa poata "sa" primeasca un upgrade cand Player a primit si el upgrade
        MovementComponent movement = new MovementComponent();
        movement.speed = 50f;
        entityEnemy.add(movement);

        //Added new AnimationComponent for my Enemy : that AnimationComponent is responsible for visual effects of Enemy design
//        AnimationComponent animation = new AnimationComponent();
//        animation.movingAnim = Utils.createAnimation(64,0.045f, "nume_enemy_animatie"); // adaugi animatia la enemy si i modifici parametrii ;
        // ATENTIE FRAMESIZE TREBUIE SA AIBA ACELEASI DIMENSIUNI CA DESIGN UL INITIAL PE CARE L FOLOSESTI IN texture.region
//        animation.idleSprite = texture.region;
//        entityEnemy.add(animation);

        Services.engine.addEntity(entityEnemy);

    }
}
