package ro.ac.castravetii.systems;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import ro.ac.castravetii.*;
import ro.ac.castravetii.components.EnemyComponent;
import ro.ac.castravetii.components.TransformComponent;
import ro.ac.castravetii.hud.HUD;

public class EnemyWaveSystem extends EntitySystem {
    private float counter = 0f;
    private int spawnedInWave = 0;
    private int wave = 0;
    private int maxPerWave = 15;
    public boolean spawningFinished = false;
    private final HUD hud;
    public float TimerMessage;

    // Endless wave 5 state
    private int endlessWaveNumber = 0; // sub-wave counter within wave 5

    public boolean reset = false;

    public EnemyWaveSystem(HUD hud) {
        this.hud = hud;
        wave++;
        hud.getWaveLabel().setText("WAVE 1");
        hud.getWaveLabel().setVisible(true);
        TimerMessage = 3f;
    }

    public boolean isEnemyAlive() {
        return getEngine().getEntitiesFor(Family.all(EnemyComponent.class).get()).size() <= 0;
    }

    private boolean isEndlessWave() {
        return wave == 5;
    }

    @Override
    public void update(float deltaTime) {
        counter += deltaTime;
        float spawnInterval = 0.3f;

        // Advance to next wave when current wave's enemies are all dead and spawning is done
        if (spawningFinished && isEnemyAlive()) {
            wave++;
            spawnedInWave = 0;
            spawningFinished = false;

            if (wave == 5) {
                endlessWaveNumber = 1;
                maxPerWave = 20;
                hud.getWaveLabel().setText("FINAL WAVE - ENDLESS");
            } else if (wave == 4) {
                hud.getWaveLabel().setText("WAVE 4");
                maxPerWave = 1; // boss wave keeps 1
            } else {
                hud.getWaveLabel().setText("WAVE " + wave);
                maxPerWave += 5;
            }
            hud.getWaveLabel().setVisible(true);
            TimerMessage = 3f;
        }

        // Within the endless wave, start a new sub-wave when all enemies are dead
        // Advance to next wave OR start next endless sub-wave when all enemies are dead
        if (spawningFinished && isEnemyAlive()) {
            spawnedInWave = 0;
            spawningFinished = false;

            if (isEndlessWave()) {
                // Already in wave 5 — just start the next sub-wave, don't increment wave
                endlessWaveNumber++;
                maxPerWave += 3;
                hud.getWaveLabel().setText("FINAL WAVE - " + endlessWaveNumber);
            } else {
                wave++;
                if (wave == 5) {
                    endlessWaveNumber = 1;
                    maxPerWave = 20;
                    hud.getWaveLabel().setText("FINAL WAVE - ENDLESS");
                } else if (wave == 4) {
                    maxPerWave = 1;
                    hud.getWaveLabel().setText("WAVE 4");
                } else {
                    maxPerWave += 5;
                    hud.getWaveLabel().setText("WAVE " + wave);
                }
            }

            hud.getWaveLabel().setVisible(true);
            TimerMessage = 3f;
        }

        if (TimerMessage > 0) {
            TimerMessage -= deltaTime;
            if (TimerMessage <= 0) {
                hud.getWaveLabel().setVisible(false);
            }
        }

        // Don't spawn if not in a valid wave, or if non-endless wave is done
        if (wave < 1 || wave > 5) return;
        if (!isEndlessWave() && spawningFinished) return;

        if (counter >= spawnInterval && spawnedInWave < maxPerWave) {
            counter = 0f;

            float distance, x, y;
            do {
                x = (float) (Math.random() * 800);
                y = (float) (Math.random() * 800);
                TransformComponent player = Player.getInstance().getTransformComponent();
                float dx = x - player.position.x;
                float dy = y - player.position.y;
                distance = (float) Math.sqrt(dx * dx + dy * dy);
            } while (distance < 300f);

            Enemy enemy;
            if (wave == 1) {
                enemy = new PepperEnemy();
            } else if (wave == 2) {
                enemy = new BellPepperEnemy();
            } else if (wave == 3) {
                enemy = new TomatoEnemy();
            } else if (wave == 4) {
                enemy = new EnemyBoss();
            } else {
                // Wave 5: endless mix, gets harder each sub-wave
                int roll = (int) (Math.random() * 3);
                if (endlessWaveNumber > 3 && roll == 0) {
                    enemy = new EnemyBoss();
                } else if (roll == 1) {
                    enemy = new TomatoEnemy();
                } else {
                    enemy = new BellPepperEnemy();
                }
            }

            TransformComponent tc = enemy.getComponent(TransformComponent.class);
            tc.position.x = x;
            tc.position.y = y;
            spawnedInWave++;

            if (spawnedInWave >= maxPerWave) {
                spawningFinished = true;
            }
        }
    }
}
