package it.polimi.ingsw.PSP50.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;

    @Before
    public void setUp() {
        board = new Board();
    }

    @After
    public void tearDown() {
        board = null;
    }

    @Test
    public void getSpace_correct() {
        for (int i = 0; i<5; i++)
            for ( int j = 0; j<5; j++)
                assertNotNull(board.getSpace(i,j));
    }
}