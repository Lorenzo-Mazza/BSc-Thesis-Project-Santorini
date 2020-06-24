package it.polimi.ingsw.PSP50.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

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
        assertTrue(game.getAllPlayers().isEmpty());
        ArrayList<Player> players= new ArrayList<>();
        players.add(new Player("p1"));
        players.add(new Player("p2"));
        players.add(new Player("p3"));
        game.setPlayers(players);
        assertEquals(players,game.getAllPlayers());
        assertFalse(game.getAllPlayers().isEmpty());
    }

    @Test
    public void getSetPlayer() {
        String player1Name= "player1";
        String player2Name= "player2";
        String player3Name= "player3";
        assertTrue(game.getAllPlayers().isEmpty());
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

    @Test
    public void testWinner() {
        assertNull(game.getWinner());
        Player winner = new Player("winner");
        game.setWinner(winner);
        assertEquals(winner,game.getWinner());
    }

    @Test
    public void testOpponents() {
        Player player1 = new Player("p1");
        Player player2 = new Player("p2");
        Player player3 = new Player("p3");
        game.setPlayer(player1);
        game.setPlayer(player2);
        game.setPlayer(player3);
        game.setOpponents();
        assertNotNull(player1.getOpponents());
        assertNotNull(player2.getOpponents());
        assertNotNull(player3.getOpponents());
        }
}