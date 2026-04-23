package ro.ac.castravetii;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    Rectangle hitbox; // Hitbox-ul bullet-ului
    float angle; // Directia bullet-ului
    float time; // Cat timp exista bullet-ul
    int speed; // Viteza bullet
    Vector2 velocity; //
    Texture bullet_texture; // Textura bullet
    Vector2 pos; // Pozitia bullet

    boolean active; // Daca bullet-ul mai exista

    public Bullet(int x, int y, float angle) {
        this.angle = angle;
        this.time = 2f;
        this.speed = 400;
        this.active = true;
        this.pos = new Vector2(x, y);
        this.hitbox = new Rectangle(x, y, 10, 10);
        this.bullet_texture = new Texture("badlogic.jpg");

        this.velocity = new Vector2(
            MathUtils.cos(angle) * speed,
            MathUtils.sin(angle) * speed
        );
    }

    public void update(float delta) {
        if (!active) return;

        // Miscare bullet
        pos.x += velocity.x * delta;
        pos.y += velocity.y * delta;

        // Hotbox-ul sa fie dupa bullet
        hitbox.setPosition(pos.x, pos.y);

        // Numarare timp existenta
        time -= delta;
        if (time <= 0) {
            active = false;
        }
    }

    public void render(SpriteBatch batch) {
        if (!active) return;
        batch.draw(bullet_texture, pos.x, pos.y, 10, 10);
    }

    public void dispose() {
        bullet_texture.dispose();
    }

    public boolean isActive() {
        return active;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
