package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;

public class BulletComponent implements Component {

    // Timpul de viață al glonțului (pentru a șterge după X secunde)
    public float lifeTime = 5.0f;

    // Statusul glonțului
    public boolean active = true;

    // damage
    public int damage = 200;

    // Constructor gol (recomandat pentru Ashley)
    public BulletComponent() {}
}
