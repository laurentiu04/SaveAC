package ro.ac.castravetii;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public final class TransformComponent implements Component {
    public Vector2 position = new Vector2(0f, 0f);
    public float rotation = 0;
}
