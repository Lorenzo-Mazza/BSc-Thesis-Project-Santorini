package it.polimi.ingsw.PSP50.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BlockTest {

    @Test
    public void testValue() {
        assertEquals(0,Block.EMPTY.getValue());
        assertEquals(1,Block.LEVEL1.getValue());
        assertEquals(2,Block.LEVEL2.getValue());
        assertEquals(3,Block.LEVEL3.getValue());
        assertEquals(10,Block.DOME.getValue());
    }
}