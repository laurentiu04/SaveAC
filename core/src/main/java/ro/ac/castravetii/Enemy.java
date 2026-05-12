package ro.ac.castravetii;

import ro.ac.castravetii.components.*;
import com.badlogic.ashley.core.Entity;

public class Enemy {

    protected TransformComponent transformC;
    protected Entity entity;
    protected TextureComponent textureC;
    protected EnemyComponent enemyC;
    protected HealthComponent healthC;
    protected MovementComponent movementC;
    protected AnimationComponent animationC;

    public Enemy() {
        // creare entitate inamic
        entity = Services.engine.createEntity();

        // transform component pentru pozitie
        transformC = new TransformComponent();
        transformC.origin.x = 0.5f;
        entity.add(transformC);

        // componenta pentru textura
        textureC = new TextureComponent();
        entity.add(textureC);

        // initializare viata - IMPORTANT: trebuie adaugata la entitate
        healthC = new HealthComponent();
        healthC.maxHealth = 200;
        healthC.currentHealth = 200;
        healthC.showHealthbar = false;
        entity.add(healthC); // adaugam componenta de sanatate

        // componenta specifica pentru inamic (damage etc)
        enemyC = new EnemyComponent();
        enemyC.damage = 20;
        entity.add(enemyC);

        // miscare inamic
        movementC = new MovementComponent();
        movementC.speed = 50f;
        entity.add(movementC);

        // animatie
        animationC = new AnimationComponent();
        entity.add(animationC);

        // adaugare entitate finalizata in engine
        Services.engine.addEntity(entity);
    }

    public TransformComponent getTransformComponent() {
        return transformC;
    }
}
