package inf112.skeleton.app;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PlayerTest {

    @Test
    public void newPlayerHasCorrectDirection(){
        Player player1 = new Player("Player1", Direction.NORTH);
        Player player2 = new Player("Player2", Direction.EAST);
        Player player3 = new Player("Player3", Direction.SOUTH);
        Player player4 = new Player("Player4", Direction.WEST);

        assertEquals(player1.getDirection(), Direction.NORTH);
        assertEquals(player2.getDirection(), Direction.EAST);
        assertEquals(player3.getDirection(), Direction.SOUTH);
        assertEquals(player4.getDirection(), Direction.WEST);
    }

    @Test
    public void turnPlayerActuallyTurnsPlayer(){
        Player player = new Player("player1", Direction.NORTH);
        player.turnPlayer(1);
        assertEquals(player.getDirection(), Direction.EAST);

        player.turnPlayer(2);
        assertEquals(player.getDirection(), Direction.WEST);

        player.turnPlayer(3);
        assertEquals(player.getDirection(), Direction.SOUTH);

        player.turnPlayer(-1);
        assertEquals(player.getDirection(), Direction.EAST);

        player.turnPlayer(-2);
        assertEquals(player.getDirection(), Direction.WEST);

        player.turnPlayer(-3);
        assertEquals(player.getDirection(), Direction.NORTH);

    }




}