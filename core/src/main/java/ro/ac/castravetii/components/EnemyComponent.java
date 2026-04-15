package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class EnemyComponent implements Component, Pool.Poolable {
    public int health = 0;
    public int damage = 0;

    @Override
    public void reset() {

    }
}
