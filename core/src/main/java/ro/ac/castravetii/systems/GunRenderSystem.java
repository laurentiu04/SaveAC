package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Vector2;
import ro.ac.castravetii.Gun;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.components.TextureComponent;
import ro.ac.castravetii.components.TransformComponent;

public class GunRenderSystem extends EntitySystem {
    private final TransformComponent transformC;
    private final TransformComponent playerTransformC;
    private final TextureComponent textureC;

    public GunRenderSystem(int priority) {
        super(priority);

        Gun gun = Player.getInstance().getGun();

        transformC = gun.getTransformComponent();
        textureC = gun.getTextureComponent();

        playerTransformC = Player.getInstance().getTransformComponent();
        transformC.origin.y = 0.5f;
    }

    @Override
    public void update(float delta) {
        transformC.position = new Vector2(
        playerTransformC.position.x + (textureC.flippedY ? 11 : 5),
            playerTransformC.position.y + 24
        );
    }
}
