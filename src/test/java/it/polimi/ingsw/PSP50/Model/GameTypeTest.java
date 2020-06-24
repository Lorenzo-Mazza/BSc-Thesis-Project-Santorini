package it.polimi.ingsw.PSP50.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameTypeTest {

    @Test
    public void getSize() {
        assertEquals(2,GameType.TWOPLAYERS.getSize());
        assertEquals(3,GameType.THREEPLAYERS.getSize());
    }
}