package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ro.ac.castravetii.components.SpriteAnimationComponent;
import ro.ac.castravetii.components.MovementComponent;
import ro.ac.castravetii.components.TextureComponent;

public class AnimationControlSystem extends IteratingSystem {
    ComponentMapper<SpriteAnimationComponent> am = ComponentMapper.getFor(SpriteAnimationComponent.class);
    ComponentMapper<TextureComponent> tm = ComponentMapper.getFor(TextureComponent.class);
    ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);

    public AnimationControlSystem() {
        super(Family.all(SpriteAnimationComponent.class, TextureComponent.class ,MovementComponent.class).get());
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SpriteAnimationComponent animComp = am.get(entity);
        TextureComponent textComp = tm.get(entity);
        MovementComponent moveComp = mm.get(entity);

        switch (animComp.state) {
            case IDLE -> {
                if (textComp.region != animComp.idleSprite && animComp.idleSprite != null) {
                    animComp.elapsedAnimTime = 0f;
                    textComp.region = animComp.idleSprite;
                }
            }

            case MOVING -> {
                // Daca nu a fost atribuita nici o regiune pentru animatie, ne intoarcem;
                if (animComp.movingAnim == null) {
                    return;
                }

                /* fac viteza animatiei proportionala cat de cat cu viteza entitatii
                    Pentru o viteza mai mare a entitatii, durata frame-ului scade incat sa
                    treaca mai rapid de la frame la frame
                 */

                float speedFactor = moveComp.speed/animComp.animationDuration;
                if(animComp.elapsedAnimTime < animComp.movingAnim.getAnimationDuration()) {
                    animComp.elapsedAnimTime += deltaTime * speedFactor;
                } else {
                    animComp.elapsedAnimTime -= animComp.movingAnim.getAnimationDuration();
                }

                if ((moveComp.moveX > 0 && !textComp.flippedX) || (moveComp.moveX < 0 && textComp.flippedX))
                    textComp.region = animComp.movingAnim.getKeyFrame(animComp.elapsedAnimTime, true);
                else
                    textComp.region = animComp.movingAnim.getKeyFrame(animComp.movingAnim.getAnimationDuration() - animComp.elapsedAnimTime, true);
            }
        }
    }
}
