package it.polimi.ingsw.PSP50.Controller;

import it.polimi.ingsw.PSP50.Model.GodsList.God;
import it.polimi.ingsw.PSP50.Model.Player;

public class PlayerManager{

    Player createPlayer (String id)
    {
        return new Player(id);
    }

  /*  void makeMove()
    {
        //moves one of the player's workers
    }

    void makeBuild()
    {
        //builds using one of the player's workers
    }

    void forcedMove()
    {
        //moves a player's worker when an opponent forces it to do so
    }

   */
}
