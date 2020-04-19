package it.polimi.ingsw.PSP50.Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game = new Game();

    @Test
    void getSetDeck() {
        assertNull(game.getDeck());
        Deck deck= new Deck();
        game.setDeck(deck);
        assertEquals(deck,game.getDeck());
    }

    @Test
    void getSetType() {
        assertNull(game.getType());
        game.setType(GameType.TWOPLAYERS);
        assertEquals(game.getType(),GameType.TWOPLAYERS);

        game.setType(GameType.THREEPLAYER);
        assertEquals(game.getType(),GameType.THREEPLAYER);

    }

    @Test
    void getSetBoard() {
        assertNull(game.getBoard());
        Board board = new Board();
        game.setBoard(board);
        assertEquals(board, game.getBoard());
    }

    @Test
    void getSetTurn() {
        assertNull(game.getTurn());
        Turn turn= new Turn();
        game.setTurn(turn);
        assertEquals(turn,game.getTurn());
    }

    @Test
    void getSetPlayers() {
        assertNull(game.getAllPlayers());
        ArrayList<Player> players= new ArrayList<>();
        game.setPlayers(players);
        assertEquals(players,game.getAllPlayers());
    }

    @Test
    void getSetPlayer() {
        assertNull(game.getAllPlayers());
        ArrayList<Player> players= new ArrayList<>();
        game.setPlayers(players);
        Player player1= new Player();
        Player player2= new Player();
        Player player3= new Player();
        game.setPlayer(player1,0);
        game.setPlayer(player2,1);
        game.setPlayer(player3,2);

        assertEquals(player1,game.getPlayer(0));
        assertEquals(player2,game.getPlayer(1));
        assertEquals(player3,game.getPlayer(2));
    }
}