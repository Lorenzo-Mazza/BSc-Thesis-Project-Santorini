package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * God is an abstract class that will be inherited by every God card
 */
public abstract class God {

    /**
     * A list of the different steps which compose the turn of play. The turn is different for every God so
     * the list will be empty by default, and changed in every constructor.
     */
    protected final ArrayList<Phase> availableSteps = new ArrayList<>();

    /**
     * The name of the God
     */
    private final GodsNames name;

    /**
     * *Constructor*
     * @param name The name of the God
     */
    protected God(GodsNames name) {this.name= name;}

    /**
     * Gives a list of the different steps which compose the turn of play.
     * Steps can be: Move, Build, Optional Move, Optional Build
     * @return an ArrayList of Phase objects, that are the available steps
     */
    public ArrayList<Phase> getAvailableSteps(){
        return new ArrayList<>(availableSteps); //returns a copy of the list;
    }

    /**
     *
     * @return the name of the God
     */
    public GodsNames getName() {
        return name;
    }


    /**
     * Gets the available spaces where the player can perform a "Move" action through this God card.
     * Basic implementation is provided, Gods that have a different logic will override this method.
     * @param player The owner of the God card
     * @return an ArrayList of the allowed spaces where his worker can move
     */
    public ArrayList<Space> getAvailableMove(Player player) {
        Worker thisWorker = player.getSelectedWorker();
        return (new ArrayList<>(thisWorker.getMovable()));
    }


    /**
     * Gets the available spaces where the player can perform a "Build" action through this God card.
     * Basic implementation is provided, Gods that have a different logic will override this method.
     * @param player The owner of the God card
     * @return an ArrayList of the allowed spaces where his worker can build
     */
    public ArrayList<Space> getAvailableBuild(Player player) {
        Worker thisWorker = player.getSelectedWorker();
        return (new ArrayList<>(thisWorker.getBuildable()));
    }


    /**
     * Gets the available spaces where the player can perform an "Optional Move" action through this God card.
     * Basic implementation returns an empty list because not every God card has an Optional Move action, so
     * it makes no sense to declare it abstract and implement it in every God.
     * Gods that have a different logic will override this method.
     * @param player The owner of the God card
     * @return an ArrayList of the allowed spaces where his worker can move
     */
    public ArrayList<Space> getOptionalMove(Player player) { return new ArrayList<>(); } //default: empty list.


    /**
     * Gets the available spaces where the player can perform an "Optional Build" action through this God card.
     * Basic implementation returns an empty list because not every God card has an Optional Build action, so
     * it makes no sense to declare it abstract and implement it in every God.
     * Gods that have a different logic will override this method.
     * @param player The owner of the God card
     * @return an ArrayList of the allowed spaces where his worker can build
     */
    public ArrayList<Space> getOptionalBuild(Player player) { return new ArrayList<>(); }  //default: empty list.


    /**
     * Gets the available blocks that the player can choose during a "Build/Optional Build" action.
     * Default implementation returns a one-element list.
     * Gods that have a different logic will override this method.
     * @param player The owner of the God card
     * @return an ArrayList of the allowed blocks that can be chosen to build
     */
    public ArrayList<Block> getAvailableBlock(Player player) {
        Space space = player.getThisBuild();
        ArrayList<Block> list = new ArrayList<>();
        switch (space.getHeight().getValue()) {
            case 0: list.add(Block.LEVEL1);
                    return list;
            case 1: list.add(Block.LEVEL2);
                    return list;
            case 2: list.add(Block.LEVEL3);
                    return list;
            case 3: list.add(Block.DOME);
                    return list;
            default: return null;
        }
    }

    /**
     * Moves the selected worker of the player and makes sure every parameter is set correctly.
     * @param player The owner of the God card
     * @param space a Space variable that indicates where to move
     * @return a boolean variable to report the outcome of the action
     */
    public boolean move(Player player, Space space)
    {   Space oldSpace=  player.getSelectedWorker().getPosition();
        oldSpace.setWorker(null); //free the old space
        player.setLastMove(oldSpace); //set last position
        player.getSelectedWorker().move(space);

        // if the outcome is correct
        if (player.getSelectedWorker().getPosition() == space)
        {
            space.setWorker(player.getSelectedWorker()); // occupy the new place
            return true;
        }

        // if the outcome is wrong
        else return false;
    }


    /**
     * Build the correct block with the selected worker of the player and makes sure every parameter is set correctly.
     * @param player The owner of the God card
     * @param space a Space variable that indicates where to build
     * @param piece The selected block to build
     * @return a boolean variable to report the outcome of the action
     */
    public boolean build(Player player, Space space, Block piece)
    {
        player.getSelectedWorker().build(space);
        // if the outcome is correct
        if (space.getHeight() == piece)
        {   player.setHasBuilt(true);
            player.setLastBuild(space);
            return true;
        }
        // if the outcome is wrong
        else
            return false;
    }

    public boolean getWinCondition(Player player){
        Block lastHeight= player.getLastMove().getHeight();
        Block thisHeight= player.getSelectedWorker().getPosition().getHeight();
        if (lastHeight==Block.LEVEL2 && thisHeight==Block.LEVEL3)
        {
            return true;
        }
        else return false;
    }

    //call from worker  public void block();

}