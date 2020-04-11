package it.polimi.ingsw.PSP50.Controller.TurnManagers;

import it.polimi.ingsw.PSP50.Model.*;

public abstract class TurnManager {

    private Player player;
    private Board board;
    private Game game;

    abstract void startTurn();
    {
        //initializes a new turn
    }

    void nextPhase()
    {
        //  advances to the next phase of the turn
    }

    void endTurn()
    {
        //notifies that the current turn is over.
    }

}
