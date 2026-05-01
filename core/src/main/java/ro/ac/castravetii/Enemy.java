package ro.ac.castravetii;
import ro.ac.castravetii.components.*;
// TODO: @Andrei Creare Enemy

import com.badlogic.ashley.core.Entity;
import ro.ac.castravetii.components.EnemyComponent;
import ro.ac.castravetii.components.TextureComponent;

public class Enemy {

    protected TransformComponent enemyTC;
    protected Entity entityEnemy;
    protected TextureComponent texture;
    protected EnemyComponent enemyC;
    protected HealthComponent health;
    protected MovementComponent movement;
    protected AnimationComponent animation;

    public Enemy() {

        //Creating an Enemy entity
        entityEnemy = Services.engine.createEntity();

        //Creating a new TransformComponent for later to be used in generating random positions for my Enemies.
        enemyTC = new TransformComponent();
        entityEnemy.add(enemyTC);

        //Creating texture component for my Enemy object
        texture = new TextureComponent();
        entityEnemy.add(texture);

        //Creating a new EnemyComponent with 2 attributes health & damage that are initialized
        health = new HealthComponent();
        health.maxHealth = 200;
        enemyC = new EnemyComponent();
        enemyC.damage = 20;
        entityEnemy.add(enemyC);

        //MovementComponent for Enemy : The attributes are going to have smaller values because I want my Enemy to be slower than Player
        movement = new MovementComponent();
        movement.speed = 50f;
        entityEnemy.add(movement);

        //Added new AnimationComponent for my Enemy : that AnimationComponent is responsible for visual effects of Enemy design
        animation = new AnimationComponent();
        // ATENTIE FRAMESIZE TREBUIE SA AIBA ACELEASI DIMENSIUNI CA DESIGN UL INITIAL PE CARE L FOLOSESTI IN texture.region
        entityEnemy.add(animation);

        Services.engine.addEntity(entityEnemy);

    }

    public TransformComponent getTransformComponent() {
        return enemyTC;
    }
}
