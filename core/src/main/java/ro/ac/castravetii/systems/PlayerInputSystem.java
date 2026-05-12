package ro.ac.castravetii.systems;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.MovementComponent;
import ro.ac.castravetii.components.TransformComponent;

public class PlayerInputSystem extends EntitySystem {
    private final MovementComponent movementC;

//    private final SoundSystem soundSystem;

    public PlayerInputSystem(int priority) {
        super(priority);

        movementC = Player.getInstance().getMovementComponent();
    }

    @Override
    public void update(float deltaTime) {

        // Vad daca am apasat pe una dintre tastele W, A, S, D
        movementC.inputX = (Gdx.input.isKeyPressed(Input.Keys.D) ? 1 : 0)
            - (Gdx.input.isKeyPressed(Input.Keys.A) ? 1 : 0);
        movementC.inputY = (Gdx.input.isKeyPressed(Input.Keys.W) ? 1 : 0)
            - (Gdx.input.isKeyPressed(Input.Keys.S) ? 1 : 0);

        float length = (float) Math.sqrt(movementC.inputX * movementC.inputX + movementC.inputY * movementC.inputY);
        if (length > 0) {
            movementC.inputX /= length;
            movementC.inputY /= length;
        }

        moveComp.moveX = moveComp.speed * moveComp.inputX;
        moveComp.moveY = moveComp.speed * moveComp.inputY;

        boolean isMoving = moveComp.inputX != 0 || moveComp.inputY != 0;

        if(isMoving){
            Services.soundSystem.loop("run");
        }else{
            Services.soundSystem.stop("run");
        }
    }
}
