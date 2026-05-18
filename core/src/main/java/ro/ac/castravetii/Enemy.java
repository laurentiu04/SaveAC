package ro.ac.castravetii;

import ro.ac.castravetii.components.*;
import ro.ac.castravetii.animations.*;
import com.badlogic.ashley.core.Entity;

public class Enemy {

    protected TransformComponent transformC;
    protected Entity entity;
    protected TextureComponent textureC;
    protected EnemyComponent enemyC;
    protected HealthComponent healthC;
    protected MovementComponent movementC;
    protected SpriteAnimationComponent animationC;
    protected RotationAnimation wobbleAnim;
    protected OpacityAnimation deathAnim;

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
        healthC.showHealthbar = true;
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
        animationC = new SpriteAnimationComponent();
        entity.add(animationC);

        // Animatie rotatie
        wobbleAnim = new RotationAnimation(transformC, -5f, 5f, 0.75f, CubicBezier.EASE_IN_OUT);
        wobbleAnim.setFillMode(FillMode.FORWARDS);
        wobbleAnim.setPlayMode(PlayMode.PING_PONG);
        wobbleAnim.play();

        deathAnim = new OpacityAnimation(textureC, 1f, 0, 1f, CubicBezier.EASE_OUT);
        deathAnim.setFillMode(FillMode.FORWARDS);

        // adaugare entitate finalizata in engine
        Services.engine.addEntity(entity);
    }

    public TransformComponent getTransformComponent() {
        return transformC;
    }
}
