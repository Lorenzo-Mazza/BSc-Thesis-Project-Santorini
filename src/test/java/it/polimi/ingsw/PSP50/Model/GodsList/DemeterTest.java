package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DemeterTest {
    private God god;
    private Player owner;
    private Board board;

    @Before
    public void setUp() {
        god = new Demeter();
        owner = new Player("testing");
        board = new Board();
    }

    @After
    public void tearDown() {
        god = null;
        owner = null;
        board = null;
    }


    // Testing that the Steps array is correct and it represents the game turn of the god card
    @Test
    public void stepsTest() {
        assertEquals(Phase.MOVE, god.getAvailableSteps().get(0));
        assertEquals(Phase.BUILD, god.getAvailableSteps().get(1));
        assertEquals(Phase.OPTIONALBUILD, god.getAvailableSteps().get(2));
    }

    @Test
    public void nameTest() {
        assertEquals(GodsNames.DEMETER, god.getName());
    }

    // Testing the normal getAvailableMove function
    @Test
    public void testNormalAvailableMove() {
        owner.selectWorker(owner.getWorkers()[0]);
        owner.getSelectedWorker().setPosition(board.getSpace(0, 0));
        board.getSpace(0,1).setHeight(Block.DOME);
        ArrayList<Space> availableMoves = new ArrayList<>();
        availableMoves.add(board.getSpace(1, 0));
        availableMoves.add(board.getSpace(1, 1));
        assertThat(god.getAvailableMove(owner), is(availableMoves));
    }

    // Testing the normal Move action
    @Test
    public void testNormalMove() {
        owner.selectWorker(owner.getWorkers()[0]);
        owner.getSelectedWorker().setPosition(board.getSpace(0, 0));
        god.move(owner, board.getSpace(1, 1));
        assertEquals(board.getSpace(1, 1), owner.getSelectedWorker().getPosition());
        assertEquals(board.getSpace(1, 1).getWorker(), owner.getSelectedWorker());
        assertNull(board.getSpace(0,0).getWorker());
    }

    // Testing the normal getAvailableBuild function
    @Test
    public void testNormalAvailableBuild() {
        owner.selectWorker(owner.getWorkers()[0]);
        owner.getSelectedWorker().setPosition(board.getSpace(0, 0));
        ArrayList<Space> availableBuilds = new ArrayList<>();
        availableBuilds.add(board.getSpace(0, 1));
        availableBuilds.add(board.getSpace(1, 0));
        availableBuilds.add(board.getSpace(1, 1));
        assertThat(god.getAvailableBuild(owner), is(availableBuilds));
    }

    // Testing the normal Build action
    @Test
    public void testNormalBuild() {
        owner.selectWorker(owner.getWorkers()[0]);
        owner.getSelectedWorker().setPosition(board.getSpace(0, 0));
        board.getSpace(1, 1).setHeight(Block.LEVEL3);
        god.build(owner, board.getSpace(1, 1), Block.DOME);
        assertEquals(Block.DOME, board.getSpace(1, 1).getHeight());
    }

    // Testing the normal getOptionalMove function
    @Test
    public void testNormalOptionalMove(){
        assertTrue(god.getOptionalMove(owner).isEmpty());
    }

    //Testing Demeter's power: check that the worker can perform an Optional Build action and
    // also that he cannot build where he already built
    @Test
    public void testDemeterOptionalBuild(){
        owner.selectWorker(owner.getWorkers()[0]);
        owner.getSelectedWorker().setPosition(board.getSpace(0, 0));
        god.build(owner,board.getSpace(1,1),Block.LEVEL1);
        ArrayList<Space> availableBuilds = new ArrayList<>();
        availableBuilds.add(board.getSpace(0, 1));
        availableBuilds.add(board.getSpace(1, 0));
        assertThat(god.getOptionalBuild(owner), is(availableBuilds));
    }

    // Testing the normal getAvailableBlock function
    @Test
    public void testNormalAvailableBlock(){
        owner.selectWorker(owner.getWorkers()[0]);
        owner.getSelectedWorker().setPosition(board.getSpace(0, 0));
        owner.setThisBuild(board.getSpace(0,0));
        assertEquals(1, god.getAvailableBlock(owner).size());
    }

    // Testing the normal getWinCondition function when it's true
    @Test
    public void testNormalWin_true() {
        owner.selectWorker(owner.getWorkers()[0]);
        board.getSpace(0,0).setHeight(Block.LEVEL2);
        board.getSpace(1,1).setHeight(Block.LEVEL3);
        owner.getSelectedWorker().setPosition(board.getSpace(0, 0));
        god.move(owner,board.getSpace(1,1));
        assertTrue(god.getWinCondition(owner));
    }

    // Testing the normal getWinCondition function when it's false
    @Test
    public void testNormalWin_false() {
        owner.selectWorker(owner.getWorkers()[0]);
        board.getSpace(0,0).setHeight(Block.LEVEL3);
        board.getSpace(1,1).setHeight(Block.LEVEL1);
        owner.getSelectedWorker().setPosition(board.getSpace(0, 0));
        god.move(owner,board.getSpace(1,1));
        assertFalse(god.getWinCondition(owner));
    }

}