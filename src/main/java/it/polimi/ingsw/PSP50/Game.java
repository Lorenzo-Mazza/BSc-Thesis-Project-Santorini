package it.polimi.ingsw.PSP50;

public class Game {
    private Player[] players;
    private Board board;
    private Turn turn;
    private GameType type;

    public void setType(GameType type) {
        this.type = type;
    }

    public GameType getType() {
        return type;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Player getPlayer(int player) {
        return players[player];
    }
}
