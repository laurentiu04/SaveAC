package ro.ac.castravetii;

import ro.ac.castravetii.components.*;
import com.badlogic.ashley.core.Entity;

public class Enemy {

    protected TransformComponent enemyTC;
    protected Entity entityEnemy;
    protected TextureComponent texture;
    protected EnemyComponent enemyC;
    protected HealthComponent health;
    protected MovementComponent movement;
    protected AnimationComponent animation;

    public Enemy() {
        // creare entitate inamic
        entityEnemy = Services.engine.createEntity();

        // transform component pentru pozitie
        enemyTC = new TransformComponent();
        enemyTC.origin.x = 0.5f;
        entityEnemy.add(enemyTC);

        // componenta pentru textura
        texture = new TextureComponent();
        entityEnemy.add(texture);

        // initializare viata - IMPORTANT: trebuie adaugata la entitate
        health = new HealthComponent();
        health.maxHealth = 200;
        health.currentHealth = 200;
        health.showHealthbar = false;
        entityEnemy.add(health); // adaugam componenta de sanatate

        // componenta specifica pentru inamic (damage etc)
        enemyC = new EnemyComponent();
        enemyC.damage = 20;
        entityEnemy.add(enemyC);

        // miscare inamic
        movement = new MovementComponent();
        movement.speed = 50f;
        entityEnemy.add(movement);

        // animatie
        animation = new AnimationComponent();
        entityEnemy.add(animation);

        // adaugare entitate finalizata in engine
        Services.engine.addEntity(entityEnemy);
    }

    public TransformComponent getTransformComponent() {
        return enemyTC;
    }
}
