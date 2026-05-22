package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

public class TextureComponent implements Component, Pool.Poolable {
    public TextureRegion region;
    public float opacity = 1f;
    public int layer = 1;
    public boolean flippedX = false;
    public boolean flippedY = false;

    @Override
    public void reset() {
        region = null;
    }
}
