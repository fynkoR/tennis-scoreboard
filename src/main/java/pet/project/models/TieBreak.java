package pet.project.models;

import pet.project.enums.PlayerPosition;
import pet.project.exceptions.GameIsAlreadyWinException;
import pet.project.exceptions.TieBreakIsAlreadyWinException;

public class TieBreak implements Scorable{

    private static final int INITIAL_VALUE = 0;
    private static final int WIN_VALUE = 7;
    private static final int DIFFERENCE_VALUE = 2;

    private int firstPlayerScore;
    private int secondPlayerScore;
    private boolean isFinished;

    public TieBreak(){
        this.firstPlayerScore = INITIAL_VALUE;
        this.secondPlayerScore = INITIAL_VALUE;
    }

    @Override
    public void addPointTo(PlayerPosition playerPosition) {
        if(isFinished){
            throw new TieBreakIsAlreadyWinException();
        }

        int winnerCounter = (playerPosition == PlayerPosition.FIRST) ? firstPlayerScore : secondPlayerScore;
        int opponentCounter = (playerPosition == PlayerPosition.FIRST) ? secondPlayerScore : firstPlayerScore;

        winnerCounter++;

        if(finishTieBreakIfWinCounterAt7AndOpponentBelow7(winnerCounter, opponentCounter)){
            isFinished = true;
        }

        firstPlayerScore = (playerPosition == PlayerPosition.FIRST) ? winnerCounter : opponentCounter;
        secondPlayerScore = (playerPosition == PlayerPosition.FIRST) ? opponentCounter : winnerCounter;
    }

    private boolean finishTieBreakIfWinCounterAt7AndOpponentBelow7(int winnerCounter, int opponentCounter){
        return winnerCounter >= WIN_VALUE
                && (winnerCounter - opponentCounter) >= DIFFERENCE_VALUE;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public GameScore getGameScore(){
        return new GameScore(String.valueOf(firstPlayerScore), String.valueOf(secondPlayerScore));
    }
}
