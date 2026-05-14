package ro.ac.castravetii.systems;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.MovementComponent;
import ro.ac.castravetii.components.TransformComponent;

public class PlayerInputSystem extends EntitySystem {
    private final MovementComponent movementC;
    private final TransformComponent transformC;

    public PlayerInputSystem(int priority) {
        super(priority);

        movementC = Player.getInstance().getMovementComponent();
        transformC = Player.getInstance().getTransformComponent();
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

        if (transformC.position.x >= Services.MAP_WIDTH * Services.mapTileSize - 16 && movementC.inputX > 0) {
            transformC.position.x = Services.MAP_WIDTH * Services.mapTileSize - 16;
            movementC.moveX = 0;
        } else if (transformC.position.x <= 16 && movementC.inputX < 0) {
            transformC.position.x = 16;
            movementC.moveX = 0;
        } else {
            movementC.moveX = movementC.speed * movementC.inputX;
        }

        if (transformC.position.y >= Services.MAP_HEIGHT * Services.mapTileSize - 80 && movementC.inputY > 0) {
            transformC.position.y = Services.MAP_HEIGHT * Services.mapTileSize - 80;
            movementC.moveY = 0;
        } else if (transformC.position.y <= 16 && movementC.inputY < 0) {
            transformC.position.y = 16;
            movementC.moveY = 0;
        } else {
            movementC.moveY = movementC.speed * movementC.inputY;
        }

        boolean isMoving = movementC.inputX != 0 || movementC.inputY != 0;

        if(isMoving){
            Services.soundSystem.loop("run");
        }else{
            Services.soundSystem.stop("run");
        }
    }
}
