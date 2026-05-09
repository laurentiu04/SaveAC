package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import ro.ac.castravetii.Gun;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.components.*;
import ro.ac.castravetii.events.EnemyDamageEvent;
import ro.ac.castravetii.events.GameEventQueue;

public class BulletSystem extends IteratingSystem {
    private final ComponentMapper<BulletComponent> bm = ComponentMapper.getFor(BulletComponent.class);
    private final ComponentMapper<EllipseColliderComponent> ecm = ComponentMapper.getFor(EllipseColliderComponent.class);
    private final ComponentMapper<PolygonColliderComponent> pcm = ComponentMapper.getFor(PolygonColliderComponent.class);
    private final ComponentMapper<BoxColliderComponent> bcm = ComponentMapper.getFor(BoxColliderComponent.class);
    private final Gun gun;

    private final GameEventQueue queue;
    private ImmutableArray<Entity> enemies;

    public BulletSystem(GameEventQueue queue) {
        // procesare entitati care au bulletComponent
        super(Family.all(BulletComponent.class).get());
        this.queue = queue;
        gun = Player.getInstance().getGun();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        // cache pentru entitatile care pot fi lovite
        enemies = engine.getEntitiesFor(Family.all(EnemyComponent.class).get());
    }

    @Override
    protected void processEntity(Entity bulletEntity, float deltaTime) {
        BulletComponent bullet = bm.get(bulletEntity);
        PolygonColliderComponent bulletCollider = pcm.get(bulletEntity);


        // verificare coliziune cu inamicii
        for (Entity enemyEntity : enemies) {

            if (pcm.has(enemyEntity)) {
                PolygonColliderComponent enemyCollider = pcm.get(enemyEntity);

                if (Intersector.overlapConvexPolygons(bulletCollider.polygon, enemyCollider.polygon)) {
                    // postare eveniment damage
                    queue.post(new EnemyDamageEvent(enemyEntity, gun.getGunComponent().damage, bulletEntity));
                    // oprire glont
                    bullet.active = false;
                    break;
                }
            }
        }

        // gestionare durata de viata
        bullet.lifeTime -= deltaTime;
        if (bullet.lifeTime <= 0 || !bullet.active) {
            getEngine().removeEntity(bulletEntity);
        }
    }
}
