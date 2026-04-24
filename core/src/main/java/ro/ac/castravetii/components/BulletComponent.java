package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BulletComponent  implements Component {
    Rectangle hitbox; // Hitbox-ul bullet-ului
    float angle; // Directia bullet-ului
    float time; // Cat timp exista bullet-ul
    int speed; // Viteza bullet
    Vector2 velocity; //
    Texture bullet_texture; // Textura bullet
    Vector2 pos; // Pozitia bullet-ului

    boolean active; // Daca bullet-ul mai exista

}
