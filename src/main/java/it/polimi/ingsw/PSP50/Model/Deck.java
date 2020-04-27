package it.polimi.ingsw.PSP50.Model;

import it.polimi.ingsw.PSP50.Model.GodsList.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * *Description of class*
 */
public class Deck {
    private ArrayList<God> deck= new ArrayList<>();

    private ArrayList<God> chosenCards= new ArrayList<>();

    /**
     * *Constructor*
     */
    public Deck() {
        deck.add(0, new Apollo());
        deck.add(1, new Artemis());
        deck.add(2, new Athena());
        deck.add(3, new Atlas());
        deck.add(4, new Demeter());
        deck.add(5, new Hephaestus());
        deck.add(6, new Minotaur());
        deck.add(7, new Pan());
        deck.add(8, new Prometheus());
    }

    /**
     * *Description of method*
     * @param card an integer that select one card from deck
     * @return the selected God picked from the deck of cards
     */
    public God getChosenCard(int card){
        return deck.get(card);
    }

    private void shuffle() {
        Collections.shuffle(deck, new Random());
    }

    /**
     * *Description of method*
     * @param type an GameType that indicate how many players are playing
     * @return an ArrayList of God with size indicated by type
     */
    public ArrayList<God> getChosenCards(GameType type) {
        shuffle();
        for(int index = 0; index < type.getSize(); index++)
            this.chosenCards.add(deck.get(index));

        return this.chosenCards;
    }
}
