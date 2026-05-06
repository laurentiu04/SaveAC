package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ro.ac.castravetii.components.BulletComponent;
import ro.ac.castravetii.components.TransformComponent;

public class BulletSystem extends IteratingSystem {
    // Mappere pentru acces rapid la componente
    private final ComponentMapper<BulletComponent> bm = ComponentMapper.getFor(BulletComponent.class);
    private final ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

    public BulletSystem() {
        // procesare doar entitati care au și Bullet și Transform
        super(Family.all(BulletComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BulletComponent bullet = bm.get(entity);
        TransformComponent transform = tm.get(entity);

        // miscare
        transform.position.x += bullet.velocity.x * deltaTime;
        transform.position.y += bullet.velocity.y * deltaTime;

        // hitbox
        bullet.hitbox.setPosition(transform.position.x, transform.position.y);

        // lifetime glont
        bullet.lifeTime -= deltaTime;
        if (bullet.lifeTime <= 0 || !bullet.active) {
            getEngine().removeEntity(entity);
        }
    }
}
