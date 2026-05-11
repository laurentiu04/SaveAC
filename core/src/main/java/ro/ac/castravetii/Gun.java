package ro.ac.castravetii;

import com.badlogic.ashley.core.Entity;
import ro.ac.castravetii.components.GunComponent;
import ro.ac.castravetii.components.TextureComponent;
import ro.ac.castravetii.components.TransformComponent;

public class Gun {
    private final TextureComponent textureC;
    private final TransformComponent transformC;
    private final GunComponent gunC;

    public Gun() {
        Entity entity = Services.engine.createEntity();
        Services.engine.addEntity(entity);

        textureC = Services.engine.createComponent(TextureComponent.class);
        textureC.region = Services.textureAtlas.findRegion("pistoleta");
        textureC.layer = 2;
        entity.add(textureC);

        gunC = Services.engine.createComponent(GunComponent.class);
        entity.add(gunC);

        transformC = Services.engine.createComponent(TransformComponent.class);
        transformC.origin.set(0.02f, 0.5f);
        entity.add(transformC);
    }

    public TransformComponent getTransformComponent() {
        return transformC;
    }

    public TextureComponent getTextureComponent() {
        return textureC;
    }

    public GunComponent getGunComponent() {
        return gunC;
    }
}
