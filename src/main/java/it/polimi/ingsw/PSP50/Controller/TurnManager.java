package it.polimi.ingsw.PSP50.Controller;

import it.polimi.ingsw.PSP50.Model.*;
import it.polimi.ingsw.PSP50.Model.GodsList.God;

import java.util.ArrayList;

public class TurnManager {

    private Player player;
    private God god;
    private Board board; //testing
    private ArrayList<Phase> steps;
    private ArrayList <Worker> blockedWorkers;


    TurnManager(Player player){
        this.player = player;
        this.god = player.getGod();
        this.steps = new ArrayList<>(god.getAvailableSteps());
        this.blockedWorkers= new ArrayList<>();
    }

    public Player getPlayer() {
        return player;
    }

    public boolean playTurn () {
        ArrayList <Space> spaceChoice;

        //Get Selected Worker from the View
        //Save the choice in player.selectedWorker

        //First and only check
        spaceChoice = god.getAvailableMove(player);
        int counter=0;
        while (spaceChoice.isEmpty())
        {
            if (!blockedWorkers.contains(player.getSelectedWorker()))
            {
                blockedWorkers.add(player.getSelectedWorker());
            }

            if (blockedWorkers.size()==2 || counter==5)
            {
                player.setHasLost(true);
                return false;
            }
            else
            //select another worker
            spaceChoice = god.getAvailableMove(player);
            counter ++;
        }


        while (steps.get(0) != null) {

            Phase turnPhase= steps.get(0);
            Space playerSpace; //testing
            Block playerBlock; //testing


            switch (turnPhase){
                case MOVE:
                    spaceChoice = god.getAvailableMove(player);
                    if (spaceChoice.isEmpty()) {
                        // the player has lost, go back to the game manager
                        player.setHasLost(true);
                        return false;
                    }

                    //give the space choices to the view
                    // get the space that the user has selected and use it to call god.move(player,space)
                    playerSpace= new Space(0,0,board);
                    god.move(player,playerSpace);

                    if (god.getWinCondition(player)) return true;

                case BUILD:
                    spaceChoice = god.getAvailableBuild(player);
                    if (spaceChoice.isEmpty()) {
                        // the player has lost, go back to the game manager
                        player.setHasLost(true);
                        return false;
                    }

                    //give the space choices to the view
                    //get the space that the user has selected and use it to call god.getAvailableBlock(player,space)
                    playerSpace= new Space(1,1,board);
                    //get the Block selected by the user and use it to call god.build(player,space,block)
                    playerBlock= Block.LEVEL1;
                    god.build(player,playerSpace,playerBlock);

                case OPTIONALMOVE:
                    spaceChoice = god.getOptionalMove(player);
                    if (spaceChoice.isEmpty()) {
                        //the view prints a message
                        break;
                    }

                        //give the space choices to the view
                    // get the space that the user has selected and use it to call god.Move(player,space)
                    playerSpace= new Space(0,0,board);
                    god.move(player,playerSpace);


                    if (god.getWinCondition(player)) return true;

                case OPTIONALBUILD:
                    spaceChoice = god.getOptionalBuild(player);
                    if (spaceChoice.isEmpty()) {
                        //the view prints a message
                        break;
                    }
                    //give the space choices to the view
                    // get the space that the user has selected and use it to call god.getAvailableBlock(player,space)
                    playerSpace= new Space(1,1,board);
                    //get the Block selected by the user and use it to call god.build(player,space,block)
                    playerBlock= Block.LEVEL1;
                    god.build(player,playerSpace,playerBlock);


            }

            steps.remove(0);


        }

        player.setHasBuilt(false);
        if (player.isPlayerBlocked())
            player.setPlayerBlocked(false);

        // set all the player/worker parameters correctly
        blockedWorkers=null;

        return false;


    }

}
