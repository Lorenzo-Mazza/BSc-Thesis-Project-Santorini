package it.polimi.ingsw.PSP50.Controller;

import it.polimi.ingsw.PSP50.Model.*;
import it.polimi.ingsw.PSP50.Model.GodsList.God;

import java.util.ArrayList;

public abstract class TurnManager {

    private Player player;
    private God god;
    private Board board;
    private Game game;
    private Phase turnPhase;
    private ArrayList<Phase> steps;

    TurnManager(Player player,God god, Game game){
        this.player= player;
        this.god= god;
        this.game= game;
        this.board=game.getBoard();
        this.steps= new ArrayList<>(god.getAvailableSteps());
    }

    public void playTurn (Player player) {
        ArrayList <Space> spaceChoice;
        while (steps.get(0) != null) {
            turnPhase= steps.get(0);

            //Get Selected Worker from the View
            //Save the choice in player.selectedWorker

            switch (turnPhase){
                case MOVE:
                    spaceChoice= god.getAvailableMove(player);
                    //give the space choices to the view
                    // get the space that the user has selected and use it to call god.move(player,space)


                case BUILD:
                    spaceChoice= god.getAvailableBuild(player);
                    //give the space choices to the view
                    // get the space that the user has selected and use it to call god.getAvailableBlock(player,space)
                    //get the Block selected by the user and use it to call god.build(player,space,block)


                case OPTIONALMOVE:
                    spaceChoice= god.getOptionalMove(player);
                    //give the space choices to the view
                    // get the space that the user has selected and use it to call god.Move(player,space)



                case OPTIONALBUILD:
                    spaceChoice= god.getOptionalBuild(player);
                    //give the space choices to the view
                    // get the space that the user has selected and use it to call god.getAvailableBlock(player,space)
                    //get the Block selected by the user and use it to call god.build(player,space,block)


            }
            //set various parameters
            steps.remove(0);
        }

        //check Win Condition at the end of the turn
        // set all the player/worker parameters correctly


    }

}
