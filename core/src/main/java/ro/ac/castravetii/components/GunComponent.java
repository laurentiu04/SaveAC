package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;

public class GunComponent implements Component {
    public float shotDelay = 0.5f;
    public float sinceLastShot = 0f;
    public boolean canShoot = true;
}
