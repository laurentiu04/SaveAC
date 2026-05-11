package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;

public class EnemyComponent implements Component {
    public int damage = 0;
    public boolean hasHit = false;
    public float attackTimer = 0f;
    public int xpValue = 10;
}
