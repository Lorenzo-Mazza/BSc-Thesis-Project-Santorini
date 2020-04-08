package it.polimi.ingsw.PSP50;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private ArrayList<Gods> deck;

    private ArrayList<Gods> chosenCards;


    public Gods getCard(int card){
        return deck.get(card);
    }

  /*  public void shuffle(){
        Random random = new Random();
        for(int index=0; index<deck.size();index++){
            deck.= deck.get(random.nextInt(deck.size()));

        }
    }
   */


}
