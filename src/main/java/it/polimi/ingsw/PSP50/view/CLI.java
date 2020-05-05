package it.polimi.ingsw.PSP50.View;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI implements UI {

    private String name;

    private ArrayList<ArrayList<String>> boardInfo = new ArrayList<>();

    private ArrayList<ArrayList<String>> playersInfo;

    private StringBuilder buffer = new StringBuilder();


    public CLI() {
        this.writeLine("Insert Username");
        this.printBuffer();
        this.name = this.readLine();
    }

    private void printBuffer() {
        System.out.print(this.buffer.toString());
        System.out.print("\n");

        this.buffer.delete(0, this.buffer.length());
        System.out.flush();
    }


    void drawSection(String line) {
        for (int i = 0; i < (line.length()); i++) {
            this.buffer.append("_");
        }
        this.buffer.append("\n");

        this.buffer.append("|");
        this.buffer.append(line);
        this.buffer.append("|");

        this.buffer.append("\n");

        for (int i = 0; i < (line.length()); i++) {
            this.buffer.append("_");
        }
        this.buffer.append("\n");
    }

    private void writeLine(String line) {
        this.buffer.append(line);
    }

    private String readLine() {
        Scanner scanner = new Scanner(System.in);
        return (scanner.nextLine());
    }

    public String getName() {
        return this.name;
    }

    public void displayEmptyMap() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            stringBuilder.append(" __ __ __ __ __");
            stringBuilder.append("\n");
            stringBuilder.append("|  |  |  |  |  |");
            stringBuilder.append("\n");
            stringBuilder.append(" __ __ __ __ __");

        }
    }


    public void insertName() {
        drawSection("What is your name?");
        printBuffer();
        name = readLine();
    }

    public int[] chooseSpace() {
        Scanner scanner = new Scanner(System.in);
        int[] result = new int[2];
        drawSection("Choose a space");
        drawSection("choose a valid X-coordinate");
        printBuffer();
        boolean done = false;
        while (!done) {
            result[0] = scanner.nextInt();
            if (result[0] < 5) {
                done = true;
            }
        }
        drawSection("choose a valid Y-coordinate");
        printBuffer();
        done = false;
        while (!done) {
            result[1] = scanner.nextInt();
            if (result[1] < 5) {
                done = true;
            }
        }
        return result;
    }



}