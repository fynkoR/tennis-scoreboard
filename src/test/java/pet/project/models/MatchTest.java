package pet.project.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pet.project.exceptions.MatchIsAlreadyCompletedException;
import pet.project.exceptions.MatchIsNotCompletedException;

import static org.junit.jupiter.api.Assertions.*;

public class MatchTest {
    private Match match;

    @BeforeEach
    public void init(){
        match = new Match();
    }

    private void winTennisSetForPlayer(int count, PlayerPosition playerPosition){
        for(int i = 0; i < 4*6 * count; i++){
            match.addPointTo(playerPosition);
        }
    }

    @Test
    public void counterSetsIsZeroIfMatchJustCreated(){
        assertEquals(0, match.getSetScore().scoreFirstPlayer());
        assertEquals(0, match.getSetScore().scoreSecondPlayer());
    }
    @Test
    public void booleanIsFinishedIfMatchJustCreated(){
        assertFalse(match.isFinished());
    }
    @Test
    public void oneAddOnePointDoesNotChangeCounterSet(){
        match.addPointTo(PlayerPosition.FIRST);
        assertEquals(0, match.getSetScore().scoreFirstPlayer());
    }
    @Test
    public void setCompletedCounterIncreased(){
        winTennisSetForPlayer(1, PlayerPosition.FIRST);
        assertEquals(1, match.getScorePlayer(PlayerPosition.FIRST));
    }

    @Test
    public void score2To0MatchIsCompleted(){
        winTennisSetForPlayer(2, PlayerPosition.FIRST);
        assertTrue(match.isFinished());
    }
    @Test
    public void score2To1MatchIsCompleted(){
        winTennisSetForPlayer(1, PlayerPosition.FIRST);
        winTennisSetForPlayer(1, PlayerPosition.SECOND);
        winTennisSetForPlayer(1, PlayerPosition.FIRST);

        assertTrue(match.isFinished());
    }
    @Test
    public void notShouldMatchIsCompletedWhenScore1To1(){
        winTennisSetForPlayer(1, PlayerPosition.FIRST);
        winTennisSetForPlayer(1, PlayerPosition.SECOND);
        assertFalse(match.isFinished());
    }
    @Test
    public void notShouldMatchIsCompletedWhenScore1To0(){
        winTennisSetForPlayer(1, PlayerPosition.FIRST);
        assertFalse(match.isFinished());
    }
    @Test
    public void tryAddPointWhenMatchIsCompleted(){
        winTennisSetForPlayer(2, PlayerPosition.FIRST);
        assertThrows(MatchIsAlreadyCompletedException.class, () -> match.addPointTo(PlayerPosition.FIRST));
    }
}
