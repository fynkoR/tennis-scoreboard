package pet.project.models;

import pet.project.exceptions.SetIsAlreadyCompletedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TennisSet {
    private static final int MIN_SCORE_TO_WIN = 6;
    private static final int MIN_SCORE_DIFFERENCE_TO_WIN = 2;

    private final List<Scorable> games;

    public TennisSet(){
        games = new ArrayList<>();
    }

    public void addPointTo(PlayerPosition winnerPosition){
        if(isFinished()){
            throw new SetIsAlreadyCompletedException();
        }

        if(games.isEmpty()){
            games.add(new Game());
        }

        Scorable scorable = getLastGameInList();
        scorable.addPointTo(winnerPosition);

        if(scorable.isFinished()){

            if(isFinished()){
                return;
            }
            else if(hasScoreForTieBreak(getScorePlayer(PlayerPosition.FIRST), getScorePlayer(PlayerPosition.SECOND))){
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
        else if(hasWinningScore(getScorePlayer(PlayerPosition.FIRST), getScorePlayer(PlayerPosition.SECOND))){
            return true;
        }
        else return hasWinningScore(getScorePlayer(PlayerPosition.SECOND), getScorePlayer(PlayerPosition.FIRST));
    }

    public Scorable getLastGameInList() {
        return games.isEmpty() ? new Game() : games.get(games.size() - 1);
    }

    private boolean hasWinningScore(int winnerCounter, int opponentCounter){
        return winnerCounter >= MIN_SCORE_TO_WIN
                && (winnerCounter - opponentCounter) >= MIN_SCORE_DIFFERENCE_TO_WIN;
    }

    private boolean hasScoreForTieBreak(int winnerCounter, int opponentCounter){
        return winnerCounter == MIN_SCORE_TO_WIN
                && opponentCounter == MIN_SCORE_TO_WIN;
    }

    public boolean isTieBreak() {
        Scorable scorable = null;
        if (!games.isEmpty()) {
            scorable = getLastGameInList();
        }
        return scorable instanceof TieBreak;
    }

    public GameScore getGameScore(){
        return getLastGameInList().getGameScore();
    }

    public Optional<PlayerPosition> getWinner(){
        if(!isFinished()) return Optional.empty();
        if(getScorePlayer(PlayerPosition.FIRST) > getScorePlayer(PlayerPosition.SECOND)) return Optional.of(PlayerPosition.FIRST);
        else return Optional.of(PlayerPosition.SECOND);
    }

    public int getScorePlayer(PlayerPosition playerPosition){
        long score = games.stream()
                .filter(games -> games.getWinner()
                        .filter(p -> p == playerPosition).isPresent())
                .count();

        return (int) score;
    }


}
