package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

/**
 * Componenta pentru a stoca animatiile unei entitati.
 * Posibil sa schimb sistemul pe viitor daca nu se potriveste.
 */
public class AnimationComponent implements Component, Pool.Poolable {
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

    @Override
    public void reset() {

    }
}
