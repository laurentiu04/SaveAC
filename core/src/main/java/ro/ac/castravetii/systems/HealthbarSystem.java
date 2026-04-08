package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.HealthComponent;
import ro.ac.castravetii.components.TransformComponent;


public class HealthbarSystem extends IteratingSystem {
    ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

    public HealthbarSystem() {
        super(Family.all(HealthComponent.class).get());
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector3 pos = tm.get(entity).position;
        HealthComponent health = hm.get(entity);
        int healthbarWidth = 30;

        if (health.showHealthbar) {
            Services.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            Services.shapeRenderer.setColor(Color.RED);
            Services.shapeRenderer.rect(pos.x - healthbarWidth/2f, pos.y, healthbarWidth * ((float) health.currentHealth / health.maxHealth), 2);
            Services.shapeRenderer.end();
        }
    }
}
