package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {
    public int maxHealth = 100;
    public int currentHealth = maxHealth;
    public boolean showHealthbar = true;
}
