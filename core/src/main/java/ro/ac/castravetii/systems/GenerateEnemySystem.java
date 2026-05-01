package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.EntitySystem;
import ro.ac.castravetii.BellPepperEnemy;
import ro.ac.castravetii.Enemy;
import ro.ac.castravetii.PepperEnemy;
import ro.ac.castravetii.components.TransformComponent;

public class GenerateEnemySystem extends EntitySystem {
    private float counter = 0f;

    //how many Enemies are spawned in current wave.
    private int spawnedInWave = 0;
    private int wave = 1;

    @Override
    public void update(float deltaTime){
        counter += deltaTime;

        int maxPerWave = 10;

        //every 3 seconds enemies appear.
        float spawnInterval = 3f;

        if(counter >= spawnInterval && spawnedInWave < maxPerWave){
            counter = 0f;
            //the map is 50x32, but I want a spawn point smaller because it is going to be boring
            //to wait for the enemies to come at player for a long period of time if they are spawned in other corner of the map.
            float x = (float) (Math.random() * 1000);
            float y = (float) (Math.random() * 800);

            Enemy enemy;

            if(wave == 1){
                enemy = new PepperEnemy();
            }else if(wave == 2){
                enemy = new BellPepperEnemy();
            }else{
                return;
            }

            TransformComponent tc = enemy.entityEnemy.getComponent(TransformComponent.class);
            tc.position.x = x;
            tc.position.y = y;

            spawnedInWave++;

            if(spawnedInWave >= maxPerWave){
                wave++;
                spawnedInWave = 0;
            }
        }
    }
}
