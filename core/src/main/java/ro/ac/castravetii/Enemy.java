package ro.ac.castravetii;

import ro.ac.castravetii.components.*;
import ro.ac.castravetii.animations.*;
import com.badlogic.ashley.core.Entity;

public class Enemy extends Entity{

    protected TransformComponent transformC;
    protected TextureComponent textureC;
    protected EnemyComponent enemyC;
    protected HealthComponent healthC;
    protected MovementComponent movementC;
    protected SpriteAnimationComponent animationC;
    protected RotationAnimation wobbleAnim;
    protected OpacityAnimation deathAnim;

    public Enemy() {
        // creare entitate inamic
        super();

        // transform component pentru pozitie
        transformC = new TransformComponent();
        transformC.origin.x = 0.5f;
        this.add(transformC);

        // componenta pentru textura
        textureC = new TextureComponent();
        this.add(textureC);

        // initializare viata - IMPORTANT: trebuie adaugata la entitate
        healthC = new HealthComponent();
        healthC.maxHealth = 200;
        healthC.currentHealth = 200;
        healthC.showHealthbar = true;
        this.add(healthC); // adaugam componenta de sanatate

        // componenta specifica pentru inamic (damage etc)
        enemyC = new EnemyComponent();
        enemyC.damage = 20;
        this.add(enemyC);

        // miscare inamic
        movementC = new MovementComponent();
        movementC.speed = 50f;
        this.add(movementC);

        // animatie
        animationC = new SpriteAnimationComponent();
        this.add(animationC);

        // Animatie rotatie
        wobbleAnim = new RotationAnimation(transformC, -5f, 5f, 0.75f, CubicBezier.EASE_IN_OUT);
        wobbleAnim.setFillMode(FillMode.FORWARDS);
        wobbleAnim.setPlayMode(PlayMode.PING_PONG);
        wobbleAnim.play();

        deathAnim = new OpacityAnimation(textureC, 1f, 0, 1f, CubicBezier.EASE_OUT);
        deathAnim.setFillMode(FillMode.FORWARDS);
        deathAnim.setDelay(3f);

        // adaugare entitate finalizata in engine
        Services.engine.addEntity(this);
    }

    public void die() {
        this.remove(EnemyComponent.class);
        this.remove(MovementComponent.class);
        this.remove(HealthComponent.class);

        wobbleAnim.end();
        deathAnim.play();
    }

}
