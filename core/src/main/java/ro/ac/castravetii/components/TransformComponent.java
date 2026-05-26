package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public final class TransformComponent implements Component, Pool.Poolable {
    public Vector2 position = new Vector2(0f, 0f);
    public Vector2 origin = new Vector2(0f, 0f);
    public Vector2 scale = new Vector2(1f, 1f);
    public float rotation = 0;
    public TransformComponent parent = null;

    @Override
    public void reset() {
        position = new Vector2();
    }

    public Entity getKnife() {
        return null;
    }
}
