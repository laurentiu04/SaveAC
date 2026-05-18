package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Componenta pentru a stoca animatiile si starea animației curente a unei entitati.
 *
 *
 * @see ro.ac.castravetii.Utils#createAnimation(int, float, String) createAnimation();
 */
public class SpriteAnimationComponent implements Component {
    /**
     * Starea animatiei ce se va afisa.
     */
    public AnimState state = AnimState.IDLE;
    /** Variabila pentru a stoca cat timp a trecut de cand a inceput animatia
    * pentru a calcula ce frame din animatie trebuie afisat.
     */
    public float elapsedAnimTime = 0f;
    /**
     * Durata totală a animatiei.
     */
    public float animationDuration = 150f;
    /**
     * TextureRegion ce va fi folosită pentru starea idle.
     */
    public TextureRegion idleSprite;
    /**
     * Animația ce va fi folosită in starea {@code AnimState.MOVING.}
     * <br>
     * <br>
     * <b>Note:</b> Pentru starea {@code AnimState.MOVING_BACK} se va folosi această animație
     * dar în direcție opusă.
     */
    public Animation<TextureRegion> movingAnim;
    /**
     * Animația ce va fi folosită in starea {@code AnimState.ATTACKING}.
     */
    public Animation<TextureRegion> attackingAnim;
    /**
     * Animația ce va fi folosită în starea {@code AnimState.DYING}.
     */
    public Animation<TextureRegion> dyingAnim;
    /**
     * Animația ce va fi folosită în starea {@code AnimState.TAKE_DAMAGE}.
     */
    public Animation<TextureRegion> takeDamageAnim;
}
