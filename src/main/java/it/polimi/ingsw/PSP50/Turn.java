package it.polimi.ingsw.PSP50;

public class Turn {
    private Player player;
    private Phase phase;

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public void advance() {
        switch(this.phase) {
            case MOVE:

                break;
            case BUILD:

                break;
            case POWER:

                break;
        }
    }
}