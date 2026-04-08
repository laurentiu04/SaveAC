package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.ColliderComponent;
import ro.ac.castravetii.components.ColliderShape;
import ro.ac.castravetii.components.TransformComponent;

public class HitboxSystem extends IteratingSystem {
    ComponentMapper<ColliderComponent> bcm = ComponentMapper.getFor(ColliderComponent.class);
    ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

    public HitboxSystem() {
        super(Family.all(TransformComponent.class).get());
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ColliderComponent collider = bcm.get(entity);
        Vector3 position = tm.get(entity).position;

        if (collider != null) {
            Vector2 newHitboxPos = new Vector2(position.x + collider.offsetX, position.y + position.z + collider.offsetY);

            if (collider.show) {
                Services.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                Services.shapeRenderer.setColor(Color.WHITE);
                if (collider.shape == ColliderShape.BOX) {
                    Services.shapeRenderer.rect(newHitboxPos.x, newHitboxPos.y, collider.with, collider.height);
                } else {
                    Services.shapeRenderer.ellipse(newHitboxPos.x, newHitboxPos.y, collider.with, collider.height);
                }
                Services.shapeRenderer.end();
            }
        }
    }
}
