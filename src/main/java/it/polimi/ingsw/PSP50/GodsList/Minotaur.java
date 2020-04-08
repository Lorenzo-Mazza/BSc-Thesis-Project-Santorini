package it.polimi.ingsw.PSP50.GodsList;

import it.polimi.ingsw.PSP50.*;

import java.util.ArrayList;

public class Minotaur extends God {
    private final Gods name = Gods.MINOTAUR;
    protected enum direction {North,NEast,NWest,East,West,SEast,SWest,South};

    @Override
    public ArrayList<Space> power(Turn turn)
    {

        Player thisPlayer = turn.getPlayer();
        Worker thisWorker = thisPlayer.getSelectedWorker();
        int[] coordinates = thisWorker.getPosition().getCoordinates();
        direction dir= null;
        Space target;

        //creates a list of all the spaces nearby.
        ArrayList<Space> neighbour_spaces = new ArrayList<>(thisWorker.getPosition().getNeighbors());
        //creates a list of all the reachable spaces nearby.
        ArrayList<Space> available_moves = new ArrayList<>(thisWorker.getMovable());
        //creates a list of all the occupied but reachable spaces nearby.
        ArrayList<Space> occupied_spaces = new ArrayList<>();
        for (int index=0;index<neighbour_spaces.size();index++)
        {
            if (neighbour_spaces.get(index).isOccupied() &&
         (neighbour_spaces.get(index).getHeight().getValue() - thisWorker.getPosition().getHeight().getValue()) <2)
                occupied_spaces.add(neighbour_spaces.get(index));
        }


        // Find the next occupied space direction, check if the next_next space is free.

        for(int index = 0; index < occupied_spaces.size(); index++)
        {   int x_next= occupied_spaces.get(index).getXPosition();
            int y_next= occupied_spaces.get(index).getYPosition();
            if (coordinates[0]==x_next &&
                    coordinates[1] + 1 ==y_next )
                dir= direction.North;

            else if (coordinates[0]==x_next &&
                    coordinates[1] -1  ==y_next )
                dir= direction.South;

            else if (coordinates[0] +1 ==x_next &&
                    coordinates[1]  ==y_next )
                dir= direction.East;

            else if (coordinates[0] -1 ==x_next &&
                    coordinates[1]  ==y_next )
                dir= direction.West;

            else if (coordinates[0] +1 ==x_next &&
                    coordinates[1] +1 ==y_next )
                dir= direction.NEast;

            else if (coordinates[0] +1 ==x_next &&
                    coordinates[1] -1 ==y_next )
                dir= direction.SEast;

            else if (coordinates[0] -1 ==x_next &&
                    coordinates[1] -1 ==y_next )
                dir= direction.SWest;

            else if (coordinates[0] -1 ==x_next &&
                    coordinates[1] +1 ==y_next )
                dir= direction.NWest;

            if (dir!= null)
            {
                switch (dir) {
                    case North: {
                        target = Board.getSpace(x_next, y_next + 1);

                        if (target.isOccupied()) {
                            occupied_spaces.remove(index);
                        }
                        break;

                    }

                    case South: {
                        target = Board.getSpace(x_next, y_next - 1);

                        if (target.isOccupied()) {
                            occupied_spaces.remove(index);
                        }
                        break;

                    }

                    case East: {
                        target = Board.getSpace(x_next + 1, y_next);

                        if (target.isOccupied()) {
                            occupied_spaces.remove(index);
                        }
                        break;

                    }

                    case West: {
                        target = Board.getSpace(x_next - 1, y_next);

                        if (target.isOccupied()) {
                            occupied_spaces.remove(index);
                        }
                        break;

                    }

                    case NEast: {
                        target = Board.getSpace(x_next + 1, y_next + 1);

                        if (target.isOccupied()) {
                            occupied_spaces.remove(index);
                        }
                        break;

                    }

                    case NWest: {
                        target = Board.getSpace(x_next - 1, y_next + 1);

                        if (target.isOccupied()) {
                            occupied_spaces.remove(index);
                        }
                        break;

                    }

                    case SWest: {
                        target = Board.getSpace(x_next - 1, y_next - 1);

                        if (target.isOccupied()) {
                            occupied_spaces.remove(index);
                        }
                        break;

                    }

                    case SEast: {
                        target = Board.getSpace(x_next + 1, y_next - 1);

                        if (target.isOccupied()) {
                            occupied_spaces.remove(index);
                        }
                        break;

                    }


                }
            }
        }

        //Copy the occupied spaces left into the available moves.
        available_moves.addAll(occupied_spaces);

        return available_moves;
    }
}
