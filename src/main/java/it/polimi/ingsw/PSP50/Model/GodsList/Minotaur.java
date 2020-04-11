package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

public class Minotaur extends God {
    private final Gods name = Gods.MINOTAUR;
    protected enum direction {North,NEast,NWest,East,West,SEast,SWest,South};

    @Override
    public ArrayList<Space> power(Player player)
    {

        Worker thisWorker = player.getSelectedWorker();
        int[] coordinates = thisWorker.getPosition().getCoordinates();
        direction dir= null;
        Space target;

        //creates a list of all the spaces nearby.
        ArrayList<Space> neighbourSpaces = new ArrayList<>(thisWorker.getPosition().getNeighbors());
        //creates a list of all the reachable spaces nearby.
        ArrayList<Space> availableMoves = new ArrayList<>(thisWorker.getMovable());
        //creates a list of all the occupied but reachable spaces nearby.
        ArrayList<Space> occupiedSpaces = new ArrayList<>();
        for (int index=0;index<neighbourSpaces.size();index++)
        {
            if (neighbourSpaces.get(index).isOccupied() &&
         (neighbourSpaces.get(index).getHeight().getValue() - thisWorker.getPosition().getHeight().getValue()) <2)
                occupiedSpaces.add(neighbourSpaces.get(index));
        }


        // Find the next occupied space's direction, check if the next_next space in the same direction is free.

        for(int index = 0; index < occupiedSpaces.size(); index++)
        {   int xNext= occupiedSpaces.get(index).getXPosition();
            int yNext= occupiedSpaces.get(index).getYPosition();
            if (coordinates[0]==xNext &&
                    coordinates[1] + 1 ==yNext )
                dir= direction.North;

            else if (coordinates[0]==xNext &&
                    coordinates[1] -1  ==yNext )
                dir= direction.South;

            else if (coordinates[0] +1 ==xNext &&
                    coordinates[1]  ==yNext )
                dir= direction.East;

            else if (coordinates[0] -1 ==xNext &&
                    coordinates[1]  ==yNext )
                dir= direction.West;

            else if (coordinates[0] +1 ==xNext &&
                    coordinates[1] +1 ==yNext )
                dir= direction.NEast;

            else if (coordinates[0] +1 ==xNext &&
                    coordinates[1] -1 ==yNext )
                dir= direction.SEast;

            else if (coordinates[0] -1 ==xNext &&
                    coordinates[1] -1 ==yNext )
                dir= direction.SWest;

            else if (coordinates[0] -1 ==xNext &&
                    coordinates[1] +1 ==yNext )
                dir= direction.NWest;

            if (dir!= null)
            {
                switch (dir) {
                    case North: {
                        target = Board.getSpace(xNext, yNext + 1);

                        if (target.isOccupied()) {
                            occupiedSpaces.remove(index);
                        }
                        break;

                    }

                    case South: {
                        target = Board.getSpace(xNext, yNext - 1);

                        if (target.isOccupied()) {
                            occupiedSpaces.remove(index);
                        }
                        break;

                    }

                    case East: {
                        target = Board.getSpace(xNext + 1, yNext);

                        if (target.isOccupied()) {
                            occupiedSpaces.remove(index);
                        }
                        break;

                    }

                    case West: {
                        target = Board.getSpace(xNext - 1, yNext);

                        if (target.isOccupied()) {
                            occupiedSpaces.remove(index);
                        }
                        break;

                    }

                    case NEast: {
                        target = Board.getSpace(xNext + 1, yNext + 1);

                        if (target.isOccupied()) {
                            occupiedSpaces.remove(index);
                        }
                        break;

                    }

                    case NWest: {
                        target = Board.getSpace(xNext - 1, yNext + 1);

                        if (target.isOccupied()) {
                            occupiedSpaces.remove(index);
                        }
                        break;

                    }

                    case SWest: {
                        target = Board.getSpace(xNext - 1, yNext - 1);

                        if (target.isOccupied()) {
                            occupiedSpaces.remove(index);
                        }
                        break;

                    }

                    case SEast: {
                        target = Board.getSpace(xNext + 1, yNext - 1);

                        if (target.isOccupied()) {
                            occupiedSpaces.remove(index);
                        }
                        break;

                    }


                }
            }
        }

        //Copy the occupied spaces left into the available moves.
        availableMoves.addAll(occupiedSpaces);

        return availableMoves;
    }
}
