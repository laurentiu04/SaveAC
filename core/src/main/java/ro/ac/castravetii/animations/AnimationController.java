package ro.ac.castravetii.animations;

import com.badlogic.ashley.core.EntitySystem;

public class AnimationController extends EntitySystem {

    @Override
    public void update(float delta) {
        for (Animation animation : Animation.collection) {
            if (animation.playing) {
                animation.update(delta);
            }
        }
    }
}
