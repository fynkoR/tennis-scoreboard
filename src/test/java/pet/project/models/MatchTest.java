package pet.project.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pet.project.exceptions.MatchIsAlreadyCompletedException;
import pet.project.exceptions.MatchIsNotCompletedException;

import static org.junit.jupiter.api.Assertions.*;

public class MatchTest {

    //private Match match;
    private TennisPlayer firstPlayer;
    private TennisPlayer secondPlayer;
    private ActiveMatch activeMatch;

    @BeforeEach
    public void init(){
        firstPlayer = new TennisPlayer("firstPlayer");
        secondPlayer = new TennisPlayer("secondPlayer");
        activeMatch = new ActiveMatch(firstPlayer, secondPlayer);
    }

    @Test
    public void matchCreatedWithTwoPlayers(){
        assertEquals(activeMatch.getFirstPlayer(), firstPlayer);
        assertEquals(activeMatch.getSecondPlayer(), secondPlayer);
    }
    @Test
    public void counterSetsIsZeroIfMatchJustCreated(){
        assertEquals(0, activeMatch.getMatchScore().scoreFirstPlayer());
        assertEquals(0, activeMatch.getMatchScore().scoreSecondPlayer());
    }
    @Test
    public void booleanIsFinishedIfMatchJustCreated(){
        assertFalse(activeMatch.isMatchCompleted());
    }
    @Test
    public void getWinnerUntilWin(){
        assertThrows(MatchIsNotCompletedException.class, () -> activeMatch.getWinner());
    }
    @Test
    public void oneAddOnePointDoesNotChangeCounterSet(){
        activeMatch.addPointTo(firstPlayer.name());
        assertEquals(0, activeMatch.getMatchScore().scoreFirstPlayer());
    }
    @Test
    public void setCompletedCounterIncreased(){
        for(int i = 0; i < 4*6; i++){
            activeMatch.addPointTo(firstPlayer.name());
        }
        assertEquals(1, activeMatch.getMatchScore().scoreFirstPlayer());
    }
    @Test
    public void score2To0MatchIsCompleted(){
        for(int i = 0; i < 4*6*2; i++){
            activeMatch.addPointTo(firstPlayer.name());
        }
        assertTrue(activeMatch.isMatchCompleted());
    }
    @Test
    public void score2To1MatchIsCompleted(){
        for(int i = 0; i < 4*6; i++){
            activeMatch.addPointTo(firstPlayer.name());
        }
        for(int i = 0; i < 4*6; i++){
            activeMatch.addPointTo(secondPlayer.name());
        }
        for(int i = 0; i < 4*6; i++){
            activeMatch.addPointTo(firstPlayer.name());
        }
        assertTrue(activeMatch.isMatchCompleted());
    }
    @Test
    public void notShouldMatchIsCompletedWhenScore1To1(){
        for(int i = 0; i < 4*6; i++){
            activeMatch.addPointTo(firstPlayer.name());
        }
        for(int i = 0; i < 4*6; i++){
            activeMatch.addPointTo(secondPlayer.name());
        }
        assertFalse(activeMatch.isMatchCompleted());
    }
    @Test
    public void notShouldMatchIsCompletedWhenScore1To0(){
        for(int i = 0; i < 4*6; i++){
            activeMatch.addPointTo(firstPlayer.name());
        }
        assertFalse(activeMatch.isMatchCompleted());
    }
    @Test
    public void tryAddPointWhenMatchIsCompleted(){
        for(int i = 0; i < 4*6*2; i++){
            activeMatch.addPointTo(firstPlayer.name());
        }
        assertThrows(MatchIsAlreadyCompletedException.class, () -> activeMatch.addPointTo(firstPlayer.name()));
    }
}
