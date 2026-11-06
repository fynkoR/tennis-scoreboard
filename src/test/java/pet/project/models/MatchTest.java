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

    private void winTennisSetForPlayer(){

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
        for(int i = 0; i < 4*6; i++){
            match.addPointTo(PlayerPosition.FIRST);
        }
        assertEquals(1, match.getScorePlayer(PlayerPosition.FIRST));
    }

    @Test
    public void score2To0MatchIsCompleted(){
        for(int i = 0; i < 4*6*2; i++){
            match.addPointTo(PlayerPosition.FIRST);
        }
        assertTrue(match.isFinished());
    }
    @Test
    public void score2To1MatchIsCompleted(){
        for(int i = 0; i < 4*6; i++){
            match.addPointTo(PlayerPosition.FIRST);
        }
        for(int i = 0; i < 4*6; i++){
            match.addPointTo(PlayerPosition.SECOND);
        }
        for(int i = 0; i < 4*6; i++){
            match.addPointTo(PlayerPosition.FIRST);
        }
        assertTrue(match.isFinished());
    }
    @Test
    public void notShouldMatchIsCompletedWhenScore1To1(){
        for(int i = 0; i < 4*6; i++){
            match.addPointTo(PlayerPosition.FIRST);
        }
        for(int i = 0; i < 4*6; i++){
            match.addPointTo(PlayerPosition.SECOND);
        }
        assertFalse(match.isFinished());
    }
    @Test
    public void notShouldMatchIsCompletedWhenScore1To0(){
        for(int i = 0; i < 4*6; i++){
            match.addPointTo(PlayerPosition.FIRST);
        }
        assertFalse(match.isFinished());
    }
    @Test
    public void tryAddPointWhenMatchIsCompleted(){
        for(int i = 0; i < 4*6*2; i++){
            match.addPointTo(PlayerPosition.FIRST);
        }
        assertThrows(MatchIsAlreadyCompletedException.class, () -> match.addPointTo(PlayerPosition.FIRST));
    }
}
