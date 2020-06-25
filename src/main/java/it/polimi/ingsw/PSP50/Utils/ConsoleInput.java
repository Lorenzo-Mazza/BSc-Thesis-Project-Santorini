package it.polimi.ingsw.PSP50.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.Callable;
/**
 * This util class gives to the CLI a buffered reader that implements a callable interface
 * It makes possible to break from waiting for an input after a timeout
 */
public class ConsoleInput implements Callable<Integer> {
    public ConsoleInput(BufferedReader br) {
        this.br = br;
    }
    private BufferedReader br;
    public Integer call() throws IOException {

        int input;
        try {
            // wait until we have data to complete a readLine()
            while (!br.ready()) {
                Thread.sleep(20);
            }
            input = Integer.parseInt(br.readLine());
        } catch (InterruptedException e) {
            return null;
        }
        return input;
    }
}