package it.polimi.ingsw.PSP50.Model;

import it.polimi.ingsw.PSP50.Model.GodsList.Apollo;
import it.polimi.ingsw.PSP50.Model.GodsList.God;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;


public class PlayerTest {
    Player player= null;

    @Before
    public void setUp() {
        String name= "player1";
        player = new Player(name);
    }

    @After
    public void tearDown() {
        player = null;
    }


    @Test
    public void getWorkers() {
        Worker[] workers = player.getWorkers();
        assertEquals(2, workers.length);
    }

    @Test
    public void getName_Correct() {
        assertEquals("player1",player.getName());
    }

    @Test
    public void selectedWorker_Correct(){
        assertNull(player.getSelectedWorker());
        player.selectWorker(player.getWorkers()[0]);
        Worker selectedWorker= player.getSelectedWorker();

        assertEquals(player.getWorkers()[0], selectedWorker);
    }

    @Test
    public void setGetGod(){
        assertNull(player.getGod());
        God god= new Apollo();
        player.setGod(god);
        assertEquals(god,player.getGod());
    }

    @Test
    public void lastMoveTest(){
        assertNull(player.getLastMove());
        Space last= new Space(0,0, new Board());
        player.setLastMove(last);
        assertEquals(last, player.getLastMove());
    }

    @Test
    public void lastBuildTest(){
        assertNull(player.getLastBuild());
        Space last= new Space(0,0, new Board());
        player.setLastBuild(last);
        assertEquals(last, player.getLastBuild());
    }

    @Test
    public void thisBuildTest(){
        assertNull(player.getThisBuild());
        Space thisBuild= new Space(0,0, new Board());
        player.setThisBuild(thisBuild);
        assertEquals(thisBuild, player.getThisBuild());
    }

    @Test
    public void hasBuiltTest(){
        assertSame(false,player.getHasBuilt());
        player.setHasBuilt(true);
        assertSame( true, player.getHasBuilt());
    }

    @Test
    public void hasMovedUpTest(){
        assertSame(false,player.getHasMovedUp());
        player.setHasMovedUp(true);
        assertSame(true,player.getHasMovedUp());
    }

    @Test
    public void hasLostTest() {
        assertSame(false,player.getHasLost());
        player.setHasLost(true);
        assertSame(true,player.getHasLost());
    }

    @Test
    public void isBlockedTest() {
        assertSame(false,player.isPlayerBlocked());
        player.setPlayerBlocked(true);
        assertSame(true,player.isPlayerBlocked());
    }

    @Test
    public void colorTest() {
        assertEquals(null, player.getColor());
        player.setColor(Color.BLUE);
        assertSame(Color.BLUE, player.getColor());
    }

    @Test
    public void opponentsTest() {
        assertEquals(0, player.getOpponents().size());
        Player opponent1 = new Player("player2");
        player.addOpponent(opponent1);
        assertSame(opponent1, (player.getOpponents()).get(0));
    }

}