package it.polimi.ingsw.PSP50.Model;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private Board board;
    private Turn turn;
    private GameType type;
    private Deck deck;

    public void setDeck(Deck deck) { this.deck = deck; }

    public Deck getDeck() {
        return deck;
    }

    public void setType(GameType type) {
        this.type = type;
    }

    public GameType getType() { return type; }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() { return board; }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getAllPlayers() {
        return players;
    }

    public void setPlayer(Player player, int position) { players.add(position,player);}


    public Player getPlayer(int position) {
        return players.get(position);
    }


}
