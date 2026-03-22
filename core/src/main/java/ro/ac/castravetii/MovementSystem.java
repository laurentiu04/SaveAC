package ro.ac.castravetii;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

/**
 * Sistem de movement pentru toate entitatile de tip enemy, player, si ce o sa mai fie
 */
public class MovementSystem extends IteratingSystem {

    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    // TODO: ComponentMapper pentru inamici

    public MovementSystem() {
        super(Family.one(PlayerComponent.class).get());
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // TODO: Sistem de movement pentru player

        // TODO: Sistem de pathfinding pentru inamici cand o sa fie
    }
}
