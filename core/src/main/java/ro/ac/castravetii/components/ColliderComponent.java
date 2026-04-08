package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class ColliderComponent implements Component {
    public float with = 10f;
    public float height = 10f;
    public float offsetX = 0f;
    public float offsetY = 0f;
    public CollisionType type = CollisionType.OTHER;
    public ColliderShape shape = ColliderShape.BOX;
    public boolean show = true;
}
