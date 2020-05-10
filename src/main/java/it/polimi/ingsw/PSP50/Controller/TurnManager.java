package it.polimi.ingsw.PSP50.Controller;

import it.polimi.ingsw.PSP50.Model.*;
import it.polimi.ingsw.PSP50.Model.GodsList.God;
import it.polimi.ingsw.PSP50.Observable;
import it.polimi.ingsw.PSP50.Observer;
import it.polimi.ingsw.PSP50.View.VirtualView;
import it.polimi.ingsw.PSP50.network.messages.Message;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;
import it.polimi.ingsw.PSP50.network.messages.ToClient.SelectBlockMessage;
import it.polimi.ingsw.PSP50.network.messages.ToClient.SelectBuildMessage;
import it.polimi.ingsw.PSP50.network.messages.ToClient.SelectMoveMessage;
import it.polimi.ingsw.PSP50.network.messages.ToClient.SelectWorkerMessage;

import java.util.ArrayList;
import java.util.Random;

public class TurnManager implements Observer,Runnable{

    private final VirtualView virtualView;
    private final Game game;
    private final Player player;
    private final God god;
    private Board board; //testing
    private ArrayList<Phase> steps;
    private ArrayList <Worker> blockedWorkers;
    private Object receiver=null;


    TurnManager(Game game,Player player, VirtualView virtualView){
        this.game = game;
        this.player = player;
        this.virtualView= virtualView;
        this.god = player.getGod();
        this.steps = new ArrayList<>(god.getAvailableSteps());
        this.blockedWorkers= new ArrayList<>();
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void run(){
        playTurn();
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
                    //create timeout(30 sec)
                    // get the space that the user has selected
                    if ((receiver!= null) && ( spaceChoice.contains(receiver))) {
                        playerSpace= (Space) receiver;
                    }
                    // otherwise get a random spaces between those available
                    else {
                        receiver= spaceChoice.get(new Random().nextInt(spaceChoice.size()) - 1);
                        playerSpace= (Space)receiver;
                    }
                    god.move(player,playerSpace);
                    game.notifyChange();

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

                    //create timeout(30 sec)
                    if ((receiver!= null) && ( spaceChoice.contains(receiver))) {
                        playerSpace= (Space)receiver;
                    }
                    // otherwise get a random spaces between those available
                    else {
                        receiver= spaceChoice.get(new Random().nextInt(spaceChoice.size()) - 1);
                        playerSpace= (Space)receiver;
                    }

                    //if Atlas, get the Block selected by the user
                    if (god.getName()==GodsNames.ATLAS) {
                        virtualView.sendToClient(new SelectBlockMessage(spaceChoice));
                        //create timeout(30 sec)
                        // get the block that the user has selected
                        if ((receiver!= null) && ( spaceChoice.contains(receiver))) {
                            playerBlock= (Block) receiver;
                        }
                        // otherwise get the default block
                        else {
                            playerBlock= playerSpace.getNextHeight();
                        }
                    }
                    else {
                        playerBlock= playerSpace.getNextHeight();
                    }

                    god.build(player,playerSpace,playerBlock);
                    game.notifyChange();

                case OPTIONALMOVE:
                    spaceChoice = god.getOptionalMove(player);
                    if (spaceChoice.isEmpty()) {
                        //the view prints a message
                        break;
                    }

                        //give the space choices to the view
                    virtualView.sendToClient(new SelectMoveMessage(spaceChoice));

                    //create timeout(30 sec)
                    // get the space that the user has selected
                    if ((receiver!= null) && ( spaceChoice.contains(receiver))) {
                        playerSpace= (Space) receiver;
                    }
                    // otherwise get a random spaces between those available
                    else {
                        receiver= spaceChoice.get(new Random().nextInt(spaceChoice.size()) - 1);
                        playerSpace= (Space)receiver;
                    }
                    god.move(player,playerSpace);
                    game.notifyChange();


                    if (god.getWinCondition(player)) return true;

                case OPTIONALBUILD:
                    spaceChoice = god.getOptionalBuild(player);
                    if (spaceChoice.isEmpty()) {
                        //the view prints a message
                        break;
                    }
                    //give the space choices to the view
                    virtualView.sendToClient(new SelectBuildMessage(spaceChoice));

                    //create timeout(30 sec)
                    if ((receiver!= null) && ( spaceChoice.contains(receiver))) {
                        playerSpace= (Space)receiver;
                    }
                    // otherwise get a random spaces between those available
                    else {
                        receiver= spaceChoice.get(new Random().nextInt(spaceChoice.size()) - 1);
                        playerSpace= (Space)receiver;
                    }

                    playerBlock= playerSpace.getNextHeight();
                    god.build(player,playerSpace,playerBlock);
                    game.notifyChange();

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


    @Override
    public void update(Message message) {
        receiver= ((ToServerMessage)message).castChoice();
    }

    @Override
    public void setObservable(Observable observable) {
        this.virtualView.register(this);
    }
}
