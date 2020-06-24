package it.polimi.ingsw.PSP50.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class WorkerTest {
    private Player player = null;
    private Worker worker = null;
    private Board board = null;

    @Before
    public void setUp() {
        // Creates a test worker that has a Worker ID = 0 and an owner called "testing"
        player = new Player("testing");
        worker = new Worker(player,0);
        board = new Board();
    }

    @After
    public void tearDown() {
        player = null;
        worker = null;
    }

    @Test
    public void getOwner() {
        Player owner = worker.getOwner();
        assertEquals(player,owner);
    }

    @Test
    public void getWorkerId() {
        int id = worker.getWorkerId();
        assertEquals(0,id);
    }

    @Test
    public void positionTest() {
        assertNull(worker.getLastPosition());
        assertNull(worker.getPosition());
        // place worker on space 0,0
        worker.setPosition(board.getSpace(0,0));
        assertEquals(board.getSpace(0,0),worker.getPosition());
        assertNull(worker.getLastPosition());
        // move worker on space 1,1
        worker.move(board.getSpace(1,1));
        // check worker position and last position
        assertEquals(board.getSpace(1,1),worker.getPosition());
        assertEquals(board.getSpace(0,0),worker.getLastPosition());
    }

    @Test
    public void build() {
        assertEquals(board.getSpace(2,2).getHeight(),Block.EMPTY);
        worker.build(board.getSpace(2,2));
        assertEquals(board.getSpace(2,2).getHeight(),Block.LEVEL1);
    }

    @Test
    public void getMovable_correct() {
        worker.move(board.getSpace(0,0));
        Worker opponent = new Worker(new Player("opponent"), 0);
        opponent.setPosition(board.getSpace(0,1));
        ArrayList<Space> movable = worker.getMovable();
        ArrayList<Space> correctMovable = new ArrayList<>();
        correctMovable.add(board.getSpace(1,0));
        correctMovable.add(board.getSpace(1,1));
        assertEquals(correctMovable.get(0),movable.get(0));
        assertEquals(correctMovable.get(1),movable.get(1));
    }

    @Test
    public void getBuildable_correct() {
        worker.move(board.getSpace(0,0));
        board.getSpace(1,1).setHeight(Block.DOME);
        board.getSpace(0,1).setHeight(Block.DOME);
        ArrayList<Space> buildable = worker.getBuildable();
        ArrayList<Space> correctBuildable = new ArrayList<>();
        correctBuildable.add(board.getSpace(1,0));
        assertEquals(correctBuildable.get(0),buildable.get(0));
    }


        @Test
    public void getMovableWithWorkers_correct() {
        worker.move(board.getSpace(0,0));
        Worker opponent = new Worker(new Player("opponent"), 0);
        opponent.setPosition(board.getSpace(0,1));
        board.getSpace(1,1).setHeight(Block.DOME);
        ArrayList<Space> movable = worker.getMovableWithWorkers();
        ArrayList<Space> correctMovable = new ArrayList<>();
        correctMovable.add(board.getSpace(0,1));
        correctMovable.add(board.getSpace(1,0));
        assertEquals(correctMovable.get(0),movable.get(0));
        assertEquals(correctMovable.get(1),movable.get(1));
        }


    }