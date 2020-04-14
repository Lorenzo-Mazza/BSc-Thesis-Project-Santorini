package it.polimi.ingsw.PSP50.Model;

import it.polimi.ingsw.PSP50.Model.Phase;
import it.polimi.ingsw.PSP50.Model.Player;
/*
            I don't think we need this anymore.


public class Turn {
    private Player player;
    private Phase phase;

    public Player getPlayer() {
        return player;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }


    public void advance() {
        switch(this.phase) {
            case START:
                setPhase(Phase.MOVE);
                break;
            case MOVE:
                setPhase(Phase.BUILD);
                break;
            case BUILD:
                setPhase(Phase.END);
                break;
            case END:
                setPhase(Phase.START);
                break;
        }
    }
}

 */