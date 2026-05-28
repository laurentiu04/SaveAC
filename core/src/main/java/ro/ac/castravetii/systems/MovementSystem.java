package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import ro.ac.castravetii.components.*;

/**
 * Sistem de movement pentru toate entitatile de tip enemy, player, si ce o sa mai fie
 */
public class MovementSystem extends IteratingSystem {

    // Stocare componente necesare
    private final ComponentMapper<MovementComponent> movm = ComponentMapper.getFor(MovementComponent.class);
    private final ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<SpriteAnimationComponent> am = ComponentMapper.getFor(SpriteAnimationComponent.class);

    // Stocare entitati ce trebuie sa se miste.
    public MovementSystem(int priority) {
        super(Family.all(MovementComponent.class, TransformComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = tm.get(entity);
        MovementComponent move = movm.get(entity);
        SpriteAnimationComponent animComp = am.get(entity);

        // Daca o entitate mai are viteza, calculam noua pozitie si o aplicam
        if (move.moveX != 0 || move.moveY != 0 || move.knockbackX != 0 || move.knockbackY != 0) {
            if (animComp != null && animComp.state != AnimState.MOVING) {
                animComp.state = AnimState.MOVING;
            }

            transform.position = new Vector2(
                transform.position.x + ((move.moveX + move.knockbackX) * deltaTime),
                transform.position.y + ((move.moveY + move.knockbackY) * deltaTime)
            );

            //setezi cat de smooth sa fie knockback ul
            move.knockbackX *= 0.85f;
            move.knockbackY *= 0.85f;
        } else {
            if (animComp != null && animComp.state != AnimState.IDLE) {
                animComp.state = AnimState.IDLE;
            }
        }
    }
    }
