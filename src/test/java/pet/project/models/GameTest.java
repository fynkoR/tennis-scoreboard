package pet.project.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pet.project.exceptions.GameIsAlreadyWinException;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;

    @BeforeEach
    void init(){
        game = new Game();
    }

    @Test
    public void booleanIsFinishedIfGameJustCreated(){
        assertFalse(game.isFinished());
    }

    private void addFewPointToPlayer(int count, PlayerPosition playerPosition){
        for(int i = 0; i < count; i++){
            game.addPointTo(playerPosition);
        }
    }

    @Test
    public void shouldBeAfterLoveComeFifteen(){
        addFewPointToPlayer(1, PlayerPosition.FIRST);
        assertEquals(Point.FIFTEEN, game.getFirstPlayerPoint());
    }

    @Test
    public void shouldBeAfterFifteenComeThirty(){
        addFewPointToPlayer(2, PlayerPosition.FIRST);
        assertEquals(Point.THIRTY, game.getFirstPlayerPoint());
    }
    @Test
    public void shouldBeAfterThirtyComeForty(){
        addFewPointToPlayer(3, PlayerPosition.FIRST);
        assertEquals(Point.FORTY, game.getFirstPlayerPoint());
    }

    @Test
    public void shouldBeAfterFortyComeAdvantage(){
        addFewPointToPlayer(3, PlayerPosition.FIRST);
        addFewPointToPlayer(3, PlayerPosition.SECOND);

        addFewPointToPlayer(1, PlayerPosition.FIRST);
        assertEquals(Point.ADVANTAGE, game.getFirstPlayerPoint());
    }

    @Test
    public void addThreePointsEachAndOnePointFirstPlayerGameNotIsFinished(){
        addFewPointToPlayer(3, PlayerPosition.FIRST);
        addFewPointToPlayer(3, PlayerPosition.SECOND);

        addFewPointToPlayer(1, PlayerPosition.FIRST);
        assertFalse(game.isFinished());
    }

    @Test
    public void shouldReturnToDeuceWhenOpponentScoresDuringAdvantage(){
        addFewPointToPlayer(3, PlayerPosition.FIRST);
        addFewPointToPlayer(3, PlayerPosition.SECOND);

        addFewPointToPlayer(1, PlayerPosition.FIRST);
        addFewPointToPlayer(1, PlayerPosition.SECOND);

        assertEquals(Point.FORTY, game.getFirstPlayerPoint());
        assertEquals(Point.FORTY, game.getSecondPlayerPoint());
    }

    @Test
    public void firstPlayerHasAdvantagePlusPointToHimGameIsFinished(){
        addFewPointToPlayer(3, PlayerPosition.FIRST);
        addFewPointToPlayer(3, PlayerPosition.SECOND);

        addFewPointToPlayer(2, PlayerPosition.FIRST);
        assertTrue(game.isFinished());
    }

    @Test
    public void score40To30WinWhenDifferenceTwoPoints(){
        addFewPointToPlayer(3, PlayerPosition.FIRST);
        addFewPointToPlayer(2, PlayerPosition.SECOND);

        addFewPointToPlayer(1, PlayerPosition.FIRST);
        assertTrue(game.isFinished());
    }

    @Test
    public void ifPlayerWinPointAt40To0ThenGameIsFinished(){
        addFewPointToPlayer(4, PlayerPosition.FIRST);
        assertTrue(game.isFinished());
    }

    @Test
    public void shouldWinWhenAdvantageIsLostAndRegainedAfterDeuce(){
        addFewPointToPlayer(3, PlayerPosition.FIRST);
        addFewPointToPlayer(3, PlayerPosition.SECOND);

        addFewPointToPlayer(1, PlayerPosition.FIRST);
        addFewPointToPlayer(1, PlayerPosition.SECOND);

        addFewPointToPlayer(2, PlayerPosition.FIRST);
        assertTrue(game.isFinished());
    }

    @Test
    public void givePointInFinishedGame(){
        addFewPointToPlayer(4, PlayerPosition.SECOND);
        assertThrows(GameIsAlreadyWinException.class, () -> {
            game.addPointTo(PlayerPosition.FIRST);
        });
    }

    @Test
    public void givePointSecondPlayerAtScore0To40WinToGame(){
        addFewPointToPlayer(4, PlayerPosition.SECOND);
        assertTrue(game.isFinished());
    }

    @Test
    public void score40To40PlusPointSecondPlayerAdvantageInSecondPlayer(){
        addFewPointToPlayer(3, PlayerPosition.FIRST);
        addFewPointToPlayer(3, PlayerPosition.SECOND);

        addFewPointToPlayer(1, PlayerPosition.SECOND);
        assertEquals(Point.ADVANTAGE, game.getSecondPlayerPoint());
    }

    @Test
    public void score40To40PlusTwoPointsSecondPlayerAndWinGame(){
        addFewPointToPlayer(3, PlayerPosition.FIRST);
        addFewPointToPlayer(3, PlayerPosition.SECOND);

        addFewPointToPlayer(2, PlayerPosition.SECOND);
        assertTrue(game.isFinished());
    }
}
