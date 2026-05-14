package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.HealthComponent;
import ro.ac.castravetii.components.PlayerComponent;
import ro.ac.castravetii.components.TransformComponent;
import ro.ac.castravetii.events.GameEventQueue;


public class HealthbarSystem extends IteratingSystem {
    ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

    public HealthbarSystem(GameEventQueue queue, int priority) {
        super(Family.all(HealthComponent.class).exclude(PlayerComponent.class) .get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector2 pos = tm.get(entity).position;
        HealthComponent health = hm.get(entity);
        int healthBarWidth = 30;

        if (health.showHealthbar) {
            Services.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            Services.shapeRenderer.setColor(Color.RED);
            Services.shapeRenderer.rect(pos.x - healthBarWidth/2f, pos.y - 5, healthBarWidth * ((float) health.currentHealth / health.maxHealth), 2);
            Services.shapeRenderer.end();
        }
    }
}
