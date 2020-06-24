package it.polimi.ingsw.PSP50.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ColorTest {

    @Test
    public void testName() {
        assertEquals("Orange",Color.ORANGE.getName());
        assertEquals("Pink",Color.PINK.getName());
        assertEquals("Blue",Color.BLUE.getName());
    }

    @Test
    public void testSequence() {
        assertEquals("\u001b[32m",Color.ORANGE.getSequence());
        assertEquals("\u001b[35m",Color.PINK.getSequence());
        assertEquals("\u001b[34m",Color.BLUE.getSequence());
    }
}