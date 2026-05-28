package ro.ac.castravetii.systems;

import org.junit.Test;

import static org.junit.Assert.*;

public class EnemyMoveToPlayerTest {

    @Test
    public void EnemyMoveToPlayer(){

        float enemyX = 0;

        float playerX = 100;

        float dx = playerX - enemyX;

        assertTrue(dx > 0);
    }
}
