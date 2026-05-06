package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public class BulletComponent implements Component {
    // Hitbox-ul pentru detecția coliziunilor
    public Rectangle hitbox = new Rectangle();

    // Direcția viteza de deplasare
    public Vector2 velocity = new Vector2();

    // Viteza scalara (cat de repede zboara)
    public float speed = 400f;

    // Unghiul la care a fost tras (pentru rotația sprite-ului)
    public float angle;

    // Timpul de viață al glonțului (pentru a șterge după X secunde)
    public float lifeTime = 2.0f;

    // Statusul glonțului
    public boolean active = true;

    // Constructor gol (recomandat pentru Ashley)
    public BulletComponent() {}
}
