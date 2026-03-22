package ro.ac.castravetii;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Sistem de randare a tuturor entitatilor de au componenta de tip TextureComponent
 * @author Laurentiu
 */
public class RenderSystem extends IteratingSystem {
    ComponentMapper<TextureComponent> txm = ComponentMapper.getFor(TextureComponent.class);
    ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);

    public RenderSystem() {
        super(Family.all(TextureComponent.class, TransformComponent.class).get());
    }

    @Override
    public void update(float delta) {
        Services.batch.begin();
        super.update(delta);
        Services.batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TextureRegion region = txm.get(entity).region;
        TransformComponent transform = tm.get(entity);
        int direction = 1;

        if (mm.has(entity)) {
            direction = mm.get(entity).directionX;
        }

        Services.batch.draw(
            region,
            transform.position.x - region.getRegionWidth()/2.0f,
            transform.position.y - region.getRegionHeight()/2.0f,
            region.getRegionWidth()/2.0f,
            region.getRegionHeight()/2.0f,
            region.getRegionWidth(),
            region.getRegionHeight(),
            transform.scale.x * direction,
            transform.scale.y,
            transform.rotation
        );

//        System.out.println(region.getRegionWidth());
    }
}
