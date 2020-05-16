package it.polimi.ingsw.PSP50.Controller;

import it.polimi.ingsw.PSP50.Model.*;
import it.polimi.ingsw.PSP50.Model.GodsList.God;
import it.polimi.ingsw.PSP50.Observable;
import it.polimi.ingsw.PSP50.Observer;
import it.polimi.ingsw.PSP50.TurnTimer;
import it.polimi.ingsw.PSP50.View.VirtualView;
import it.polimi.ingsw.PSP50.network.messages.Message;
import it.polimi.ingsw.PSP50.network.messages.ToClient.*;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;

import java.util.ArrayList;
import java.util.Random;

import static it.polimi.ingsw.PSP50.network.messages.ToServer.NoAction.NO_ACTION;

public class TurnManager implements Observer{

    private final VirtualView virtualView;
    private final Game game;
    private final Player player;
    private final God god;
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
        setObservable(this.virtualView);
    }

    public Player getPlayer() {
        return player;
    }


    public boolean playTurn () {
        ArrayList <Space> spaceChoice=null;

        //Get Selected Worker from the View
        selectWorker();
        //First and only check
        if (god.getAvailableMove(player)!=null)
            spaceChoice = god.getAvailableMove(player);
        int counter=0;
        while (spaceChoice==null)
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
            //select another worker
            selectWorker();
            if (god.getAvailableMove(player)!=null)
                spaceChoice = god.getAvailableMove(player);
            counter ++;
        }


        while (steps.size() != 0) {

            Phase turnPhase= steps.get(0);
            Space playerSpace = null;
            Block playerBlock=null;

            switch (turnPhase){
                case MOVE:
                    spaceChoice = god.getAvailableMove(player);
                    if (spaceChoice.isEmpty()) {
                        // the player has lost, go back to the game manager
                        player.setHasLost(true);
                        return false;
                    }

                    //give the space choices to the view
                    virtualView.sendToClient(new SelectMoveMessage(spaceChoice,false));
                    while (receiver==null)
                    {
                        Thread.yield();
                    }
                    // get the space that the user has selected
                    if (( spaceChoice.contains(receiver))) {
                        playerSpace= (Space) receiver;
                    }
                    // if there's no correct answer, get a random spaces between those available
                    else
                    {
                        receiver= spaceChoice.get(new Random().nextInt(spaceChoice.size()) - 1);
                        playerSpace= (Space)receiver;
                    }
                    god.move(player,playerSpace);
                    game.notifyChange();
                    receiver=null;

                    if (god.getWinCondition(player)) return true;
                    break;

                case BUILD:
                    spaceChoice = god.getAvailableBuild(player);
                    if (spaceChoice.isEmpty()) {
                        // the player has lost, go back to the game manager
                        player.setHasLost(true);
                        return false;
                    }

                    //give the space choices to the view
                    virtualView.sendToClient(new SelectBuildMessage(spaceChoice,false));
                    while (receiver==null)
                    {
                        Thread.yield();
                    }
                    // get the space that the user has selected
                    if (( spaceChoice.contains(receiver))) {
                        playerSpace= (Space) receiver;
                    }
                    // if there's no correct answer, get a random spaces between those available
                    else
                    {
                        receiver= spaceChoice.get(new Random().nextInt(spaceChoice.size()) - 1);
                        playerSpace= (Space)receiver;
                    }

                    //if Atlas, get the Block selected by the user
                    if ((god.getName() == GodsNames.ATLAS) && (playerSpace.getNextHeight() != Block.DOME)) {
                        virtualView.sendToClient(new SelectBlockMessage(playerSpace));
                        // get the block that the user has selected
                        while (receiver==null)
                        {
                            Thread.yield();
                        }
                        if (( playerSpace.getNextHeight()== (Block)receiver)|| (Block.DOME== (Block)receiver)) {
                            playerBlock= (Block) receiver;
                        }
                        // if timer expired and there's no answer, get a random spaces between those available
                        else {
                            playerBlock= playerSpace.getNextHeight();
                        }
                    }
                    // otherwise get the default block
                    else {
                        playerBlock = playerSpace.getNextHeight();
                    }
                    god.build(player,playerSpace,playerBlock);
                    receiver=null;
                    game.notifyChange();
                    break;

                case OPTIONALMOVE:
                    spaceChoice = god.getOptionalMove(player);
                    if (spaceChoice.isEmpty()) {
                        //the view prints a message
                        break;
                    }

                    //give the space choices to the view
                    virtualView.sendToClient(new SelectMoveMessage(spaceChoice,true));
                    while (receiver==null)
                    {
                        Thread.yield();
                    }
                    // get the space that the user has selected (DEBUG, Maybe it's useless)
                    if (receiver instanceof Integer){
                        //do nothing
                    }
                    else if ( spaceChoice.contains(receiver)) {
                        playerSpace= (Space) receiver;
                        god.move(player,playerSpace);
                        game.notifyChange();
                    }

                    receiver=null;

                    if (god.getWinCondition(player)) return true;
                    break;


                case OPTIONALBUILD:
                    spaceChoice = god.getOptionalBuild(player);
                    if (spaceChoice.isEmpty()) {
                        //the view prints a message
                        break;
                    }
                    //give the space choices to the view
                    virtualView.sendToClient(new SelectBuildMessage(spaceChoice,true));
                    while (receiver==null)
                    {
                        Thread.yield();
                    }
                    // get the space that the user has selected (DEBUG, Maybe it's useless)
                    if (receiver instanceof Integer){
                        //do nothing
                    }
                    else if ((receiver!= null) && ( spaceChoice.contains(receiver))) {
                        playerSpace= (Space) receiver;
                        playerBlock= playerSpace.getNextHeight();
                        god.build(player,playerSpace,playerBlock);
                        game.notifyChange();
                    }
                    receiver=null;
                    break;



            }

            steps.remove(0);


        }

        player.setHasBuilt(false);
        if (player.isPlayerBlocked())
            player.setPlayerBlocked(false);

        // set all the player/worker parameters correctly, restore the steps available
        blockedWorkers=null;
        this.steps = new ArrayList<>(god.getAvailableSteps());

        return false;


    }

    public void selectWorker(){
        Worker selectedWorker=null;
        ArrayList<Space> workersPosition = new ArrayList<>();
        workersPosition.add(player.getWorkers()[0].getPosition());
        workersPosition.add(player.getWorkers()[1].getPosition());
        virtualView.sendToClient(new SelectWorkerMessage(player.getWorkers()));
        while (receiver==null)
        {
            Thread.yield();
        }
        // get the worker that the user has selected
        if (workersPosition.contains(receiver))
        {
            selectedWorker=((Space) receiver).getWorker();
        }
        else{
            selectedWorker= player.getWorkers()[new Random().nextInt( 1)];
        }
      /*  // if there's no answer, get a random spaces between those available
        if (selectedWorker==null)
        {
            selectedWorker= player.getWorkers()[new Random().nextInt( 1)];
        } */

        //Save the choice in player.selectedWorker
        player.selectWorker(selectedWorker);
        receiver=null;
    }

    /*
     ** Update the class whenever the Virtual View notifies a new message
     */
    @Override
    public void update(Message message) {
        receiver= ((ToServerMessage)message).castChoice();
    }

    /*
    ** Set the Virtual View as an Observable for this class
     */
    @Override
    public void setObservable(Observable observable) {
        this.virtualView.register(this);
    }
}
