package it.polimi.ingsw.PSP50;


public class TurnTimer extends Thread {
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
