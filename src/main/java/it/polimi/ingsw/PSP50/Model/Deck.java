package it.polimi.ingsw.PSP50.Model;

import it.polimi.ingsw.PSP50.Model.GodsList.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    private ArrayList<God> deck;

    private ArrayList<God> chosenCards;

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

    public God getChosenCard(int card){
        return deck.get(card);
    }

    private void shuffle() {
        Collections.shuffle(deck, new Random());
    }

    public ArrayList<God> getChosenCards(GameType type) {
        shuffle();
        for(int index = 0; index < type.getSize(); index++)
            this.chosenCards.add(deck.get(index));

        return this.chosenCards;
    }
}
