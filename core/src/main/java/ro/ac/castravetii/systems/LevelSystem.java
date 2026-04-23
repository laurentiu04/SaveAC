package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ro.ac.castravetii.components.LevelComponent;

public class LevelSystem extends IteratingSystem {
    ComponentMapper<LevelComponent> lm = ComponentMapper.getFor(LevelComponent.class);

    public LevelSystem() {
        super(Family.all(LevelComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        LevelComponent levelComponent = lm.get(entity);

        if (levelComponent.xp >= levelComponent.levelUpTarget && levelComponent.level < levelComponent.maxLevel) {
            levelUp(levelComponent);
        }
    }

    private void levelUp(LevelComponent levelComponent) {
        levelComponent.level++;

        if (levelComponent.level != levelComponent.maxLevel) {
            levelComponent.xp -= levelComponent.levelUpTarget;
            levelComponent.levelUpTarget = levelComponent.levelWeight + (int)Math.pow(levelComponent.level, 2);
        }

        /** TODO:
         * Sistem de upgrade pentru player (ceva pop-up cu 3 optiuni poate
         * din care player-ul sa aleaga una permanenta.
         * */
    }
}
