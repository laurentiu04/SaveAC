package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Game;
import ro.ac.castravetii.Enemy;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.components.EnemyComponent;
import ro.ac.castravetii.components.MovementComponent;
import ro.ac.castravetii.components.TransformComponent;
import ro.ac.castravetii.events.GameEventQueue;

public class EnemyPathfindingSystem extends IteratingSystem {

    private final ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);
    private final GameEventQueue queue;
    //Run the system for ONLY the entities that have TransformComponent - NO, RUN ONLY FOR ENEMY !!!

    //implementeaza GameEventQueue vezi alte exemple
    public EnemyPathfindingSystem(GameEventQueue queue){
        super(Family.all(TransformComponent.class, EnemyComponent.class).get());
        this.queue = queue;
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent enemy = tm.get(entity);
        TransformComponent player = Player.getInstance().getTransformComponent();
        MovementComponent move = mm.get(entity);

        float dx = player.position.x - enemy.position.x;
        float dy = player.position.y - enemy.position.y;

        float distance = (float)Math.sqrt(dx*dx+dy*dy);

        //I want my enemy to have a space between the player - I used "stopRange" for that.
        float stopRange = 20f;

        if(distance > stopRange){
            move.moveX = (dx/distance) * move.speed;
            move.moveY = (dy/distance) * move.speed;
        }else{
            move.moveX = 0;
            move.moveY = 0;
        }
    }
}
