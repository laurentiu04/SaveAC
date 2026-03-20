package ro.ac.castravetii;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Core extends ApplicationAdapter {

    private Sprite cubeSprite;
    private SpriteBatch spriteBatch;
    private TextureAtlas atlas;


    @Override
    public void create() {
        atlas = new TextureAtlas(Utils.getInternalPath("atlas/testAtlas.atlas"));

        cubeSprite =  atlas.createSprite("stair");
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void render() {
        spriteBatch.begin();
        cubeSprite.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
        public void dispose() {
        spriteBatch.dispose();
        atlas.dispose();
    }

}
