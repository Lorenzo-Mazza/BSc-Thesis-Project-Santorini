package it.polimi.ingsw.PSP50.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;


    @Before
    public void setUp() {
        game = new Game();

    }
    @After
    public void tearDown(){
        game = null;
    }

    @Test
    public void DeckTest() {
        assertNull(game.getDeck());
        Deck deck = new Deck();
        game.setDeck(deck);
        assertEquals(deck,game.getDeck());
    }

    @Test
    public void TypeTest() {
        assertNull(game.getType());
        game.setType(GameType.TWOPLAYERS);
        assertEquals(game.getType(),GameType.TWOPLAYERS);

        game.setType(GameType.THREEPLAYERS);
        assertEquals(game.getType(),GameType.THREEPLAYERS);
    }

    @Test
    public void BoardTest() {
        assertNull(game.getBoard());
        Board board = new Board();
        game.setBoard(board);
        assertEquals(board, game.getBoard());
    }

    @Test
    public void PlayersTest() {
        String player1Name= "player1";
        String player2Name= "player2";
        String player3Name= "player3";
        assertEquals(0, game.getAllPlayers().size());

        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);
        Player player3 = new Player(player3Name);
        game.setPlayer(player1);
        game.setPlayer(player2);
        game.setPlayer(player3);

        assertEquals(player1, game.getPlayer(0));
        assertEquals(player2, game.getPlayer(1));
        assertEquals(player3, game.getPlayer(2));
        assertEquals(3, game.getAllPlayers().size());
    }

    @Test
    public void winnerTest() {
        assertEquals(null, game.getWinner());
        Player player = new Player("winner");
        game.setWinner(player);
        assertEquals(player, game.getWinner());
    }

    @Test
    public void opponentsTest() {
        assertEquals(0, game.getAllPlayers().size());
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        players.add(new Player("Player3"));
        game.setPlayers(players);
        assertEquals(3, game.getAllPlayers().size());

        for (int index = 0; index < 3; index++) {
            Player player = game.getPlayer(index);
            assertEquals(0, player.getOpponents().size());
        }

        game.setOpponents();

        for (int index = 0; index < 3; index++) {
            Player player = game.getPlayer(index);
            assertEquals(2, player.getOpponents().size());
        }
    }
}