package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import ro.ac.castravetii.Gun;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.components.*;
import ro.ac.castravetii.events.AttackEvent;
import ro.ac.castravetii.events.GameEventQueue;

public class BulletSystem extends IteratingSystem {
    private final ComponentMapper<ProjectileComponent> bm = ComponentMapper.getFor(ProjectileComponent.class);
    private final ComponentMapper<EllipseColliderComponent> ecm = ComponentMapper.getFor(EllipseColliderComponent.class);
    private final ComponentMapper<PolygonColliderComponent> pcm = ComponentMapper.getFor(PolygonColliderComponent.class);
    private final ComponentMapper<BoxColliderComponent> bcm = ComponentMapper.getFor(BoxColliderComponent.class);
    private final Gun gun;

    private final GameEventQueue queue;
    private ImmutableArray<Entity> enemies;
    private Entity playerEntity;

    public BulletSystem(GameEventQueue queue) {
        // procesare entitati care au bulletComponent
        super(Family.all(ProjectileComponent.class).get());
        this.queue = queue;
        gun = Player.getInstance().getGun();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        // cache pentru entitatile care pot fi lovite
        enemies = engine.getEntitiesFor(Family.all(EnemyComponent.class).get());
        playerEntity = Player.getInstance();
    }

    @Override
    protected void processEntity(Entity bulletEntity, float deltaTime) {
        ProjectileComponent bullet = bm.get(bulletEntity);
        PolygonColliderComponent bulletCollider = pcm.get(bulletEntity);
        Entity playerEntity = Player.getInstance();

        if (bullet.isEnemy) {
            if (playerEntity != null && ecm.has(playerEntity)) {
                EllipseColliderComponent playerCollider = ecm.get(playerEntity);
                Polygon playerPoly = getPolygon(playerCollider);

                if (Intersector.overlapConvexPolygons(bulletCollider.polygon, playerPoly)) {
                    queue.post(new AttackEvent(bulletEntity, 20, playerEntity));
                    bullet.active = false;
                }

        } } else {
        // verificare coliziune cu inamicii
        for (Entity enemyEntity : enemies) {

            if (pcm.has(enemyEntity)) {
                PolygonColliderComponent enemyCollider = pcm.get(enemyEntity);

                if (enemyCollider == null || enemyCollider.polygon == null) continue;
                if (bulletCollider == null || bulletCollider.polygon == null) continue;

                if (Intersector.overlapConvexPolygons(bulletCollider.polygon, enemyCollider.polygon)) {
                    // postare eveniment damage
                    queue.post(new AttackEvent(bulletEntity, gun.getGunComponent().damage, enemyEntity));
                    // oprire glonta
                    bullet.active = false;
                    break;
                    }
                }
            }
        }

        // gestionare durata de viata
        bullet.lifeTime -= deltaTime;
        if (bullet.lifeTime <= 0 || !bullet.active) {
            getEngine().removeEntity(bulletEntity);
        }
    }

    private static Polygon getPolygon(EllipseColliderComponent playerCollider) {
        Vector2 playerPos = Player.getInstance().getTransformComponent().position;

        float w = playerCollider.width;
        float h = playerCollider.height;

        float[] playerVertices = new float[] {
            0, 0,
            w, 0,
            w, h,
            0, h
        };
        Polygon playerPoly = new Polygon(playerVertices);
        playerPoly.setPosition(
            playerPos.x + playerCollider.offset.x,
            playerPos.y + playerCollider.offset.y
        );
        return playerPoly;
    }
}
