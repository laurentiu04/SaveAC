package ro.ac.castravetii;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class AnimationControlSystem extends IteratingSystem {
    ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);
    ComponentMapper<TextureComponent> tm = ComponentMapper.getFor(TextureComponent.class);
    ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);

    public AnimationControlSystem() {
        super(Family.all(AnimationComponent.class, TextureComponent.class ,MovementComponent.class).get());
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animComp = am.get(entity);
        TextureComponent textComp = tm.get(entity);
        MovementComponent moveComp = mm.get(entity);

        switch (animComp.state) {
            case IDLE -> {
                if (textComp.region != animComp.idleSprite) {
                    animComp.elapsedAnimTime = 0f;
                    textComp.region = animComp.idleSprite;
                }
            }

            case MOVING -> {

                // fac viteza animatiei proportionala cat de cat cu viteza entitatii
                animComp.elapsedAnimTime += ((moveComp.max_vel * 0.00005f) * (Math.max(Math.abs(moveComp.velX), Math.abs(moveComp.velY))/moveComp.max_vel)) * deltaTime * 50f;
                textComp.region = animComp.movingAnim.getKeyFrame(animComp.elapsedAnimTime, true);
            }
        }
    }
}
