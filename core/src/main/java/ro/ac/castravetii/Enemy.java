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

        // collider pentru detectia coliziunilor cu gloantele
        ColliderComponent collider = new ColliderComponent();
        collider.with = 28;
        collider.height = 28;
        collider.offsetX = -12;
        collider.offsetY = 5;
        entityEnemy.add(collider); // adaugam collider-ul inainte de a adauga entitatea in engine

        // adaugare entitate finalizata in engine
        Services.engine.addEntity(entityEnemy);
    }

    public TransformComponent getTransformComponent() {
        return enemyTC;
    }
}
