package it.polimi.ingsw.PSP50.Model;

import it.polimi.ingsw.PSP50.Model.GodsList.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Deck contains all the God cards
 */
public class Deck implements Serializable {
    /**
     *  Deck is an ArrayList containing every God card
     */
    private ArrayList<God> deck= new ArrayList<>();
    /**
     *  chosenCards is an ArrayList containing the Gods that wil be used in the game
     */
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
     *
     * @param card an index that selects one card from deck
     * @return the selected God picked from the deck
     */
    public God getCard(int card){
        return deck.get(card);
    }


    /**
     * Shuffles the deck cards randomly
     */
    private void shuffle() {
        Collections.shuffle(deck, new Random());
    }

    /**
     * Gets a number of cards equal to the number of players in the game
     * @param type a GameType that indicate how many players are playing
     * @return an ArrayList of God cards
     */
    public ArrayList<God> getChosenCards(GameType type) {
        shuffle();
        for(int index = 0; index < type.getSize(); index++)
            this.chosenCards.add(deck.get(index));

        return this.chosenCards;
    }
}
