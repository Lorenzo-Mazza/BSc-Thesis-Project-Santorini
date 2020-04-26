package it.polimi.ingsw.PSP50.Model;

import it.polimi.ingsw.PSP50.Model.GodsList.Apollo;
import it.polimi.ingsw.PSP50.Model.GodsList.God;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertNotNull(workers);
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


}