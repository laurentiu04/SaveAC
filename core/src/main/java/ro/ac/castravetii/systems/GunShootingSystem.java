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

    private final TransformComponent transformC;
    private final TextureComponent textureC;

    public GunShootingSystem() {
        super(10);
        Gun gun = Player.getInstance().getGun();
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
    float gunOffsetY = textureC.flippedY ? -2f : 2f;
        float rad = (float) Math.toRadians(transformC.rotation);
        float rotatedOffsetX = gunOffsetX * (float) Math.cos(rad) - gunOffsetY * (float) Math.sin(rad);
        float rotatedOffsetY = gunOffsetX * (float) Math.sin(rad) + gunOffsetY * (float) Math.cos(rad);
        float gunTipX = pivotX + rotatedOffsetX;
        float gunTipY = pivotY + rotatedOffsetY;

        if (Gdx.input.isButtonJustPressed(com.badlogic.gdx.Input.Buttons.LEFT)) {
            spawnBullet(gunTipX, gunTipY, transformC.rotation);
        }

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

    private void spawnBullet(float x, float y, float angle) {
        com.badlogic.ashley.core.Entity bullet = new com.badlogic.ashley.core.Entity();

        // poziția de start
        ro.ac.castravetii.components.TransformComponent bt = new ro.ac.castravetii.components.TransformComponent();
        bt.position.set(x, y);
        bt.rotation = angle;

        // logica de miscare
        ro.ac.castravetii.components.BulletComponent bc = new ro.ac.castravetii.components.BulletComponent();
        bc.speed = 400f; // Pixeli pe secunda
        float radians = (float) Math.toRadians(angle);
        bc.velocity.x = (float) Math.cos(radians) * bc.speed;
        bc.velocity.y = (float) Math.sin(radians) * bc.speed;

        // dimensiune hitbox
        bc.hitbox.setSize(4, 4);
        TextureComponent tex = new TextureComponent();
        // bullet.add(new TextureComponent(Services.assets.getBulletTexture()));
        tex.region = textureC.region;
        bullet.add(bt);
        bullet.add(bc);
        bullet.add(tex);

        getEngine().addEntity(bullet);
    }
}
