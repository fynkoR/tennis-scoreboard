package pet.project.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pet.project.exceptions.TieBreakIsAlreadyWinException;

import static org.junit.jupiter.api.Assertions.*;

public class TieBreakTest {

    private TieBreak tieBreak;

    @BeforeEach
    public void init() {
        tieBreak = new TieBreak();
    }

    private void addFewPointToPlayer(int count, PlayerPosition playerPosition){
        for(int i = 0; i < count; i++){
            tieBreak.addPointTo(playerPosition);
        }
    }

    private void makeTieBreakAt6To6(){
        addFewPointToPlayer(6, PlayerPosition.FIRST);
        addFewPointToPlayer(6, PlayerPosition.SECOND);
    }

    @Test
    public void booleanIsFinishedIfTieBreakJustCreated() {
        assertFalse(tieBreak.isFinished());
    }

    @Test
    public void winWhenPlayerScores7Points() {
        addFewPointToPlayer(7, PlayerPosition.FIRST);
        assertTrue(tieBreak.isFinished());
    }

    @Test
    public void winWhenA2PointDifference() {
        addFewPointToPlayer(6, PlayerPosition.FIRST);
        addFewPointToPlayer(5, PlayerPosition.SECOND);
        addFewPointToPlayer(1, PlayerPosition.FIRST);
        assertTrue(tieBreak.isFinished());
    }

    @Test
    public void noWinWhenScore7To6() {
        makeTieBreakAt6To6();
        addFewPointToPlayer(1, PlayerPosition.FIRST);
        assertFalse(tieBreak.isFinished());
    }

    @Test
    public void noWinIfPlayersReached7Points() {
        makeTieBreakAt6To6();
        addFewPointToPlayer(1, PlayerPosition.FIRST);
        addFewPointToPlayer(1, PlayerPosition.SECOND);
        assertFalse(tieBreak.isFinished());
    }

    @Test
    public void tryScoreAPointInFinishedTieBreak() {
        addFewPointToPlayer(6, PlayerPosition.FIRST);
        addFewPointToPlayer(5, PlayerPosition.SECOND);
        addFewPointToPlayer(1, PlayerPosition.FIRST);

        assertThrows(TieBreakIsAlreadyWinException.class, () -> addFewPointToPlayer(1, PlayerPosition.FIRST));
    }
    @Test
    public void secondPlayerWinAtScore7To0(){
        addFewPointToPlayer(7, PlayerPosition.SECOND);
        assertTrue(tieBreak.isFinished());
    }
    @Test
    public void secondPlayerWinAtScore8To6(){
        makeTieBreakAt6To6();
        addFewPointToPlayer(2, PlayerPosition.SECOND);
        assertTrue(tieBreak.isFinished());
    }
}
