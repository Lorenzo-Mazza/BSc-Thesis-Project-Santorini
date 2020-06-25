package it.polimi.ingsw.PSP50.Controller;

import it.polimi.ingsw.PSP50.Model.*;
import it.polimi.ingsw.PSP50.Model.GodsList.God;
import it.polimi.ingsw.PSP50.Utils.Observable;
import it.polimi.ingsw.PSP50.Utils.Observer;
import it.polimi.ingsw.PSP50.View.VirtualView;
import it.polimi.ingsw.PSP50.network.messages.Message;
import it.polimi.ingsw.PSP50.network.messages.ToClient.*;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;

import java.util.ArrayList;
import java.util.Random;

/**
 * Turn Manager represent the game turn logic of each player, depending on the Player's God card
 */
public class TurnManager implements Observer{
    /**
     * Virtual view referenced by the turn manager
     */
    private final VirtualView virtualView;
    /**
     * Game referenced by the turn manager
     */
    private final Game game;
    /**
     * Player controlled by the turn manager
     */
    private final Player player;
    /**
     * Player's god
     */
    private final God god;
    /**
     * List of the steps that the player will go through in his turn
     */
    private ArrayList<Phase> steps;
    /**
     * List of the workers that the player cannot move in his turn because they are blocked
     */
    private ArrayList <Worker> blockedWorkers;
    /**
     * Object used to receive View-side messages
     */
    private Object receiver=null;

    /**
     * Constructor
     */
    TurnManager(Game game,Player player, VirtualView virtualView){
        this.game = game;
        this.player = player;
        this.virtualView= virtualView;
        this.god = player.getGod();
        this.steps = new ArrayList<>(god.getAvailableSteps());
        this.blockedWorkers= new ArrayList<>();
        setObservable(this.virtualView);
    }

    /**
     * @return the player controlled by the turn manager
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Main function of the class: it plays a complete turn start to finish
     * @return a boolean that is true if the player has won during the turn, false otherwise
     */
    public boolean playTurn () {
        ArrayList <Space> spaceChoice=null;

        //Get Selected Worker from the View
        selectWorker();
        //First and only check
        if (god.getAvailableMove(player).isEmpty()) {
            int counter = 0;
            while(spaceChoice == null)
            {
                if (!blockedWorkers.contains(player.getSelectedWorker())) {
                    blockedWorkers.add(player.getSelectedWorker());
                }

                if (blockedWorkers.size() == 2 || counter == 3) {
                    player.setHasLost(true);
                    return false;
                }
                //select another worker
                virtualView.sendToClient(new WorkerBlocked(player.getSelectedWorker()));
                selectWorker();
                if (!god.getAvailableMove(player).isEmpty())
                    spaceChoice = god.getAvailableMove(player);
                counter++;
            }
        }


        while (steps.size() != 0) {

            Phase turnPhase= steps.get(0);
            Space playerSpace = null;
            Block playerBlock=null;

            switch (turnPhase){
                case MOVE:
                    if (god.getAvailableMove(player).isEmpty()) {
                        // the player has lost, go back to the game manager
                        player.setHasLost(true);
                        return false;
                    }
                    spaceChoice = god.getAvailableMove(player);

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
                     if (god.getAvailableBuild(player).isEmpty()) {
                        // the player has lost, go back to the game manager
                        player.setHasLost(true);
                        return false;
                    }
                    spaceChoice = god.getAvailableBuild(player);
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
                        receiver=null;
                        virtualView.sendToClient(new SelectBlockMessage(playerSpace));
                        // get the block that the user has selected
                        while (receiver==null)
                        {
                            Thread.yield();
                        }
                        if (( playerSpace.getNextHeight()== (Block) receiver) || (Block.DOME== (Block)receiver))
                        {
                            playerBlock= (Block) receiver;
                        }
                        else {
                            // if timer expired and there's no answer, get a random spaces between those available
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
                    if (god.getOptionalMove(player).isEmpty()) {
                        //it's optional so do nothing
                        break;
                    }
                    spaceChoice = god.getOptionalMove(player);
                    //give the space choices to the view
                    virtualView.sendToClient(new SelectMoveMessage(spaceChoice,true));
                    while (receiver==null)
                    {
                        Thread.yield();
                    }
                    // get the space that the user has selected
                    if (receiver instanceof Integer){
                        //do nothing if you receive the NO_ACTION Constant
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
                    if (god.getOptionalBuild(player).isEmpty()) {
                        //the view prints a message
                        break;
                    }
                    spaceChoice = god.getOptionalBuild(player);
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
                    else if (spaceChoice.contains(receiver)) {
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
        // set all the player/worker parameters correctly, restore the steps available
        player.setHasMovedUp(false);
        player.setHasBuilt(false);
        if (player.isPlayerBlocked())
            player.setPlayerBlocked(false);

        blockedWorkers = new ArrayList<>();
        this.steps = new ArrayList<>(god.getAvailableSteps());
        return false;
    }

    /**
     * Worker selection process- from the controller to the view
     */
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

        //Save the choice in player.selectedWorker
        player.selectWorker(selectedWorker);
        receiver=null;
    }

    /**
     ** Update the class whenever the Virtual View notifies a new message
     */
    @Override
    public void update(Message message) {
        receiver= ((ToServerMessage)message).castChoice();
    }

    /**
    ** Set the Virtual View as an Observable for this class
     */
    @Override
    public void setObservable(Observable observable) {
        this.virtualView.register(this);
    }
}
