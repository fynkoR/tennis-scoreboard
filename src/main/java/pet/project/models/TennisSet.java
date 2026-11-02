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
    private final List<Scorable> games;

    public TennisSet(){
        counterFirstPlayer = INITIAL_SCORE;
        counterSecondPlayer = INITIAL_SCORE;
        games = new ArrayList<>();
    }

    public void addPointTo(PlayerPosition winnerPosition){
        if(isFinished()){
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
            updateCounterPlayers(winnerCounter, opponentCounter, winnerPosition);

            if(isFinished()){
                return;
            }
            else if(hasScoreForTieBreak(winnerCounter, opponentCounter)){
                games.add(new TieBreak());
            }
            else{
                games.add(new Game());
            }
        }
    }

    public boolean isFinished() {
        if(getLastGameInList() instanceof TieBreak tieBreak && tieBreak.isFinished()){
            return true;
        }
        else if(hasWinningScore(getCounterFirstPlayer(), getCounterSecondPlayer())){
            return true;
        }
        else return hasWinningScore(getCounterSecondPlayer(), getCounterFirstPlayer());
    }

    public Scorable getLastGameInList(){
        return games.isEmpty() ? new Game() : games.get(games.size() - 1);
    }

    private void updateCounterPlayers(int winnerCounter, int opponentCounter, PlayerPosition winnerPosition){
        counterFirstPlayer = (winnerPosition == PlayerPosition.FIRST) ? winnerCounter : opponentCounter;
        counterSecondPlayer = (winnerPosition == PlayerPosition.FIRST) ? opponentCounter : winnerCounter;
    }

    private boolean hasWinningScore(int winnerCounter, int opponentCounter){
        return winnerCounter >= MIN_SCORE_TO_WIN
                && (winnerCounter - opponentCounter) >= MIN_SCORE_DIFFERENCE_TO_WIN;
    }

    private boolean hasScoreForTieBreak(int winnerCounter, int opponentCounter){
        return winnerCounter == MIN_SCORE_TO_WIN
                && opponentCounter == MIN_SCORE_TO_WIN;
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
