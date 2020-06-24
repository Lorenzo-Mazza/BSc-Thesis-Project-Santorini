package it.polimi.ingsw.PSP50.Model;

import it.polimi.ingsw.PSP50.Model.GodsList.God;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DeckTest {
    private Deck deck;
    @Before
    public void setUp() {
        deck = new Deck();
    }

    @After
    public void tearDown() {
        deck = null;
    }

    @Test
    public void testGods() {
        assertEquals(GodsNames.APOLLO,deck.getCard(0).getName());
        assertEquals(GodsNames.ARTEMIS,deck.getCard(1).getName());
        assertEquals(GodsNames.ATHENA,deck.getCard(2).getName());
        assertEquals(GodsNames.ATLAS,deck.getCard(3).getName());
        assertEquals(GodsNames.DEMETER,deck.getCard(4).getName());
        assertEquals(GodsNames.HEPHAESTUS,deck.getCard(5).getName());
        assertEquals(GodsNames.MINOTAUR,deck.getCard(6).getName());
        assertEquals(GodsNames.PAN,deck.getCard(7).getName());
        assertEquals(GodsNames.PROMETHEUS,deck.getCard(8).getName());
    }

    @Test
    public void getChosenCards() {
        ArrayList<God> gods = deck.getChosenCards(GameType.TWOPLAYERS);
        assertEquals(2, gods.size());
        Deck deck2 = new Deck();
        gods = deck2.getChosenCards(GameType.THREEPLAYERS);
        assertEquals(3, gods.size());
    }
}