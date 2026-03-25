package ro.ac.castravetii;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

enum AnimState {
    IDLE,
    MOVING,
    ATTACKING,
    DYING,
    TAKE_DAMAGE
}

public class AnimationComponent implements Component {
    public AnimState state;
    public TextureRegion idleAnim;
    public TextureRegion movingAnim;
    public TextureRegion attackingAnim;
    public TextureRegion dyingAnim;
    public TextureRegion takeDamageAnim;
}
