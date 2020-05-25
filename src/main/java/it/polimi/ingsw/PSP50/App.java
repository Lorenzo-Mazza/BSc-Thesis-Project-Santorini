package it.polimi.ingsw.PSP50;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Hello world!
 *
 */

/*
public class App 
{
    static boolean lastWasOptional;
    public static void main( String[] args )
    {
        int counter=0;
        boolean optional=true;
        ExecutorService pool = Executors.newFixedThreadPool(1);
        while (counter<5) {
            int choice = getChoiceWithTimeout(2, 15, optional,pool);
            System.out.println(choice);
            counter++;
            optional= (!optional);
        }
    }

     static class ConsoleInputReadTask implements Callable<Integer> {
        public Integer call() throws IOException {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in));
            int input;
                try {
                    // wait until we have data to complete a readLine()
                    while (!br.ready()) {
                        Thread.sleep(200);
                    }
                    input = Integer.parseInt(br.readLine());
                } catch (InterruptedException e) {
                    return null;
                }
            return input;
        }
    }


    static int getChoiceWithTimeout(int range, int timeout, boolean optional,ExecutorService pool) {
        //Callable<Integer> callable = () -> sc.nextInt();

        ConsoleInputReadTask callable= new ConsoleInputReadTask();
        long start = System.currentTimeMillis();
        int choice = 0;
        boolean valid;
        Future<Integer> future;
        System.out.println("Enter your choice in " + timeout + " seconds :");
        future = pool.submit(callable);
        done: while (System.currentTimeMillis() - start < timeout * 1000) {
            do {
                valid = true;
                if (future.isDone()) {
                    try {
                        choice = future.get();
                        if ((choice >= 1 && choice <= range)||(choice >=0 && choice <= range && optional)) {
                            break done;
                        } else {
                            throw new IllegalArgumentException();
                        }
                    } catch (InterruptedException | ExecutionException | IllegalArgumentException e) {
                        System.out.println("Wrong choice, you have to pick an integer between 1 - " + range);
                        future = pool.submit(callable);
                        valid = false;
                    }
                }
            } while (!valid);
        }
        future.cancel(true);
        if ((choice < 0 || choice > range))
            choice=0;
        return choice;
    }
}
 */