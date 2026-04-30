package ro.ac.castravetii.systems;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.components.MovementComponent;
import ro.ac.castravetii.components.PlayerStatsComponent;

public class PlayerInputSystem extends EntitySystem {
    private final MovementComponent moveComp;
    private final PlayerStatsComponent stats;

    public PlayerInputSystem(int priority) {
        super(priority);

        moveComp = Player.getInstance().getMovementComponent();
        stats = Player.getInstance().getPlayerStats();
    }

    @Override
    public void update(float deltaTime) {

        // Vad daca am apasat pe una dintre tastele W, A, S, D
        moveComp.inputX = (Gdx.input.isKeyPressed(Input.Keys.D) ? 1 : 0)
            - (Gdx.input.isKeyPressed(Input.Keys.A) ? 1 : 0);
        moveComp.inputY = (Gdx.input.isKeyPressed(Input.Keys.W) ? 1 : 0)
            - (Gdx.input.isKeyPressed(Input.Keys.S) ? 1 : 0);

        float length = (float) Math.sqrt(moveComp.inputX * moveComp.inputX + moveComp.inputY * moveComp.inputY);
        if (length > 0) {
            moveComp.inputX /= length;
            moveComp.inputY /= length;
        }

        moveComp.moveX = (moveComp.speed + stats.speedBoost) * moveComp.inputX;
        moveComp.moveY = (moveComp.speed + stats.speedBoost) * moveComp.inputY;
    }
}
