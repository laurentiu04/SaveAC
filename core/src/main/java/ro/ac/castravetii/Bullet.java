package ro.ac.castravetii;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    Rectangle hitbox; // Hitbox-ul bullet-ului
    float angle; //
    float time; // Cat timp exista bullet-ul
    int speed; // Viteza bullet
    Texture text; // Textura bullet
    Vector2 pos; // Pozitia bullet

    public Bullet(int x, int y, float angle) {
        time = 2;
        speed = 50;
        hitbox = new Rectangle(x, y, 10, 10);
        text = new Texture("badlogic.jpg");
    }

    public Rectangle getHitbox() {
        return hitbox;
    }


}
