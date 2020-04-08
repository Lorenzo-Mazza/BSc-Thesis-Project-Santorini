package it.polimi.ingsw.PSP50;

public class PlayerManager{

    Player createPlayer (String id)
    {
        Player new_player = new Player();
        new_player.setName(id);
        return new_player;
    }

    void makeMove()
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
}
