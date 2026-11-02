package pet.project.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pet.project.enums.PlayerPosition;
import pet.project.exceptions.SetIsAlreadyCompletedException;

import static org.junit.jupiter.api.Assertions.*;

public class TennisSetTest {
    private TennisSet tennisSet;

    @BeforeEach
    public void init(){
        tennisSet = new TennisSet();
    }

    @Test
    public void booleanIsFinishedIfSetJustCreated(){
        assertFalse(tennisSet.isFinished());
    }

    @Test
    public void onePointDoesNotChangeGamesCount(){
        tennisSet.addPointTo(PlayerPosition.FIRST);
        assertEquals(0, tennisSet.getCounterFirstPlayer());
        assertEquals(0, tennisSet.getCounterSecondPlayer());
    }

    @Test
    public void firstPlayerWinGameCounterIncreased(){
        for(int i = 0; i < 4; i++){
            tennisSet.addPointTo(PlayerPosition.FIRST);
        }
        assertEquals(1, tennisSet.getCounterFirstPlayer());
    }

    @Test
    public void secondPlayerWinGameCounterIncreased(){
        for(int i = 0; i < 4; i++){
            tennisSet.addPointTo(PlayerPosition.SECOND);
        }
        assertEquals(1, tennisSet.getCounterSecondPlayer());
    }

    @Test
    public void winByScore6To0(){
        for(int i = 0; i < 4*6; i++){
            tennisSet.addPointTo(PlayerPosition.FIRST);
        }
        assertTrue(tennisSet.isFinished());
    }

    @Test
    public void winByDifferenceOf2Point(){
        for(int i = 0; i < 4*5; i++){
            tennisSet.addPointTo(PlayerPosition.FIRST);
        }
        for(int i = 0; i < 4*4; i++){
            tennisSet.addPointTo(PlayerPosition.SECOND);
        }

        for(int i = 0; i < 4; i++){
            tennisSet.addPointTo(PlayerPosition.FIRST);
        }
        assertTrue(tennisSet.isFinished());
    }
    @Test
    public void noWinByDifferenceOf1Point(){
        for(int i = 0; i < 4*5; i++){
            tennisSet.addPointTo(PlayerPosition.FIRST);
        }
        for(int i = 0; i < 4*5; i++){
            tennisSet.addPointTo(PlayerPosition.SECOND);
        }

        for(int i = 0; i < 4; i++){
            tennisSet.addPointTo(PlayerPosition.FIRST);
        }
        assertFalse(tennisSet.isFinished());
    }
    @Test
    public void gameContinueWhenSetScore5To5(){
        for(int i = 0; i < 4*5; i++){
            tennisSet.addPointTo(PlayerPosition.FIRST);
        }
        for(int i = 0; i < 4*5; i++){
            tennisSet.addPointTo(PlayerPosition.SECOND);
        }
        assertFalse(tennisSet.isFinished());
    }
    @Test
    public void tieBreakWhenScore6To6(){
        for(int i = 0; i < 4*5; i++){
            tennisSet.addPointTo(PlayerPosition.FIRST);
        }
        for(int i = 0; i < 4*5; i++){
            tennisSet.addPointTo(PlayerPosition.SECOND);
        }
        for(int i = 0; i < 4; i++){
            tennisSet.addPointTo(PlayerPosition.FIRST);
        }
        for(int i = 0; i < 4; i++){
            tennisSet.addPointTo(PlayerPosition.SECOND);
        }

        assertTrue(tennisSet.isTieBreak());
    }
    @Test
    public void scoreDoesNotIncreaseWhenTieBreak(){
        for(int i = 0; i < 4*5; i++){
            tennisSet.addPointTo(PlayerPosition.FIRST);
        }
        for(int i = 0; i < 4*5; i++){
            tennisSet.addPointTo(PlayerPosition.SECOND);
        }
        for(int i = 0; i < 4; i++){
            tennisSet.addPointTo(PlayerPosition.FIRST);
        }
        for(int i = 0; i < 4; i++){
            tennisSet.addPointTo(PlayerPosition.SECOND);
        }
        tennisSet.addPointTo(PlayerPosition.FIRST);
        assertEquals(6, tennisSet.getCounterFirstPlayer());
        assertEquals(6, tennisSet.getCounterSecondPlayer());

    }
    @Test
    public void setCompletedAfterTieBreak(){
        for(int i = 0; i < 4*5; i++){
            tennisSet.addPointTo(PlayerPosition.FIRST);
        }
        for(int i = 0; i < 4*5; i++){
            tennisSet.addPointTo(PlayerPosition.SECOND);
        }
        for(int i = 0; i < 4; i++){
            tennisSet.addPointTo(PlayerPosition.FIRST);
        }
        for(int i = 0; i < 4; i++){
            tennisSet.addPointTo(PlayerPosition.SECOND);
        }

        for(int i = 0; i < 7; i++){
            tennisSet.addPointTo(PlayerPosition.FIRST);
        }
        assertTrue(tennisSet.isFinished());
    }
    @Test
    public void givePointInFinishedSet(){
        for(int i = 0; i < 4*6; i++){
            tennisSet.addPointTo(PlayerPosition.FIRST);
        }
        assertThrows(SetIsAlreadyCompletedException.class, () -> tennisSet.addPointTo(PlayerPosition.FIRST));
    }

}
