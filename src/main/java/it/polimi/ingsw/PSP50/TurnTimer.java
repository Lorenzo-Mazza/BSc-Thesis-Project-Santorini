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
            System.out.print("\nTimer started with "+ seconds + "left");
            int slept = 0;
            while ((slept < seconds) && (active)) {
                sleep(1000);
                slept++;
                System.out.print("\nSeconds left: " + slept);
                if (slept==seconds || !active) this.interrupt();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

    }

    public void endTimer(){
        active=false;
    }
}
