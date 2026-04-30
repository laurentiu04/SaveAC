package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.MovementComponent;
import ro.ac.castravetii.components.TextureComponent;
import ro.ac.castravetii.components.TransformComponent;

/**
 * Sistem de randare a tuturor entitatilor de au componenta de tip TextureComponent
 * @author Laurentiu
 */
public class RenderSystem extends SortedIteratingSystem {

    ComponentMapper<TextureComponent> txm = ComponentMapper.getFor(TextureComponent.class);
    ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);

    public RenderSystem() {
        super(
            Family.all(TextureComponent.class, TransformComponent.class).get(),
            (o1, o2) -> {
                TransformComponent t1 = ComponentMapper.getFor(TransformComponent.class).get(o1);
                TransformComponent t2 = ComponentMapper.getFor(TransformComponent.class).get(o2);

                return (int)(t2.position.y - t1.position.y);
            }
        );
    }

    @Override
    public void update(float delta) {
        forceSort(); // Re-sorteaza entitatile la fiecare frame
        super.update(delta);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TextureRegion region = txm.get(entity).region;
        TransformComponent transform = tm.get(entity);

        int direction = 1;
        if (mm.has(entity)) {
            direction = mm.get(entity).inputX >= 0 ? 1 : -1;
        }

        Services.batch.begin();
        Services.batch.draw(
            region,
            transform.position.x - region.getRegionWidth()/2.0f,
            transform.position.y,
            region.getRegionWidth()/2f,
            0,
            region.getRegionWidth(),
            region.getRegionHeight(),
            1f * direction,
            1f,
            transform.rotation
        );
        Services.batch.end();
    }
}


