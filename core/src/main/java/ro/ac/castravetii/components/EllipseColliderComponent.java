package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;

/**
 * Componentă pentru detectare de coliziuni între entități.
 */
public class EllipseColliderComponent implements Component {
    public float width = 100;
    public float height = 100;
    public Vector2 offset = new Vector2();
    public boolean show = false;
    public Ellipse ellipse = new Ellipse();
}
