package ro.ac.castravetii;

import com.badlogic.ashley.core.Entity;
import ro.ac.castravetii.animations.CubicBezier;
import ro.ac.castravetii.animations.FillMode;
import ro.ac.castravetii.animations.OpacityAnimation;
import ro.ac.castravetii.animations.RotationAnimation;
import ro.ac.castravetii.components.KnifeComponent;
import ro.ac.castravetii.components.TextureComponent;
import ro.ac.castravetii.components.TransformComponent;

public class Knife extends Entity {

    public final RotationAnimation attackAnimation;
    public final OpacityAnimation despawnAnimation;

    public Knife() {
        super();

        TextureComponent textureC = Services.engine.createComponent(TextureComponent.class);
        textureC.region = Services.textureAtlas.findRegion("enemy_knife");
        this.add(textureC);

        TransformComponent transformC = Services.engine.createComponent(TransformComponent.class);
        transformC.origin.set(0.5f, 1f);
        transformC.rotation  = 25f;
        this.add(transformC);

        attackAnimation = new RotationAnimation(transformC, 0f, 25f, 1f, new CubicBezier(0.75f, -2f, 0.48f, 1.04f));
        despawnAnimation = new OpacityAnimation(textureC, 1f, 0f, 1f, CubicBezier.EASE_OUT);
        despawnAnimation.setFillMode(FillMode.FORWARDS);
        despawnAnimation.setDelay(3f);

        KnifeComponent knifeC = Services.engine.createComponent(KnifeComponent.class);
        this.add(knifeC);
        Services.engine.addEntity(this);
    }
}
