package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ro.ac.castravetii.components.AIComponent;

public class AISystem extends IteratingSystem {
    public AISystem(){
        super(Family.all(AIComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AIComponent aiComp = entity.getComponent(AIComponent.class);
        aiComp.AI.update(deltaTime);
    }
}
