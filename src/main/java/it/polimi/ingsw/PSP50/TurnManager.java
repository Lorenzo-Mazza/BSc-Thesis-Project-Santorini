package it.polimi.ingsw.PSP50;

public abstract class TurnManager {

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
