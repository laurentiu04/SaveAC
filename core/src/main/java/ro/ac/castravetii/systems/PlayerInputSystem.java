package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ro.ac.castravetii.components.MovementComponent;
import ro.ac.castravetii.components.PlayerComponent;

public class PlayerInputSystem extends IteratingSystem {
    ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);

    public PlayerInputSystem(int priority) {
        super(Family.all(PlayerComponent.class, MovementComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent moveComp = mm.get(entity); // Iau componenta pentru Movement

        // Vad daca am apasat pe una dintre tastele W, A, S, D
        float inputX = (Gdx.input.isKeyPressed(Input.Keys.D) ? 1 : 0)
            - (Gdx.input.isKeyPressed(Input.Keys.A) ? 1 : 0);
        float inputY = (Gdx.input.isKeyPressed(Input.Keys.W) ? 1 : 0)
            - (Gdx.input.isKeyPressed(Input.Keys.S) ? 1 : 0);

        if (inputX != 0) {
            moveComp.directionX = (int)inputX;
        }

        float length = (float) Math.sqrt(inputX * inputX + inputY * inputY);
        if (length > 0) {
            inputX /= length;
            inputY /= length;
        }

        if (inputX != 0) { // Acelerez

            // Adaug sau scad valoarea acceleratiei la viteza, in functie de directie
            // Folosesc clamp() ca sa nu depasesc viteza maxima a player-ului
            moveComp.velX = Math.clamp(
                moveComp.velX + (moveComp.acceleration * inputX),
                -moveComp.max_vel, moveComp.max_vel
            );

        } else { // Decelerez

            // Daca inca am viteza, scad sau adaug valoarea decelerarii in functie de directie
            if (moveComp.velX > 0) {
                moveComp.velX = Math.clamp(moveComp.velX - moveComp.deceleration, 0, moveComp.max_vel);
            } else if (moveComp.velX < 0){
                moveComp.velX = Math.clamp(moveComp.velX + moveComp.deceleration, -moveComp.max_vel, 0);
            }
        }

        // Jos este acelasi lucru, numai ca pentru axa verticala

        if (inputY != 0) {
            if ( !Gdx.input.isKeyPressed(Input.Keys.W) || !Gdx.input.isKeyPressed(Input.Keys.S)) {
                moveComp.directionY = Gdx.input.isKeyPressed(Input.Keys.W) ? 1 : -1;
            }

            moveComp.velY = Math.clamp(
                moveComp.velY + (moveComp.acceleration * inputY),
                -moveComp.max_vel, moveComp.max_vel
            );

        } else { // Altfel decelereaza
            if (moveComp.velY > 0) {
                moveComp.velY = Math.clamp(moveComp.velY - moveComp.deceleration, 0, moveComp.max_vel);
            } else if (moveComp.velY < 0){
                moveComp.velY = Math.clamp(moveComp.velY + moveComp.deceleration, -moveComp.max_vel, 0);
            }
        }

    }
}
