package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.Utils;
import ro.ac.castravetii.components.*;

/**
 * Sistem de randare a tuturor entitatilor de au componenta de tip TextureComponent
 * @author Laurentiu
 */
public class RenderSystem extends SortedIteratingSystem {

    ComponentMapper<TextureComponent> txm = ComponentMapper.getFor(TextureComponent.class);
    ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);

    public RenderSystem(int priority) {
        super(
            Family.all(TextureComponent.class, TransformComponent.class).get(),
            (o1, o2) -> {
                TransformComponent t1 = ComponentMapper.getFor(TransformComponent.class).get(o1);
                TransformComponent t2 = ComponentMapper.getFor(TransformComponent.class).get(o2);
                TextureComponent tx1 = ComponentMapper.getFor(TextureComponent.class).get(o1);
                TextureComponent tx2 = ComponentMapper.getFor(TextureComponent.class).get(o2);

                return (int)(t2.position.y * tx1.layer - t1.position.y * tx2.layer);
            }, priority
        );
    }

    @Override
    public void update(float delta) {
        forceSort(); // Re-sorteaza entitatile la fiecare frame
        super.update(delta);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TextureComponent texture = txm.get(entity);
        TextureRegion region = texture.region;
        TransformComponent transform = tm.get(entity);
        MovementComponent move = mm.get(entity);

        if (move != null && move.moveX != 0 && entity.getComponent(PlayerComponent.class) == null) {
            if (texture.flippedX && move.moveX > 0) {
                texture.flippedX = false;
                if (entity.getComponent(EnemyComponent.class) != null){
                    Utils.flipCollider(entity);
                }
            } else if (!texture.flippedX && move.moveX < 0) {
                texture.flippedX = true;
                if (entity.getComponent(EnemyComponent.class) != null) {
                    Utils.flipCollider(entity);
                }
            }
        }

        if (entity.getComponent(GunComponent.class) != null) {
            //noinspection SuspiciousNameCombination
            texture.flippedY = Player.getInstance().getTextureComponent().flippedX;
        }

        Services.batch.begin();
        Services.batch.draw(
            region,
            transform.position.x - region.getRegionWidth() * transform.origin.x,
            transform.position.y- region.getRegionWidth() * transform.origin.y,
            region.getRegionWidth()*transform.origin.x,
            region.getRegionWidth()*transform.origin.y,
            region.getRegionWidth(),
            region.getRegionHeight(),
            texture.flippedX ? -1f : 1f,
            texture.flippedY ? -1f : 1f,
            transform.rotation
        );
        Services.batch.end();

        Services.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Services.shapeRenderer.setColor(Color.BLACK);
        Services.shapeRenderer.circle(transform.position.x, transform.position.y, 2);
        Services.shapeRenderer.setColor(Color.ORANGE);
        Services.shapeRenderer.circle(transform.position.x, transform.position.y, 1);
        Services.shapeRenderer.end();
    }
}


