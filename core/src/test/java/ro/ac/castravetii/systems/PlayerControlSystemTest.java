package ro.ac.castravetii.systems;

import org.junit.Test;
import ro.ac.castravetii.Player;
import ro.ac.castravetii.events.GameEventQueue;

import static org.junit.Assert.*;

// e mult mai complicat sa fac test cu saveFinalScore - foloseste prea multe resurse si componente LibGDX
//aici e poate fi tratat si la general ca noi lucram doar cu resurse din LibGDX, nu prea adica spre deloc nu avem functii proprii create...
public class PlayerControlSystemTest {

    @Test
    public void saveFinalScore() {
        GameEventQueue queue = new GameEventQueue();

        Player.create();
        PlayerControlSystem system =
            new PlayerControlSystem(null, queue, 1);

        system.saveFinalScore(100);

        System.out.println("Scor salvat");
    }
}
