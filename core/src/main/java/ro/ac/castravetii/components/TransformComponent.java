package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

public final class TransformComponent implements Component {
    public Vector3 position = new Vector3(0f, 0f, 0f);
    public float rotation = 0;
}
