package ro.ac.castravetii.events;

import com.badlogic.ashley.core.Entity;
import org.junit.Test;

import static org.junit.Assert.*;

public class AttackEventTest {

    @Test
    public void testAttackEventCreation(){

        Entity source = new Entity();
        Entity target = new Entity();

        AttackEvent event =
            new AttackEvent(source, 50, target);

        assertEquals(source, event.source());

        assertEquals(50, event.damage());

        assertEquals(target, event.target());
    }
}
