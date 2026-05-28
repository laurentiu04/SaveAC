package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class HealthComponent implements Component, Pool.Poolable {
    public int maxHealth = 100;
    public int currentHealth = maxHealth;
    public boolean showHealthbar = true;

    public float regenTimer = 0f;
    public float regenDelay = 2f;

    @Override
    public void reset() {

    }
}
