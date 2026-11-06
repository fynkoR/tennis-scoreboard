package pet.project.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pet.project.exceptions.MatchIsNotCompletedException;
import pet.project.exceptions.PlayerNotFoundByNameException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ActiveMatchTest {
    private ActiveMatch activeMatch;
    private TennisPlayer firstPlayer;
    private TennisPlayer secondPlayer;

    @BeforeEach
    public void init(){
        firstPlayer = new TennisPlayer("first");
        secondPlayer = new TennisPlayer("second");
        activeMatch = new ActiveMatch(firstPlayer, secondPlayer);
    }

    private void winMatchForPlayer(String playerName){
        for(int i = 0; i < 4*6*2; i++){
            activeMatch.addPointTo(playerName);
        }
    }

    @Test
    public void shouldBePositionFirstIfFirstPlayer(){
        assertEquals(PlayerPosition.FIRST, activeMatch.getPlayerPositionInMatch(firstPlayer.name()));
    }

    @Test
    public void shouldBePositionSecondIfSecondPlayer(){
        assertEquals(PlayerPosition.SECOND, activeMatch.getPlayerPositionInMatch(secondPlayer.name()));
    }

    @Test
    public void shouldBeExceptionIfPlayerNoFirstOrSecond(){
        assertThrows(PlayerNotFoundByNameException.class, () -> activeMatch.getPlayerPositionInMatch("third"));
    }

    @Test
    public void getFirstPlayerWinnerIfHimWinThisMatch(){
        winMatchForPlayer(firstPlayer.name());
        assertEquals(firstPlayer, activeMatch.getWinner());
    }

    @Test
    public void getSecondPlayerWinnerIfHimWinThisMatch(){
        winMatchForPlayer(secondPlayer.name());
        assertEquals(secondPlayer, activeMatch.getWinner());
    }

    @Test
    public void getPlayerWinnerIfHimNoWinThisMatch(){
        assertThrows(MatchIsNotCompletedException.class, () -> activeMatch.getWinner());
    }
}
