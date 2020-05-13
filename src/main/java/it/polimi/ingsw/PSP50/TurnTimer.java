package it.polimi.ingsw.PSP50;


import java.io.Serializable;

public class TurnTimer extends Thread implements Serializable {
    private int seconds;
    private boolean active=true;

    public TurnTimer(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public void run() {
        try {
            int slept = 0;
            while ((slept < seconds) && (active)) {
                sleep(1000);
                slept++;
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

    }

    public void endTimer(){
        active=false;
    }
}
