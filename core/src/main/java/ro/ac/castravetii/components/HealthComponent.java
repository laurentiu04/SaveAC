package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class HealthComponent implements Component, Pool.Poolable {
    public int maxHealth = 100;
    public int currentHealth = maxHealth;
    public boolean showHealthbar = true;

    @Override
    public void reset() {

    }
}
