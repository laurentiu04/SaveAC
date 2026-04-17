package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Componenta pentru movement.
 * @author Laurentiu
 */
public class MovementComponent implements Component, Pool.Poolable {
    public float max_vel = 200f;
    public float velX = 0f;
    public float velY = 0f;
    public float velZ = 0f;
    public boolean isFalling = false;
    public float acceleration = 25f;
    public float deceleration = 20f;
    public int directionX = 1;
    public int directionY = 1;

    @Override
    public void reset() {

    }
}
