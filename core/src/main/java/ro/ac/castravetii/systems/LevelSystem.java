package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ro.ac.castravetii.components.LevelComponent;
import ro.ac.castravetii.components.PlayerComponent;
import ro.ac.castravetii.components.PlayerStatsComponent;
import ro.ac.castravetii.events.GameEventQueue;
import ro.ac.castravetii.events.PlayerEvent;

public class LevelSystem extends IteratingSystem {
    final GameEventQueue queue;
    ComponentMapper<LevelComponent> lm = ComponentMapper.getFor(LevelComponent.class);
    ComponentMapper<PlayerStatsComponent> psc = ComponentMapper.getFor(PlayerStatsComponent.class);

    public LevelSystem(GameEventQueue queue) {
        super(Family.all(PlayerComponent.class).get());
        this.queue = queue;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        LevelComponent levelComponent = lm.get(entity);
        PlayerStatsComponent stats = psc.get(entity);

        if (levelComponent.xp >= levelComponent.levelUpTarget && levelComponent.level < levelComponent.maxLevel) {
            levelUp(levelComponent);
            stats.upgradePoints++;
            queue.add(PlayerEvent.addedPoint);
        }
    }

    private void levelUp(LevelComponent levelComponent) {
        levelComponent.level++;

        if (levelComponent.level != levelComponent.maxLevel) {
            levelComponent.xp -= levelComponent.levelUpTarget;
            levelComponent.levelUpTarget = levelComponent.levelWeight + (int)Math.pow(levelComponent.level, 2);
        }

        queue.add(PlayerEvent.levelUp);

        /** TODO:
         * Sistem de upgrade pentru player (ceva pop-up cu 3 optiuni poate
         * din care player-ul sa aleaga una permanenta.
         * */
    }
}
