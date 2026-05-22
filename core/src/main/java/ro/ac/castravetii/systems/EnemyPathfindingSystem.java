package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ro.ac.castravetii.Enemy;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.components.*;
import ro.ac.castravetii.events.AttackEvent;
import ro.ac.castravetii.events.GameEventQueue;
import ro.ac.castravetii.events.UpdateHUDEvent;

public class EnemyPathfindingSystem extends IteratingSystem {

    private final ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);
    private final ComponentMapper<EnemyComponent> em = ComponentMapper.getFor(EnemyComponent.class);
    private final GameEventQueue queue;
    //Run the system for ONLY the entities that have TransformComponent - NO, RUN ONLY FOR ENEMY !!!

    public EnemyPathfindingSystem(GameEventQueue queue){
        super(Family.all(EnemyComponent.class).get());
        this.queue = queue;
    }


    @Override
    protected void processEntity(Entity enemyEntity, float deltaTime) {
        TransformComponent enemyTransformC = tm.get(enemyEntity);
        TransformComponent playerTransformC = Player.getInstance().getTransformComponent();
        MovementComponent move = mm.get(enemyEntity);

        //How can I access player entity ? Solution:
        Entity playerEntity = Player.getInstance().getEntity();


        float dx = playerTransformC.position.x - enemyTransformC.position.x;
        float dy = playerTransformC.position.y - enemyTransformC.position.y;

        for(Entity other : getEngine().getEntitiesFor(Family.all(EnemyComponent.class).get())){
            if(other == enemyEntity)
                continue;

            TransformComponent otherTransform = tm.get(other);

            float ox = enemyTransformC.position.x - otherTransform.position.x;
            float oy = enemyTransformC.position.y - otherTransform.position.y;

            float dist = (float)Math.sqrt(ox * ox + oy * oy);

            //daca inamicii sunt mai aproape de 40px sa se respinga
            if(dist < 40f && dist > 0){
                enemyTransformC.position.x += (ox/dist)*30f * deltaTime;
                enemyTransformC.position.y += (oy/dist)*30f * deltaTime;
            }
        }

        float distance = (float)Math.sqrt(dx*dx+dy*dy);

        //I want my enemy to have a space between the player - I used "stopRange" for that.
        float stopRange = 20f;

        EnemyComponent ec = em.get(enemyEntity);
        ec.attackTimer += deltaTime;

        Enemy enemy = (Enemy)enemyEntity;
        if(distance > stopRange) {
            move.moveX = (dx/distance) * move.speed;
            move.moveY = (dy/distance) * move.speed;

            TransformComponent knifeTransformC = enemy.getKnife().getComponent(TransformComponent.class);
            knifeTransformC.position.x = enemy.getComponent(TextureComponent.class).region.getRegionWidth() * 0.2f * (move.moveX > 0f ? -1f : 1f);
            knifeTransformC.rotation = -10f * (move.moveX > 0f ? -1f : 1f);

            enemy.wobbleAnim.play();
            ec.hasHit = false;
        }else{
            enemy.wobbleAnim.end();
            move.moveX = 0;
            move.moveY = 0;

            //modify the attack time : >= increment the value -> slower attack / decrement the value -> faster attack - "3f" THE VALUE
            if(ec.attackTimer >= 3f) {
                queue.post(new AttackEvent(enemyEntity, ec.damage, Player.getInstance()));
                queue.post(UpdateHUDEvent.healthBar);

                //40% sansa sa ia stun (stiu e mare dar modificam dupa... ar merge si o animatie de stun sincer :D)
                if(Math.random() < 0.8f){
                    playerEntity.getComponent(PlayerComponent.class).stunned = true;
                    playerEntity.getComponent(PlayerComponent.class).stunTimer = 1f;
                    Player.getInstance().stunAnimationScale.play();
                    Player.getInstance().stunAnimationRotation.play();
                }

                ec.attackTimer = 0f;
            }
        }
    }
}
