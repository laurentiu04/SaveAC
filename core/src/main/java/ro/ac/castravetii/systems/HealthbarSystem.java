package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.Utils;
import ro.ac.castravetii.components.HealthComponent;
import ro.ac.castravetii.components.PlayerComponent;
import ro.ac.castravetii.components.TransformComponent;
import ro.ac.castravetii.events.GameEventQueue;


public class HealthbarSystem extends IteratingSystem {
    ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    final Texture healthBarFrame;
    final Texture healthBarFill;

    public HealthbarSystem(Game game, int priority) {
        super(Family.all(HealthComponent.class).exclude(PlayerComponent.class) .get(), priority);

        healthBarFrame = new Texture(Utils.getInternalPath("sprites/enemy_healthbar_frame.png"));
        healthBarFill = new Texture(Utils.getInternalPath("sprites/enemy_healthbar_fill.png"));
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector2 pos = tm.get(entity).position;
        HealthComponent health = hm.get(entity);
        float healthPercent = (float) health.currentHealth / health.maxHealth;

        if (health.showHealthbar && healthPercent < 1f) {

            Services.batch.draw(
                healthBarFrame,
                pos.x - 16, pos.y - 10,
                32, 4
            );
            Services.batch.draw(
                healthBarFill,
                pos.x - 16, pos.y - 10,
                32 * healthPercent, 4
            );

        }
    }
}
