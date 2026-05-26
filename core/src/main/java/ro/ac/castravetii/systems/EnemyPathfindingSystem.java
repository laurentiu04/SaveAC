package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.components.EnemyComponent;
import ro.ac.castravetii.components.MovementComponent;
import ro.ac.castravetii.components.PlayerComponent;
import ro.ac.castravetii.components.TransformComponent;
import ro.ac.castravetii.events.AttackEvent;
import ro.ac.castravetii.events.GameEventQueue;
import ro.ac.castravetii.events.UpdateHUDEvent;
import ro.ac.castravetii.BellPepperEnemy;
import ro.ac.castravetii.Seed;


public class EnemyPathfindingSystem extends IteratingSystem {

    private final ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);
    private final ComponentMapper<EnemyComponent> em = ComponentMapper.getFor(EnemyComponent.class);
    private final GameEventQueue queue;
    private final ComponentMapper<ro.ac.castravetii.components.TextureComponent> tmAtlas = ComponentMapper.getFor(ro.ac.castravetii.components.TextureComponent.class);
    //Run the system for ONLY the entities that have TransformComponent - NO, RUN ONLY FOR ENEMY !!!

    public EnemyPathfindingSystem(GameEventQueue queue){
        super(Family.all(EnemyComponent.class).get());
        this.queue = queue;
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent enemy = tm.get(entity);
        TransformComponent player = Player.getInstance().getTransformComponent();
        MovementComponent move = mm.get(entity);

        //How can I access player entity ? Solution:
        Entity playerEntity = Player.getInstance().getEntity();


        float dx = player.position.x - enemy.position.x;
        float dy = player.position.y - enemy.position.y;

        for (Entity other : getEngine().getEntitiesFor(Family.all(EnemyComponent.class).get())) {
            if (other == entity)
                continue;
            TransformComponent otherTransform = tm.get(other);

            float ox = enemy.position.x - otherTransform.position.x;
            float oy = enemy.position.y - otherTransform.position.y;

            float dist = (float) Math.sqrt(ox * ox + oy * oy);

            //daca inamicii sunt mai aproape de 40px sa se respinga
            if (dist < 40f && dist > 0) {
                enemy.position.x += (ox / dist) * 30f * deltaTime;
                enemy.position.y += (oy / dist) * 30f * deltaTime;
            }
        }

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        //I want my enemy to have a space between the player - I used "stopRange" for that.
        float stopRange = 20f;

        EnemyComponent ec = em.get(entity);
        ec.attackTimer += deltaTime;

        boolean isBellPepper = entity instanceof BellPepperEnemy;

        stopRange = isBellPepper ? 150f : 40f;

        if (distance > stopRange) {
            move.moveX = (dx / distance) * move.speed;
            move.moveY = (dy / distance) * move.speed;

            ec.hasHit = false;
        } else {
            move.moveX = 0;
            move.moveY = 0;

            //modify the attack time : >= increment the value -> slower attack / decrement the value -> faster attack - "3f" THE VALUE
            if (ec.attackTimer >= 3f) {

                isBellPepper = false;
                if (tmAtlas.has(entity) && tmAtlas.get(entity).region instanceof com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion atlasRegion) {

                    if (atlasRegion.name != null && atlasRegion.name.contains("BellPepper")) {
                        isBellPepper = true;
                    }
                }


                if (entity instanceof BellPepperEnemy) {

                    new Seed(enemy.position, player.position);
                } else {

                    queue.post(new AttackEvent(entity, ec.damage, playerEntity));
                    queue.post(UpdateHUDEvent.healthBar);

                    // 40% sansa sa ia stun
                    if (Math.random() < 0.4f) {
                        playerEntity.getComponent(PlayerComponent.class).stunned = true;
                        playerEntity.getComponent(PlayerComponent.class).stunTimer = 1f;
                    }
                }

                ec.attackTimer = 0f;
            }
        }
    }
}
