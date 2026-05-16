package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import ro.ac.castravetii.*;
import ro.ac.castravetii.components.EnemyComponent;
import ro.ac.castravetii.components.TransformComponent;
import ro.ac.castravetii.hud.HUD;


public class EnemyWaveSystem extends EntitySystem {
    private float counter = 0f;

    //how many Enemies are spawned in current wave.
    private int spawnedInWave = 0;
    private int wave = 0;
    private int maxWaves = 5;
    public boolean spawningFinished = false;
    private int maxPerWave = 15;

    private final HUD hud;
    public float TimerMessage = 0f;

    public EnemyWaveSystem(HUD hud) {
        this.hud = hud;

        wave++;

        hud.getWaveLabel().setText("WAVE 1");
        hud.getWaveLabel().setVisible(true);

        TimerMessage = 3f;
    }

    public boolean isEnemyAlive(){
        return getEngine().getEntitiesFor(Family.all(EnemyComponent.class).get()).size()>0;
    }

    @Override
    public void update(float deltaTime){
        counter += deltaTime;

        //every 3 seconds enemies appear.
        float spawnInterval = 0.3f;

        if(spawningFinished && !isEnemyAlive()){
            wave++;

            hud.getWaveLabel().setText("WAVE " + wave);

            hud.getWaveLabel().setVisible(true);

            TimerMessage = 3f;

            spawnedInWave = 0;

            maxPerWave += 5;

            spawningFinished = false;
        }

        if(TimerMessage > 0){
            TimerMessage -= deltaTime;

            if(TimerMessage <= 0){
                hud.getWaveLabel().setVisible(false);
            }
        }

        if(counter >= spawnInterval && spawnedInWave < maxPerWave){
            counter = 0f;
            //the map is 50x32, but I want a spawn point smaller because it is going to be boring
            //to wait for the enemies to come at player for a long period of time if they are spawned in other corner of the map.
            float distance;
            float x = 0;
            float y = 0;

            // spawn pentru inamici de la o distanta considerabila <3
            do {
                x = (float) (Math.random() * 800);
                y = (float) (Math.random() * 800);

                TransformComponent player = Player.getInstance().getTransformComponent();

                float dx = x - player.position.x;
                float dy = y - player.position.y;

                distance = (float) Math.sqrt(dx * dx + dy * dy);

            }while(distance < 300f);

            Enemy enemy;

            if(wave == 1){
                enemy = new PepperEnemy();
            }else if(wave == 2){
                enemy = new BellPepperEnemy();
            }else if(wave == 3){
                enemy = new TomatoEnemy();
            }else if(wave == 4){
                enemy = new EnemyBoss();

                maxPerWave = 1;
            }else{
                return;
            }

            TransformComponent tc = enemy.getTransformComponent();
            tc.position.x = x;
            tc.position.y = y;

            spawnedInWave++;
            if(spawnedInWave >= maxPerWave){
                spawningFinished = true;
            }
        }
    }
}
