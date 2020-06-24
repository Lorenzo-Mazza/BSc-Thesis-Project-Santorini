package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ArtemisTest {
    private God god;
    private Player owner;
    private Board board;


    @Before
    public void setUp() {
        god = new Artemis();
        owner = new Player("testing");
        board = new Board();
    }

    @After
    public void tearDown() {
        god = null;
        owner = null;
        board = null;
    }

    @Test
    public void stepsTest() {
        assertEquals(Phase.MOVE, god.getAvailableSteps().get(0));
        assertEquals(Phase.BUILD, god.getAvailableSteps().get(1));
        assertEquals(Phase.OPTIONALMOVE, god.getAvailableSteps().get(2));
    }

    @Test
    public void nameTest() {
        assertEquals(GodsNames.ARTEMIS, god.getName());
    }

    @Test
    public void testNormalAvailableMove() {
        owner.selectWorker(owner.getWorkers()[0]);
        owner.getSelectedWorker().setPosition(board.getSpace(0, 0));
        board.getSpace(0,1).setHeight(Block.LEVEL1);
        ArrayList<Space> availableMoves = new ArrayList<>();
        availableMoves.add(board.getSpace(0, 1));
        availableMoves.add(board.getSpace(1, 0));
        availableMoves.add(board.getSpace(1, 1));
        assertThat(god.getAvailableMove(owner), is(availableMoves));
    }

    @Test
    public void testNormalMove() {
        owner.selectWorker(owner.getWorkers()[0]);
        owner.getSelectedWorker().setPosition(board.getSpace(0, 0));
        god.move(owner, board.getSpace(1, 1));
        assertEquals(board.getSpace(1, 1), owner.getSelectedWorker().getPosition());
        assertEquals(board.getSpace(1, 1).getWorker(), owner.getSelectedWorker());
        assertNull(board.getSpace(0,0).getWorker());
    }

    @Test
    public void testNormalAvailableBuild() {
        owner.selectWorker(owner.getWorkers()[0]);
        owner.getSelectedWorker().setPosition(board.getSpace(0, 0));
        board.getSpace(1, 1).setHeight(Block.LEVEL1);
        ArrayList<Space> availableBuilds = new ArrayList<>();
        availableBuilds.add(board.getSpace(0, 1));
        availableBuilds.add(board.getSpace(1, 0));
        availableBuilds.add(board.getSpace(1, 1));
        assertThat(god.getAvailableBuild(owner), is(availableBuilds));
    }

    @Test
    public void testNormalBuild() {
        owner.selectWorker(owner.getWorkers()[0]);
        owner.getSelectedWorker().setPosition(board.getSpace(0, 0));
        board.getSpace(1, 1).setHeight(Block.LEVEL1);
        god.build(owner, board.getSpace(1, 1), Block.LEVEL2);
        assertEquals(Block.LEVEL2, board.getSpace(1, 1).getHeight());
    }

    @Test
    public void testNormalOptionalBuild(){
        assertTrue(god.getOptionalBuild(owner).isEmpty());
    }

    @Test
    public void testNormalAvailableBlock(){
        owner.selectWorker(owner.getWorkers()[0]);
        owner.getSelectedWorker().setPosition(board.getSpace(0, 0));
        owner.setThisBuild(board.getSpace(0,0));
        assertEquals(1, god.getAvailableBlock(owner).size());
    }

    @Test
    public void testNormalWin_true() {
        owner.selectWorker(owner.getWorkers()[0]);
        board.getSpace(0,0).setHeight(Block.LEVEL2);
        board.getSpace(1,1).setHeight(Block.LEVEL3);
        owner.getSelectedWorker().setPosition(board.getSpace(0, 0));
        god.move(owner,board.getSpace(1,1));
        assertTrue(god.getWinCondition(owner));
    }
    @Test
    public void testNormalWin_false() {
        owner.selectWorker(owner.getWorkers()[0]);
        board.getSpace(0,0).setHeight(Block.LEVEL3);
        board.getSpace(1,1).setHeight(Block.LEVEL1);
        owner.getSelectedWorker().setPosition(board.getSpace(0, 0));
        god.move(owner,board.getSpace(1,1));
        assertFalse(god.getWinCondition(owner));
    }
    @Test
    public void testArtemisOptionalMove() {
        owner.selectWorker(owner.getWorkers()[0]);
        owner.getSelectedWorker().setPosition(board.getSpace(0, 0));
        god.move(owner,board.getSpace(1,1));
        assertFalse(god.getOptionalMove(owner).contains(board.getSpace(0, 0)));
    }
}