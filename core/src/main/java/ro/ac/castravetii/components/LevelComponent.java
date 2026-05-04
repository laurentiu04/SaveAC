package ro.ac.castravetii.components;

import com.badlogic.ashley.core.Component;

public class LevelComponent implements Component {
    public int xp = 0;
    public float xpGain = 1f;
    public int level = 0;
    public int maxLevel = 120;
    public int levelWeight = 100;
    public int levelUpTarget = 100;
}
