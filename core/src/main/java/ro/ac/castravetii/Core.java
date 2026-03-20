package ro.ac.castravetii;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static com.badlogic.gdx.Gdx.gl;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Core implements ApplicationListener {

    private SpriteBatch spriteBatch;
    private TextureAtlas atlas;
    private Sprite cubeSprite;
    private Sprite castravete;
    private Texture castraveteTexture;

    @Override
    public void create() {
        atlas = new TextureAtlas(Utils.getInternalPath("atlas/testAtlas.atlas"));

        spriteBatch = new SpriteBatch();
        cubeSprite =  atlas.createSprite("stair");
        castraveteTexture = new Texture(Utils.getInternalPath("castravete.png"));
        castravete = new Sprite(castraveteTexture);
        castravete.setBounds(200, 120, 64*4, 64*4);
        castravete.setOrigin(castravete.getHeight()/2, castravete.getWidth()/2);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        castravete.setRotation(castravete.getRotation() + 1);

        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        cubeSprite.draw(spriteBatch);
        castravete.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
        public void dispose() {
        spriteBatch.dispose();
        castraveteTexture.dispose();
        atlas.dispose();
    }

}
