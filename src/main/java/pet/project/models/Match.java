package pet.project.models;

import pet.project.enums.PlayerPosition;
import pet.project.exceptions.MatchIsAlreadyCompletedException;
import pet.project.exceptions.MatchIsNotCompletedException;
import pet.project.exceptions.NamePlayerNotInMatchException;

import java.util.ArrayList;
import java.util.List;

public class Match {
    private static final int INITIAL_SCORE = 0;
    public static final int MIN_SCORE_TO_WIN = 2;

    private int counterFirstPlayer;
    private int counterSecondPlayer;

    private final List<TennisSet> listTennisSet;

    public Match(){
        this.counterFirstPlayer = INITIAL_SCORE;
        this.counterSecondPlayer = INITIAL_SCORE;
        listTennisSet = new ArrayList<>();
    }

    public void addPointTo(PlayerPosition winnerPosition){
        if(isFinished()){
            throw new MatchIsAlreadyCompletedException();
        }

        int winnerCounter = (winnerPosition == PlayerPosition.FIRST) ? counterFirstPlayer : counterSecondPlayer;
        int opponentCounter = (winnerPosition == PlayerPosition.FIRST) ? counterSecondPlayer : counterFirstPlayer;

        if(listTennisSet.isEmpty()){
            listTennisSet.add(new TennisSet());
        }

        TennisSet tennisSet = getLastTennisSetInList();

        tennisSet.addPointTo(winnerPosition);

        if(tennisSet.isFinished()){
            winnerCounter++;
            counterFirstPlayer = (winnerPosition == PlayerPosition.FIRST) ? winnerCounter : opponentCounter;
            counterSecondPlayer = (winnerPosition == PlayerPosition.FIRST) ? opponentCounter : winnerCounter;

            if(isFinished()){
                return;
            }
            else{
                listTennisSet.add(new TennisSet());
            }
        }
    }

    public boolean isFinished(){
        if(counterFirstPlayer >= MIN_SCORE_TO_WIN){
            return true;
        }
        else return counterSecondPlayer >= MIN_SCORE_TO_WIN;
    }

    private TennisSet getLastTennisSetInList(){
        return listTennisSet.isEmpty() ? new TennisSet() : listTennisSet.get(listTennisSet.size() - 1);
    }

    public int getCounterFirstPlayer() {
        return counterFirstPlayer;
    }

    public int getCounterSecondPlayer() {
        return counterSecondPlayer;
    }

    public SetScore getSetScore(){
        TennisSet tennisSet = getLastTennisSetInList();
        return new SetScore(tennisSet.getCounterFirstPlayer(), tennisSet.getCounterSecondPlayer());
    }

    public boolean isTieBreak(){
        return getLastTennisSetInList().isTieBreak();
    }

    public GameScore getGameScore(){
        return getLastTennisSetInList().getGameScore();
    }
}
