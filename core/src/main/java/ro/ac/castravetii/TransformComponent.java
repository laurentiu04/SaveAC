package ro.ac.castravetii;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public final class TransformComponent implements Component {
    public Vector2 position = new Vector2(0f, 0f);
    public Vector2 scale = new Vector2(1f, 1f);
    public float rotation = 0;
}
