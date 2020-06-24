package it.polimi.ingsw.PSP50.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SpaceTest {

    private Space space;
    private Board board;

    @Before
    public void setUp() {
        board = new Board();
        space = new Space(0,0, board);
    }

    @After
    public void tearDown() {
        space = null;
        board = null;
    }

    @Test
    public void testCoordinates() {
        assertEquals(0,space.getXPosition());
        assertEquals(0,space.getYPosition());
        assertEquals(0,space.getCoordinates()[0]);
        assertEquals(0,space.getCoordinates()[1]);
    }

    @Test
    public void testHeight() {
        assertEquals(Block.EMPTY, space.getHeight());
        assertEquals(Block.LEVEL1, space.getNextHeight());
        space.setHeight(Block.LEVEL1);
        assertEquals(Block.LEVEL1, space.getHeight());
        assertEquals(Block.LEVEL2, space.getNextHeight());
    }

    @Test
    public void testSpaceOccupied() {
        assertFalse(space.isOccupied());
        assertNull(space.getWorker());
        Worker test = new Worker(new Player("testing"),0);
        space.setWorker(test);
        assertTrue(space.isOccupied());
        assertEquals(test,space.getWorker());
    }

    @Test
    public void getNeighbors() {
        ArrayList<Space> neighbors = new ArrayList<>();
        neighbors.add(board.getSpace(0,1));
        neighbors.add(board.getSpace(1,0));
        neighbors.add(board.getSpace(1,1));
        assertThat(space.getNeighbors(),is(neighbors));
        Space space2 = board.getSpace(2,2);
        ArrayList<Space> neighborsOfSpace2 = new ArrayList<>();
        neighborsOfSpace2.add(board.getSpace(1,1));
        neighborsOfSpace2.add(board.getSpace(1,2));
        neighborsOfSpace2.add(board.getSpace(1,3));
        neighborsOfSpace2.add(board.getSpace(2,1));
        neighborsOfSpace2.add(board.getSpace(2,3));
        neighborsOfSpace2.add(board.getSpace(3,1));
        neighborsOfSpace2.add(board.getSpace(3,2));
        neighborsOfSpace2.add(board.getSpace(3,3));
        assertThat(space2.getNeighbors(),is(neighborsOfSpace2));
    }

    @Test
    public void testNextSpace() {
        assertFalse(space.thereIsNext(-1,-1));
        assertTrue(space.thereIsNext(1,1));
        Space next = space.getNext(1,1);
        assertEquals(board.getSpace(1,1),next);
        Space space2 = new Space(2,2,board);
        board.getSpace(2,3).setHeight(Block.DOME);
        assertFalse(space2.thereIsNext(0,1));
    }

    @Test
    public void getDirection() {
        int[] movement = space.getDirection(board.getSpace(1,1));
        assertEquals(1,movement[0]);
        assertEquals(1,movement[1]);
        movement = space.getDirection(board.getSpace(1,0));
        assertEquals(1,movement[0]);
        assertEquals(0,movement[1]);
    }
}