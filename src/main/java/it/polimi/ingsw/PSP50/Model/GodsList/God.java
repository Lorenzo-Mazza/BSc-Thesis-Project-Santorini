package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * *Description of class*
 */
public abstract class God {
    protected final ArrayList<Phase> availableSteps = new ArrayList<>();
    private final GodsNames name;

    /**
     * *Constructor*
     * @param name a GodsNames constant Enum
     */
    protected God(GodsNames name) {this.name= name;}

    /**
     * *Description of method*
     * @return an ArrayList of Phase
     */
    public ArrayList<Phase> getAvailableSteps(){
        return new ArrayList<>(availableSteps); //returns a copy of the list;
    }

    /**
     * *Description of method*
     * @return a GodsNames variable
     */
    public GodsNames getName() {
        return name;
    }

    /**
     * *Description of method*
     * @param player a Player variable that is playing during this turn
     * @return an ArrayList of Space which contains the allowed spaces where worker can move
     */
    public ArrayList<Space> getAvailableMove(Player player) {
        Worker thisWorker = player.getSelectedWorker();
        return (new ArrayList<>(thisWorker.getMovable()));
    }

    /**
     * *Description of method*
     * @param player a Player variable that is playing during this turn
     * @return an ArrayList of Space which contains the allowed spaces where worker can build
     */
    public ArrayList<Space> getAvailableBuild(Player player) {
        Worker thisWorker = player.getSelectedWorker();
        return (new ArrayList<>(thisWorker.getBuildable()));
    }

    /**
     * *Description of method*
     * @param player a Player variable that is playing during this turn
     * @return an ArrayList of Space which contains the allowed spaces where worker can move (by default is empty)
     */
    public ArrayList<Space> getOptionalMove(Player player) { return new ArrayList<>(); } //default: empty list.

    /**
     * *Description of method*
     * @param player a Player variable that is playing during this turn
     * @return an ArrayList of Space which contains the allowed spaces where worker can build (by default is empty)
     */
    public ArrayList<Space> getOptionalBuild(Player player) { return new ArrayList<>(); }  //default: empty list.

    /**
     * *Description of method*
     * @param player a Player variable that is playing during this turn
     * @return an ArrayList of Block which contains the allowed Blocks that can be build
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
     * *Description of method*
     * @param player a Player variable that is playing during this turn
     * @param space a Space variable that indicate where to move
     * @return a boolean variable just for confirmation
     */
    public boolean Move(Player player, Space space)
    {   Space oldSpace=  player.getSelectedWorker().getPosition();
        oldSpace.setWorker(null); //free the old space
        player.setLastMove(oldSpace); //set last position
        player.getSelectedWorker().move(space);
        if (player.getSelectedWorker().getPosition() == space)
        {
            space.setWorker(player.getSelectedWorker()); // occupy the new place
            return true;
        }
        else return false;
    }

    /**
     * *Description of method*
     * @param player a Player variable that is playing during this turn
     * @param space a Space variable that indicate where to build
     * @param piece a Block variable
     * @return a boolean variable just for confirmation
     */
    public boolean Build(Player player, Space space, Block piece)
    {
        player.getSelectedWorker().build(space);
        if (space.getHeight() == piece)
        {   player.setHasBuilt(true);
            player.setLastBuild(space);
            return true;
        }
        else
            return false;
    }

}