package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;
import java.util.List;

public abstract class God {
    protected final ArrayList<Phase> availableSteps= new ArrayList<>();
    private final GodsNames name;

    protected God(GodsNames name) {this.name= name;}

    public ArrayList<Phase> getAvailableSteps(){
        return new ArrayList<>(availableSteps); //returns a copy of the list;
    }

    public  GodsNames getName() {
        return name;
    }

    public ArrayList<Space> getAvailableMove(Player player) {
        Worker thisWorker = player.getSelectedWorker();
        return (new ArrayList<>(thisWorker.getMovable()));
    }

    public ArrayList<Space> getAvailableBuild(Player player) {
        Worker thisWorker = player.getSelectedWorker();
        return (new ArrayList<>(thisWorker.getBuildable()));
    }

    public ArrayList<Space> getOptionalMove(Player player) {return new ArrayList<>();} //default: empty list.

    public ArrayList<Space> getOptionalBuild(Player player) {return new ArrayList<>();}  //default: empty list.

    public  ArrayList<Block> getAvailableBlock(Player player) {
        Space space= player.getThisBuild();
        ArrayList<Block> list= new ArrayList<>();
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

    };

    public boolean Move(Player player, Space space)
    {
        player.getSelectedWorker().move(space);
        if (player.getSelectedWorker().getPosition()== space)
            return true;
        else return false;
    }

    public boolean Build(Player player, Space space, Block piece)
    {
        player.getSelectedWorker().build(space);
        if (space.getHeight()==piece)
            return true;
        else return false;
    }





}