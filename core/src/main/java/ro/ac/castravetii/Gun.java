package ro.ac.castravetii;

import com.badlogic.ashley.core.Entity;
import ro.ac.castravetii.components.GunComponent;
import ro.ac.castravetii.components.TextureComponent;
import ro.ac.castravetii.components.TransformComponent;

public class Gun {
    private final TextureComponent textureC;
    private final TransformComponent transformC;

    public Gun() {
        Entity entity = Services.engine.createEntity();
        Services.engine.addEntity(entity);

        textureC = Services.engine.createComponent(TextureComponent.class);
        textureC.region = Services.textureAtlas.findRegion("pistoleta");
        textureC.layer = 2;
        entity.add(textureC);

        GunComponent gunC = Services.engine.createComponent(GunComponent.class);
        entity.add(gunC);

        transformC = Services.engine.createComponent(TransformComponent.class);
        entity.add(transformC);
    }

    public TransformComponent getTransformComponent() {
        return transformC;
    }

    public TextureComponent getTextureComponent() {
        return textureC;
    }
}
