package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;

/**
 * Componentă pentru clasa Gun.
 */
public class GunComponent implements Component {
    /**
     * Durata pauzei între shot-uri.
     */
    public float shotDelay = 0.4f;
    /**
     * Variabliă ce stochează căt timp a trecut de la ultimul shot.
     */
    public float sinceLastShot = 0f;
    /**
     * Flag pentru a limita posibilitatea de a trage.
     */
    public boolean canShoot = true;
    /**
     * Cât damage dă arma.
     */
    public int damage = 10;
}
