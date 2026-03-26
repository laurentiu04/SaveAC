package ro.ac.castravetii;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Stari posibile pentru o animatie
 */
enum AnimState {
    IDLE,
    MOVING,
    ATTACKING,
    DYING,
    TAKE_DAMAGE
}

/**
 * Componenta pentru a stoca animatiile unei entitati.
 * Posibil sa schimb sistemul pe viitor daca nu se potriveste.
 */
public class AnimationComponent implements Component {
    // Starea animatiei
    public AnimState state = AnimState.IDLE;
    // Variabila pentru a stoca cat timp a trecut de cand a inceput animatia
    // pentru a calcula ce frame din animatie trebuie afisat.
    public float elapsedAnimTime = 0f;
    // Textura pentru starea IDLE
    public TextureRegion idleSprite;
    // Animatia pentru starea MOVING
    public Animation<TextureRegion> movingAnim;
    public Animation<TextureRegion> attackingAnim;
    public Animation<TextureRegion> dyingAnim;
    public Animation<TextureRegion> takeDamageAnim;
}
