package ro.ac.castravetii;

import com.badlogic.ashley.core.Entity;
import ro.ac.castravetii.components.*;

public class Bullet extends Entity{

    public Bullet() {
        Services.engine.addEntity(this);

        TextureComponent textureC = Services.engine.createComponent(TextureComponent.class);
        textureC.region = Services.textureAtlas.findRegion("bullet");
        this.add(textureC);

        PolygonColliderComponent collider = Services.engine.createComponent(PolygonColliderComponent.class);
        collider.vertices = new float[]{
            0, 0,
            7, 0,
            7, 3,
            0, 3,
            0, 0
        };
        collider.polygon.setOrigin(-4, 1.5f);
        collider.offset.set(4, -1.5f);
//        collider.show = true;

        this.add(collider);

        BulletComponent bulletC = Services.engine.createComponent(BulletComponent.class);
        this.add(bulletC);

        TransformComponent transformC = Services.engine.createComponent(TransformComponent.class);
        transformC.origin.set(0f, 0.5f);
        this.add(transformC);

        MovementComponent movementC = Services.engine.createComponent(MovementComponent.class);
        movementC.speed = 400f;

        this.add(movementC);
    }
}
