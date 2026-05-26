package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;

public class ProjectileComponent implements Component {

    // Timpul de viață al glonțului (pentru a șterge după X secunde)
    public float lifeTime = 5.0f;

    // Statusul glonțului
    public boolean active = true;

    public boolean isEnemy = false;

    // Constructor gol (recomandat pentru Ashley)
    public ProjectileComponent() {}
}
