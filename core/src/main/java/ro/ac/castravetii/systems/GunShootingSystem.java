package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import ro.ac.castravetii.Gun;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.TextureComponent;
import ro.ac.castravetii.components.TransformComponent;

public class GunShootingSystem extends EntitySystem {

    private final Gun gun;
    private final TransformComponent transformC;
    private final TextureComponent textureC;

    public GunShootingSystem() {
        super(10);
        this.gun = Player.getInstance().getGun();
        transformC = gun.getTransformComponent();
        textureC = gun.getTextureComponent();
    }

    @Override
    public void update(float delta) {
        Vector3 mouseScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Vector3 mouseWorld = Services.camera.unproject(mouseScreen);

        float pivotX = transformC.position.x + textureC.region.getRegionWidth() * -0.5f;
        float pivotY = transformC.position.y + textureC.region.getRegionHeight() * transformC.origin.y;

        // Calculate angle from PIVOT (stable, no feedback loop)
        float a = mouseWorld.y - pivotY;
        float b = mouseWorld.x - pivotX;
        transformC.rotation = (float) Math.toDegrees(Math.atan2(a, b));

        // Compute gun tip AFTER rotation is set, just for rendering
        float gunOffsetX = 13f;
        float gunOffsetY = 2f;
        float rad = (float) Math.toRadians(transformC.rotation);
        float rotatedOffsetX = gunOffsetX * (float) Math.cos(rad) - gunOffsetY * (float) Math.sin(rad);
        float rotatedOffsetY = gunOffsetX * (float) Math.sin(rad) + gunOffsetY * (float) Math.cos(rad);
        float gunTipX = pivotX + rotatedOffsetX;
        float gunTipY = pivotY + rotatedOffsetY;

        Player.getInstance().getTextureComponent().flippedX = transformC.rotation >= 90 || transformC.rotation <= -90;

        Services.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        Services.shapeRenderer.setColor(Color.RED);
        Services.shapeRenderer.line(gunTipX, gunTipY, mouseWorld.x, mouseWorld.y);
        float s = 3f;
        Services.shapeRenderer.setColor(Color.YELLOW);
        Services.shapeRenderer.line(gunTipX - s, gunTipY, gunTipX + s, gunTipY);
        Services.shapeRenderer.line(gunTipX, gunTipY - s, gunTipX, gunTipY + s);
        Services.shapeRenderer.end();
    }
}
