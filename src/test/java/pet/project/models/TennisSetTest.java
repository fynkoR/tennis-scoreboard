package pet.project.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pet.project.exceptions.SetIsAlreadyCompletedException;

import static org.junit.jupiter.api.Assertions.*;

public class TennisSetTest {
    private TennisSet tennisSet;

    @BeforeEach
    public void init(){
        tennisSet = new TennisSet();
    }

    private void winFewGamesForPlayer(int count, PlayerPosition playerPosition){
        for(int i = 0; i < 4 * count; i++){
            tennisSet.addPointTo(playerPosition);
        }
    }

    private void makeTieBreak(){
        winFewGamesForPlayer(5, PlayerPosition.FIRST);
        winFewGamesForPlayer(5, PlayerPosition.SECOND);

        winFewGamesForPlayer(1, PlayerPosition.FIRST);
        winFewGamesForPlayer(1, PlayerPosition.SECOND);
    }

    private void winTieBreakForPlayer(PlayerPosition playerPosition){
        for(int i = 0; i < 7; i++){
            tennisSet.addPointTo(playerPosition);
        }
    }

    @Test
    public void booleanIsFinishedIfSetJustCreated(){
        assertFalse(tennisSet.isFinished());
    }

    @Test
    public void onePointDoesNotChangeGamesCount(){
        tennisSet.addPointTo(PlayerPosition.FIRST);
        assertEquals(0, tennisSet.getScorePlayer(PlayerPosition.FIRST));
        assertEquals(0, tennisSet.getScorePlayer(PlayerPosition.SECOND));
    }

    @Test
    public void firstPlayerWinGameCounterIncreased(){
        winFewGamesForPlayer(1, PlayerPosition.FIRST);
        assertEquals(1, tennisSet.getScorePlayer(PlayerPosition.FIRST));
    }

    @Test
    public void secondPlayerWinGameCounterIncreased(){
        winFewGamesForPlayer(1, PlayerPosition.SECOND);
        assertEquals(1, tennisSet.getScorePlayer(PlayerPosition.SECOND));
    }

    @Test
    public void winByScore6To0(){
        winFewGamesForPlayer(6, PlayerPosition.FIRST);
        assertTrue(tennisSet.isFinished());
    }

    @Test
    public void winByDifferenceOf2Point(){
        winFewGamesForPlayer(5, PlayerPosition.FIRST);
        winFewGamesForPlayer(4, PlayerPosition.SECOND);

        winFewGamesForPlayer(1, PlayerPosition.FIRST);
        assertTrue(tennisSet.isFinished());
    }
    @Test
    public void noWinByDifferenceOf1Point(){
        winFewGamesForPlayer(5, PlayerPosition.FIRST);
        winFewGamesForPlayer(5, PlayerPosition.SECOND);

        winFewGamesForPlayer(1, PlayerPosition.FIRST);
        assertFalse(tennisSet.isFinished());
    }
    @Test
    public void gameContinueWhenSetScore5To5(){
        winFewGamesForPlayer(5, PlayerPosition.FIRST);
        winFewGamesForPlayer(5, PlayerPosition.SECOND);
        assertFalse(tennisSet.isFinished());
    }
    @Test
    public void tieBreakWhenScore6To6(){
        makeTieBreak();

        assertTrue(tennisSet.isTieBreak());
    }

    @Test
    public void scoreDoesNotIncreaseWhenTieBreak(){
        makeTieBreak();

        tennisSet.addPointTo(PlayerPosition.FIRST);

        assertEquals(6, tennisSet.getScorePlayer(PlayerPosition.FIRST));
        assertEquals(6, tennisSet.getScorePlayer(PlayerPosition.SECOND));

    }
    @Test
    public void setCompletedAfterTieBreak(){
        makeTieBreak();

        winTieBreakForPlayer(PlayerPosition.FIRST);

        assertTrue(tennisSet.isFinished());
    }

    @Test
    public void scorePlayers7To6AfterTieBreak(){
        makeTieBreak();

        winTieBreakForPlayer(PlayerPosition.FIRST);

        assertEquals(7, tennisSet.getScorePlayer(PlayerPosition.FIRST));
        assertEquals(6, tennisSet.getScorePlayer(PlayerPosition.SECOND));
    }

    @Test
    public void givePointInFinishedSet(){
        winFewGamesForPlayer(6, PlayerPosition.FIRST);
        assertThrows(SetIsAlreadyCompletedException.class, () -> tennisSet.addPointTo(PlayerPosition.FIRST));
    }

    @Test
    public void afterTieBreakLastGameIsTieBreak(){
        makeTieBreak();

        winTieBreakForPlayer(PlayerPosition.FIRST);

        assertInstanceOf(TieBreak.class, tennisSet.getLastGameInList());
    }

    @Test
    public void addPointAtAScoreOf6To4(){
        winFewGamesForPlayer(5, PlayerPosition.FIRST);
        winFewGamesForPlayer(4, PlayerPosition.SECOND);

        winFewGamesForPlayer(1, PlayerPosition.FIRST);
        assertThrows(SetIsAlreadyCompletedException.class, () -> tennisSet.addPointTo(PlayerPosition.FIRST));
    }
}
