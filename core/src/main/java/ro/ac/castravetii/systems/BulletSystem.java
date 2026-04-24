package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ro.ac.castravetii.components.BulletComponent;
import ro.ac.castravetii.components.HealthComponent;
import ro.ac.castravetii.components.TransformComponent;

public class BulletSystem extends IteratingSystem {
    ComponentMapper<BulletComponent> bm = ComponentMapper.getFor(BulletComponent.class);

    public BulletSystem(Family family) {
        super(family);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {


    }
}
