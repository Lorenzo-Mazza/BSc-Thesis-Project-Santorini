package it.polimi.ingsw.PSP50.Controller;

import it.polimi.ingsw.PSP50.Model.*;
import it.polimi.ingsw.PSP50.Model.GodsList.God;
import it.polimi.ingsw.PSP50.Observer;
import it.polimi.ingsw.PSP50.View.VirtualView;
import it.polimi.ingsw.PSP50.network.messages.ClientMessage;
import it.polimi.ingsw.PSP50.network.messages.Message;
import it.polimi.ingsw.PSP50.network.messages.ServerMessage;
import it.polimi.ingsw.PSP50.network.messages.ToClient.SelectBuildMessage;
import it.polimi.ingsw.PSP50.network.messages.ToClient.SelectMoveMessage;
import it.polimi.ingsw.PSP50.network.messages.ToClient.SelectWorkerMessage;

import java.util.ArrayList;

public class TurnManager {

    private final VirtualView virtualView;
    private Player player;
    private God god;
    private Board board; //testing
    private ArrayList<Phase> steps;
    private ArrayList <Worker> blockedWorkers;


    TurnManager(Player player, VirtualView virtualView){
        this.player = player;
        this.virtualView= virtualView;
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
        virtualView.sendToClient(new SelectWorkerMessage(player.getWorkers()));

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
                    virtualView.sendToClient(new SelectMoveMessage(spaceChoice));
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
                    virtualView.sendToClient(new SelectBuildMessage(spaceChoice));

                    //get the space that the user has selected and use it to call god.getAvailableBlock(player,space)
                    playerSpace= new Space(1,1,board);

                    //if Atlas, get the Block selected by the user and use it to call god.build(player,space,block)
                    // else, do nothing
                    playerBlock= playerSpace.getNextHeight();
                    god.build(player,playerSpace,playerBlock);

                case OPTIONALMOVE:
                    spaceChoice = god.getOptionalMove(player);
                    if (spaceChoice.isEmpty()) {
                        //the view prints a message
                        break;
                    }

                        //give the space choices to the view
                    virtualView.sendToClient(new SelectMoveMessage(spaceChoice));

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
                    virtualView.sendToClient(new SelectBuildMessage(spaceChoice));

                    // get the space that the user has selected and use it to call god.getAvailableBlock(player,space)
                    playerSpace= new Space(1,1,board);
                    //get the Block selected by the user and use it to call god.build(player,space,block)
                    playerBlock= playerSpace.getNextHeight();
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

    public void selectWorker(){
        // SelectWorkerMessage message= new SelectWorkerMessage();
      //  message.setReceiver(this.getPlayer().getName());

    }



}
