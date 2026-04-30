package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public final class TransformComponent implements Component, Pool.Poolable {
    public Vector2 position = new Vector2(0f, 0f);
    public float rotation = 0;

    @Override
    public void reset() {
        position = new Vector2();
    }
}
