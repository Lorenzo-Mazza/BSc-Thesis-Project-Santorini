package it.polimi.ingsw.PSP50.View;

import it.polimi.ingsw.PSP50.Model.Board;
import it.polimi.ingsw.PSP50.Model.Game;
import it.polimi.ingsw.PSP50.Model.Space;
import it.polimi.ingsw.PSP50.Model.Worker;
import it.polimi.ingsw.PSP50.Observable;
import it.polimi.ingsw.PSP50.Observer;
import it.polimi.ingsw.PSP50.network.messages.ToClient.ModelMessage;

import java.io.PrintStream;
import java.util.Scanner;

/*
** ClientView is an abstract class that will be implemented by GUI and CLI
 */
public abstract class ClientView {


    public abstract void update(Object gameCopy);



}
