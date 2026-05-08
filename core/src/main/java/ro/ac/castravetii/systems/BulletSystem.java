package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import ro.ac.castravetii.components.*;
import ro.ac.castravetii.events.EnemyDamageEvent;
import ro.ac.castravetii.events.GameEventQueue;

    public class BulletSystem extends IteratingSystem {
    private final ComponentMapper<BulletComponent> bm = ComponentMapper.getFor(BulletComponent.class);
    private final ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<ColliderComponent> cm = ComponentMapper.getFor(ColliderComponent.class);

    private final GameEventQueue queue;
    private ImmutableArray<Entity> enemies;

    public BulletSystem(GameEventQueue queue) {
        // procesare entitati care au bullet si transform
        super(Family.all(BulletComponent.class, TransformComponent.class).get());
        this.queue = queue;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        // cache pentru entitatile care pot fi lovite
        enemies = engine.getEntitiesFor(Family.all(EnemyComponent.class, ColliderComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity bulletEntity, float deltaTime) {
        BulletComponent bullet = bm.get(bulletEntity);
        TransformComponent bulletTrans = tm.get(bulletEntity);

        // miscare glont
        bulletTrans.position.x += bullet.velocity.x * deltaTime;
        bulletTrans.position.y += bullet.velocity.y * deltaTime;

        // actualizare hitbox glont
        bullet.hitbox.set(bulletTrans.position.x, bulletTrans.position.y, 8, 8);

        // verificare coliziune cu inamicii
        for (Entity enemyEntity : enemies) {
            TransformComponent enemyTrans = tm.get(enemyEntity);
            ColliderComponent enemyColl = cm.get(enemyEntity);

            Rectangle enemyRect = new Rectangle(
                enemyTrans.position.x + enemyColl.offsetX,
                enemyTrans.position.y + enemyColl.offsetY,
                enemyColl.with,
                enemyColl.height
            );

            if (bullet.hitbox.overlaps(enemyRect)) {
                // postare eveniment damage
                queue.post(new EnemyDamageEvent(enemyEntity, bullet.damage, bulletEntity));
                // oprire glont
                bullet.active = false;
                break;
            }
        }

        // gestionare durata de viata
        bullet.lifeTime -= deltaTime;
        if (bullet.lifeTime <= 0 || !bullet.active) {
            getEngine().removeEntity(bulletEntity);
        }
    }
}
