package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import ro.ac.castravetii.Bullet;
import ro.ac.castravetii.Gun;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.components.*;

public class GunShootingSystem extends EntitySystem {

    private final TransformComponent transformC;
    private final Gun gun;

    public GunShootingSystem() {
        super(10);
        gun = Player.getInstance().getGun();
        transformC = gun.getTransformComponent();
    }

    @Override
    public void update(float delta) {
        Vector3 mouseScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Vector3 mouseWorld = Services.camera.unproject(mouseScreen);

        float pivotX = transformC.position.x + transformC.parent.position.x;
        float pivotY = transformC.position.y + transformC.parent.position.y + 2;

        // Calculate angle from PIVOT (stable, no feedback loop)
        float a = mouseWorld.y - pivotY;
        float b = mouseWorld.x - pivotX;
        transformC.rotation = (float) Math.toDegrees(Math.atan2(a, b));

        // Compute gun tip AFTER rotation is set, just for rendering
        float gunOffsetX = 12f;
        float gunOffsetY = 0;
        float rad = (float) Math.toRadians(transformC.rotation);
        float rotatedOffsetX = gunOffsetX * (float) Math.cos(rad) - gunOffsetY * (float) Math.sin(rad);
        float rotatedOffsetY = gunOffsetX * (float) Math.sin(rad) + gunOffsetY * (float) Math.cos(rad);
        float gunTipX = pivotX + rotatedOffsetX;
        float gunTipY = pivotY + rotatedOffsetY;

        GunComponent gunC = gun.getGunComponent();
        if (!gunC.canShoot) {
            gunC.sinceLastShot += delta;
            if (gunC.sinceLastShot >= gunC.shotDelay) {
                gunC.canShoot = true;
            }
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && gunC.canShoot) {
            spawnBullet(gunTipX, gunTipY, transformC.rotation);

            Services.soundSystem.play("shoot", 0.5f);

            gun.shootAnimationScale.play();
            gunC.canShoot = false;
            gunC.sinceLastShot = 0f;
        }

        Player.getInstance().getTransformComponent().scale.x = (transformC.rotation >= 90 || transformC.rotation < -90 ? -1f : 1f);
        gun.getTransformComponent().position.x = (transformC.rotation >= 90 || transformC.rotation < -90 ? 4 : -4f);
        // DEBUG
//        Services.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        Services.shapeRenderer.setColor(Color.RED);
//        Services.shapeRenderer.line(gunTipX, gunTipY, mouseWorld.x, mouseWorld.y);
//        Services.shapeRenderer.end();

    }

    private void spawnBullet(float x, float y, float angle) {
        Bullet bullet = new Bullet();

        TransformComponent transformC = bullet.getComponent(TransformComponent.class);
        transformC.position.set(x, y);
        transformC.rotation = angle;
        bullet.getComponent(TextureComponent.class).flippedY = angle >= 90 || angle < -90;

        MovementComponent movementC = bullet.getComponent(MovementComponent.class);
        float radians = (float) Math.toRadians(angle);
        movementC.moveX = (float) Math.cos(radians) * movementC.speed;
        movementC.moveY = (float) Math.sin(radians) * movementC.speed;

    }
}
