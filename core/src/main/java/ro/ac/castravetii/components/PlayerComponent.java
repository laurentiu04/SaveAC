package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Componenta ce va defini o entitate ca fiind player.
 */
public class PlayerComponent implements Component, Pool.Poolable {
    public boolean stunned = false;
    public float stunTimer = 0f;

    @Override
    public void reset() {

    }
}
