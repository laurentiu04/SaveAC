package ro.ac.castravetii;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

/**
 * Sistem de movement pentru toate entitatile de tip enemy, player, si ce o sa mai fie
 */
public class MovementSystem extends IteratingSystem {

    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<MovementComponent> movm = ComponentMapper.getFor(MovementComponent.class);
    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

    // TODO: ComponentMapper pentru inamici

    public MovementSystem() {
        super(Family.all(MovementComponent.class, TransformComponent.class).get());
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        if (pm.has(entity)) {
            MovementComponent p_move = movm.get(entity);
            TransformComponent p_trans = tm.get(entity);

            // TODO: Sistem de movement pentru player

            boolean keyPressedX = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D);
            boolean keyPressedY = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.S);

            if (keyPressedX) {

                if ( !Gdx.input.isKeyPressed(Input.Keys.A) || !Gdx.input.isKeyPressed(Input.Keys.D)) {
                    p_move.directionX = Gdx.input.isKeyPressed(Input.Keys.D) ? 1 : -1;
                }

                p_move.velX = Math.clamp(p_move.velX + (p_move.acceleration * p_move.directionX), -p_move.max_vel, p_move.max_vel);

            } else { // Altfel decelereaza
                if (p_move.velX != 0 && p_move.directionX == 1) {
                    p_move.velX = Math.clamp(p_move.velX - p_move.deceleration, 0, p_move.max_vel);
                } else if (p_move.velX != 0 && p_move.directionX == -1){
                    p_move.velX = Math.clamp(p_move.velX + p_move.deceleration, -p_move.max_vel, 0);
                }
            }

            if (keyPressedY) {
                if ( !Gdx.input.isKeyPressed(Input.Keys.W) || !Gdx.input.isKeyPressed(Input.Keys.S)) {
                    p_move.directionY = Gdx.input.isKeyPressed(Input.Keys.W) ? 1 : -1;
                }

                    p_move.velY = Math.clamp(p_move.velY + (p_move.acceleration * p_move.directionY), -p_move.max_vel, p_move.max_vel);

            } else { // Altfel decelereaza
                if (p_move.velY != 0 && p_move.directionY == 1) {
                    p_move.velY = Math.clamp(p_move.velY - p_move.deceleration, 0, p_move.max_vel);
                } else if (p_move.velY != 0 && p_move.directionY == -1){
                    p_move.velY = Math.clamp(p_move.velY + p_move.deceleration, -p_move.max_vel, 0);
                }
            }

                if (p_move.velX != 0 || p_move.velY != 0) {
                    p_trans.position = new Vector2(
                        p_trans.position.x + (p_move.velX * deltaTime),
                        p_trans.position.y + (p_move.velY * deltaTime)
                    );
                System.out.println(p_move.directionX + " " + p_move.velX);
                }


                // TODO: ===============================
            }
            // TODO: Sistem de pathfinding pentru inamici cand o sa fie
        }
    }
