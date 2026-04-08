package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;
import ro.ac.castravetii.components.AnimState;
import ro.ac.castravetii.components.AnimationComponent;
import ro.ac.castravetii.components.MovementComponent;
import ro.ac.castravetii.components.TransformComponent;

/**
 * Sistem de movement pentru toate entitatile de tip enemy, player, si ce o sa mai fie
 */
public class MovementSystem extends IteratingSystem {

    // Stocare componente necesare
    private final ComponentMapper<MovementComponent> movm = ComponentMapper.getFor(MovementComponent.class);
    private final ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);

    // TODO: ComponentMapper pentru inamici

    // Stocare entitati ce trebuie sa se miste.
    public MovementSystem(int priority) {
        super(Family.all(MovementComponent.class, TransformComponent.class).get(), priority);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        TransformComponent transform = tm.get(entity);
        MovementComponent move = movm.get(entity);
        AnimationComponent animComp = am.get(entity);

        // Daca o entitate mai are viteza, calculam noua pozitie si o aplicam
        if (move.velX != 0 || move.velY != 0 || move.velZ != 0) {
            if (animComp != null && animComp.state != AnimState.MOVING) {
                animComp.state = AnimState.MOVING;
            }

            transform.position = new Vector3(
                transform.position.x + (move.velX * deltaTime),
                transform.position.y + (move.velY * deltaTime),
                transform.position.z + (move.velZ * deltaTime)
            );
        } else {
            if (animComp != null && animComp.state != AnimState.IDLE) {
                animComp.state = AnimState.IDLE;
            }
        }
    }
    }
