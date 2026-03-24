package ro.ac.castravetii;

import com.badlogic.ashley.core.Component;

/**
 * Componenta pentru movement.
 * @author Laurentiu
 */
public class MovementComponent implements Component {
    public float max_vel = 300f;
    public float velX = 0f;
    public float velY = 0f;
    public float acceleration = 30f;
    public float deceleration = 20f;
    public int directionX = 1;
    public int directionY = 1;
}
