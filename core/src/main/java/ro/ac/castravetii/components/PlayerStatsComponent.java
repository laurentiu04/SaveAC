package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;

public class PlayerStatsComponent implements Component {
    public int maxHealth = 100;
    public int healthLevel = 1;

    public float maxVel = 100f;
    public int speedLevel = 1;

    public int damage = 10;
    public int strengthLevel = 1;

    public float xpGain = 1f;
    public int xpGainLevel = 1;

    public int upgradePoints = 0;

}
