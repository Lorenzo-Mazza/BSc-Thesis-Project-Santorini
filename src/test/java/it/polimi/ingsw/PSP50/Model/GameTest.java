package it.polimi.ingsw.PSP50.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game = null;


    @Before
    public void setUp() {
        game= new Game();

    }
    @After
    public void tearDown(){
        game=null;
    }

    @Test
    public void getSetDeck() {
        assertNull(game.getDeck());
        Deck deck= new Deck();
        game.setDeck(deck);
        assertEquals(deck,game.getDeck());
    }

    @Test
    public void getSetType() {
        assertNull(game.getType());
        game.setType(GameType.TWOPLAYERS);
        assertEquals(game.getType(),GameType.TWOPLAYERS);

        game.setType(GameType.THREEPLAYERS);
        assertEquals(game.getType(),GameType.THREEPLAYERS);

    }

    @Test
    public void getSetBoard() {
        assertNull(game.getBoard());
        Board board = new Board();
        game.setBoard(board);
        assertEquals(board, game.getBoard());
    }


    @Test
    public void getSetPlayers() {
        assertNull(game.getAllPlayers());
        ArrayList<Player> players= new ArrayList<>();
        game.setPlayers(players);
        assertEquals(players,game.getAllPlayers());
    }

    @Test
    public void getSetPlayer() {
        String player1Name= "player1";
        String player2Name= "player2";
        String player3Name= "player3";
        assertNull(game.getAllPlayers());
        ArrayList<Player> players= new ArrayList<>();
        game.setPlayers(players);
        Player player1= new Player(player1Name);
        Player player2= new Player(player2Name);
        Player player3= new Player(player3Name);
        game.setPlayer(player1);
        game.setPlayer(player2);
        game.setPlayer(player3);

        assertEquals(player1,game.getPlayer(0));
        assertEquals(player2,game.getPlayer(1));
        assertEquals(player3,game.getPlayer(2));
    }
}