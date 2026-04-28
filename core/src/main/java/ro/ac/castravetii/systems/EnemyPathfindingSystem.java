package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.components.EnemyComponent;
import ro.ac.castravetii.components.TransformComponent;

public class EnemyPathfindingSystem extends IteratingSystem {

    private final ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

    //Run the system for ONLY the entities that have TransformComponent - NO RUN ONLY FOR ENEMY !!!
    public EnemyPathfindingSystem(){
        super(Family.all(TransformComponent.class, EnemyComponent.class).get());
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent enemy = tm.get(entity);
        TransformComponent player = Player.getInstance().getTransformComponent();


        float dx = player.position.x - enemy.position.x;
        float dy = player.position.y - enemy.position.y;

        float distance = (float)Math.sqrt(dx*dx+dy*dy);

        float detectRange = 9999f; // enemy should detect the player on the entire map.

        //this system is going to rewrite the speed in class Enemy - CHECK later.
        float speed = 100f;
        //I want my enemy to have a space between the player - I used "stopRange" for that.
        float stopRange = 20f;

        if(distance < detectRange){
            if(distance > stopRange){
                enemy.position.x += (dx/distance) * speed * deltaTime;
                enemy.position.y += (dy/distance) * speed * deltaTime;
            }
        }
    }
}
