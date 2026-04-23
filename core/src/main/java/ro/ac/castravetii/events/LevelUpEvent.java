package ro.ac.castravetii.events;

import javax.swing.text.html.parser.Entity;

public class LevelUpEvent implements GameEvent{
    public Entity source;
    public int level;
}
