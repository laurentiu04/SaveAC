package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class EnemyComponent implements Component, Pool.Poolable {
    public int damage = 0;
    public boolean hasHit = false;
    @Override
    public void reset() {

    }
}
