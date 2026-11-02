package pet.project.models;

import pet.project.enums.PlayerPosition;
import pet.project.exceptions.SetIsAlreadyCompletedException;

import java.util.ArrayList;
import java.util.List;

public class TennisSet {
    private static final int INITIAL_SCORE = 0;
    private static final int MIN_SCORE_TO_WIN = 6;
    private static final int MIN_SCORE_DIFFERENCE_TO_WIN = 2;

    private int counterFirstPlayer;
    private int counterSecondPlayer;
    private boolean isFinished;
    private final List<Scorable> games;

    public TennisSet(){
        counterFirstPlayer = INITIAL_SCORE;
        counterSecondPlayer = INITIAL_SCORE;
        games = new ArrayList<>();
    }

    public void addPointTo(PlayerPosition winnerPosition){
        if(this.isFinished){
            throw new SetIsAlreadyCompletedException();
        }

        int winnerCounter = (winnerPosition == PlayerPosition.FIRST) ? counterFirstPlayer : counterSecondPlayer;
        int opponentCounter = (winnerPosition == PlayerPosition.FIRST) ? counterSecondPlayer : counterFirstPlayer;

        if(games.isEmpty()){
            games.add(new Game());
        }

        Scorable scorable = getLastGameInList();
        scorable.addPointTo(winnerPosition);

        if(scorable.isFinished()){
            winnerCounter++;

            if(scorable instanceof TieBreak){
                this.isFinished = true;
            }
            else if(hasWinningScore(winnerCounter, opponentCounter)){
                this.isFinished = true;
            }
            else if(hasScoreForTieBreak(winnerCounter, opponentCounter)){
                games.add(new TieBreak());
            }
            else{
                games.add(new Game());
            }
        }

        counterFirstPlayer = (winnerPosition == PlayerPosition.FIRST) ? winnerCounter : opponentCounter;
        counterSecondPlayer = (winnerPosition == PlayerPosition.FIRST) ? opponentCounter : winnerCounter;
    }

    public Scorable getLastGameInList(){
        return games.get(games.size() - 1);
    }

    private boolean hasWinningScore(int winnerCounter, int opponentCounter){
        return winnerCounter >= MIN_SCORE_TO_WIN
                && (winnerCounter - opponentCounter) >= MIN_SCORE_DIFFERENCE_TO_WIN;
    }

    private boolean hasScoreForTieBreak(int winnerCounter, int opponentCounter){
        return winnerCounter == MIN_SCORE_TO_WIN
                && opponentCounter == MIN_SCORE_TO_WIN;
    }

    public boolean isFinished() {
        return this.isFinished;
    }

    public int getCounterFirstPlayer() {
        return counterFirstPlayer;
    }

    public int getCounterSecondPlayer() {
        return counterSecondPlayer;
    }

    public List<Scorable> getGames() {
        return games;
    }
}
