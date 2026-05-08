package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.*;

public class ColliderRenderSystem extends IteratingSystem {
    ComponentMapper<BoxColliderComponent> bcm = ComponentMapper.getFor(BoxColliderComponent.class);
    ComponentMapper<EllipseColliderComponent> ecm = ComponentMapper.getFor(EllipseColliderComponent.class);
    ComponentMapper<PolygonColliderComponent> pcm = ComponentMapper.getFor(PolygonColliderComponent.class);
    ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

    public ColliderRenderSystem(int priority) {
        super(Family.one(BoxColliderComponent.class, EllipseColliderComponent.class, PolygonColliderComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        TransformComponent transformC = tm.get(entity);
        Vector2 pos = transformC.position;
        float rotation = transformC.rotation;

        Services.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        Services.shapeRenderer.setColor(Color.PURPLE);
        if (bcm.has(entity)) {
            BoxColliderComponent collider = bcm.get(entity);
            collider.rect.set(
                pos.x + collider.offset.x,
                pos.y + collider.offset.y,
                collider.width,
                collider.height
            );

            if (collider.show) {
                Services.shapeRenderer.rect(
                    pos.x + collider.offset.x,
                    pos.y + collider.offset.y,
                    collider.width,
                    collider.height
                );
            }
        } else if (ecm.has(entity)) {
            EllipseColliderComponent collider = ecm.get(entity);

            collider.ellipse.set(
                pos.x + collider.offset.x,
                pos.y + collider.offset.y,
                collider.width,
                collider.height
            );

            if (collider.show) {
                Services.shapeRenderer.ellipse(
                    pos.x + collider.offset.x,
                    pos.y + collider.offset.y,
                    collider.width,
                    collider.height
                );
            }
        } else {
            PolygonColliderComponent collider = pcm.get(entity);

            collider.polygon.setVertices(collider.vertices);
            collider.polygon.setPosition(
                pos.x + collider.offset.x,
                pos.y + collider.offset.y);
            collider.polygon.setRotation(rotation);

            if (collider.show) {
                Services.shapeRenderer.polygon(collider.polygon.getTransformedVertices());
            }
        }

        Services.shapeRenderer.end();
    }
}
