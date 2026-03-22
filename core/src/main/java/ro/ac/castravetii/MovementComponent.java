package ro.ac.castravetii;

import com.badlogic.ashley.core.Component;

public class MovementComponent implements Component {
    public float max_vel = 640f;
    public float velX = 0f;
    public float velY = 0f;
    public float acceleration = 40f;
    public float deceleration = 20f;
    public int directionX = 1;
    public int directionY = 1;
}
