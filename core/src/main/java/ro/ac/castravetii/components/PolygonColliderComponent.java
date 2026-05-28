package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * Componentă pentru detectare de coliziuni între entități.
 */
public class PolygonColliderComponent implements Component {
    public float[] vertices = {
        0, -10,
        -10, 10,
        10, 10,
        0, -10,
    };
    public Vector2 offset = new Vector2();
    public boolean show = false;
    public Polygon polygon = new Polygon(vertices);
}
