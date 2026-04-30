package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Componenta pentru movement.
 * @author Laurentiu
 */
public class MovementComponent implements Component, Pool.Poolable {
    public float moveX = 0f;
    public float moveY = 0f;
    public float speed = 150f;
    public float inputX = 0f;
    public float inputY = 0f;

    @Override
    public void reset() {

    }
}
