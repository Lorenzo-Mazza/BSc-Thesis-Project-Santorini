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

public class TurnManager implements Observer,Runnable{

    private final VirtualView virtualView;
    private final Game game;
    private final Player player;
    private final God god;
    private ArrayList<Phase> steps;
    private ArrayList <Worker> blockedWorkers;
    private Object receiver=null;
    private int secondsLeft;


    TurnManager(Game game,Player player, VirtualView virtualView){
        this.game = game;
        this.player = player;
        this.virtualView= virtualView;
        this.god = player.getGod();
        this.steps = new ArrayList<>(god.getAvailableSteps());
        this.blockedWorkers= new ArrayList<>();
        this.secondsLeft =30;
        setObservable(this.virtualView);
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
        selectWorker();
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
            //select another worker
            selectWorker();
            spaceChoice = god.getAvailableMove(player);
            counter ++;
        }


        while (steps.get(0) != null) {

            Phase turnPhase= steps.get(0);
            Space playerSpace = null;
            Block playerBlock=null;
            TurnTimer timer;

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
                    //create timer(30 sec)
                    timer= new TurnTimer(secondsLeft);
                    virtualView.sendToClient(new TimerStarted(timer));
                    while (!timer.isInterrupted())
                    {
                        // get the space that the user has selected
                        if ((receiver!= null) && ( spaceChoice.contains(receiver))) {
                            playerSpace= (Space) receiver;
                            timer.endTimer();
                        }
                    }
                    // if timer expired and there's no answer, get a random spaces between those available
                    if (playerSpace==null)
                    {
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
                    timer= new TurnTimer(secondsLeft);
                    virtualView.sendToClient(new TimerStarted(timer));
                    while (!timer.isInterrupted())
                    {
                        // get the space that the user has selected
                        if ((receiver!= null) && ( spaceChoice.contains(receiver))) {
                            playerSpace= (Space) receiver;
                            timer.endTimer();
                        }
                    }

                    // if timer expired and there's no answer, get a random spaces between those available
                    if (playerSpace==null)
                    {
                        receiver= spaceChoice.get(new Random().nextInt(spaceChoice.size()) - 1);
                        playerSpace= (Space)receiver;
                    }

                    //if Atlas, get the Block selected by the user
                    if ((god.getName() == GodsNames.ATLAS) && (playerSpace.getNextHeight() != Block.DOME)) {
                        virtualView.sendToClient(new SelectBlockMessage(playerSpace));
                        //create timeout(30 sec)
                        timer= new TurnTimer(secondsLeft);
                        virtualView.sendToClient(new TimerStarted(timer));
                        while (!timer.isInterrupted())
                        {
                            // get the block that the user has selected
                            if ((receiver!= null) && ( spaceChoice.contains(receiver))) {
                                playerBlock= (Block) receiver;
                                timer.endTimer();
                            }
                        }
                        // if timer expired and there's no answer, get a random spaces between those available
                        if (playerBlock==null)
                        {
                            playerBlock= playerSpace.getNextHeight();
                        }
                    }
                    // otherwise get the default block
                    else {
                        playerBlock = playerSpace.getNextHeight();
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

                    //create timer(30 sec)
                    timer= new TurnTimer(secondsLeft);
                    virtualView.sendToClient(new TimerStarted(timer));
                    while (!timer.isInterrupted())
                    {
                        // get the space that the user has selected
                        if ((receiver!= null) && ( spaceChoice.contains(receiver))) {
                            playerSpace= (Space) receiver;
                            timer.endTimer();
                        }
                    }
                    // if timer expired and there's no answer, get a random spaces between those available
                    if (playerSpace==null)
                    {
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

                    //create timer(30 sec)
                    timer= new TurnTimer(secondsLeft);
                    virtualView.sendToClient(new TimerStarted(timer));
                    while (!timer.isInterrupted())
                    {
                        // get the space that the user has selected
                        if ((receiver!= null) && ( spaceChoice.contains(receiver))) {
                            playerSpace= (Space) receiver;
                            timer.endTimer();
                        }
                    }
                    // if timer expired and there's no answer, get a random spaces between those available
                    if (playerSpace==null)
                    {
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
        Worker selectedWorker=null;
        virtualView.sendToClient(new SelectWorkerMessage(player.getWorkers()));
        //create timeout(30 sec)
        TurnTimer timer= new TurnTimer(secondsLeft);
        virtualView.sendToClient(new TimerStarted(timer));
        while (!timer.isInterrupted())
        {
            // get the worker that the user has selected
            if ((receiver!= null) && ( player.getWorkers()[0].equals(receiver)) || ( player.getWorkers()[1].equals(receiver))) {
                selectedWorker=(Worker) receiver;
                timer.endTimer();
            }
        }
        // if timer expired and there's no answer, get a random spaces between those available
        if (selectedWorker==null)
        {
            selectedWorker= player.getWorkers()[new Random().nextInt( 1)];
        }
        //Save the choice in player.selectedWorker
        player.selectWorker(selectedWorker);
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
