package ro.ac.castravetii;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerInputSystem extends IteratingSystem {
    ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);


    public PlayerInputSystem(int priority) {
        super(Family.one(PlayerComponent.class, MovementComponent.class).get(), priority);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent moveComp = mm.get(entity); // Iau componenta pentru Movement


        // Vad daca am apasat pe una dintre tastele W, A, S, D
        boolean keyPressedX = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D);
        boolean keyPressedY = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.S);

        if (keyPressedX) { // Acelerez

            // Aflu daca am apasat pe A sau pe D si setez directia in functie de tasta apasata
            // Daca ambele taste sunt apasate, trec peste setare si raman cu directia initiala
            if ( !Gdx.input.isKeyPressed(Input.Keys.A) || !Gdx.input.isKeyPressed(Input.Keys.D)) {
                moveComp.directionX = Gdx.input.isKeyPressed(Input.Keys.D) ? 1 : -1;
            }

            // Adaug sau scad valoarea acceleratiei la viteza, in functie de directie
            // Folosesc clamp() ca sa nu depasesc viteza maxima a player-ului
            moveComp.velX = Math.clamp(moveComp.velX + (moveComp.acceleration * moveComp.directionX), -moveComp.max_vel, moveComp.max_vel);

        } else { // Decelerez

            // Daca inca am viteza, scad sau adaug valoarea decelerarii in functie de directie
            if (moveComp.velX > 0) {
                moveComp.velX = Math.clamp(moveComp.velX - moveComp.deceleration, 0, moveComp.max_vel);
            } else if (moveComp.velX < 0){
                moveComp.velX = Math.clamp(moveComp.velX + moveComp.deceleration, -moveComp.max_vel, 0);
            }
        }

        // Jos este acelasi lucru, numai ca pentru axa verticala

        if (keyPressedY) {
            if ( !Gdx.input.isKeyPressed(Input.Keys.W) || !Gdx.input.isKeyPressed(Input.Keys.S)) {
                moveComp.directionY = Gdx.input.isKeyPressed(Input.Keys.W) ? 1 : -1;
            }

            moveComp.velY = Math.clamp(moveComp.velY + (moveComp.acceleration * moveComp.directionY), -moveComp.max_vel, moveComp.max_vel);

        } else { // Altfel decelereaza
            if (moveComp.velY > 0) {
                moveComp.velY = Math.clamp(moveComp.velY - moveComp.deceleration, 0, moveComp.max_vel);
            } else if (moveComp.velY < 0){
                moveComp.velY = Math.clamp(moveComp.velY + moveComp.deceleration, -moveComp.max_vel, 0);
            }
        }

        }
    }
