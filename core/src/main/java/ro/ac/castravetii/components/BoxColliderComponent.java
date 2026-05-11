package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Componentă pentru detectare de coliziuni între entități.
 */
public class BoxColliderComponent implements Component {
    public int width = 100;
    public int height = 100;
    public Vector2 offset = new Vector2();
    public boolean show = false;
    public Rectangle rect = new Rectangle();
}
