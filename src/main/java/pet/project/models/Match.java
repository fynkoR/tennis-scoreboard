package pet.project.models;

import pet.project.enums.PlayerPosition;
import pet.project.exceptions.MatchIsAlreadyCompletedException;
import pet.project.exceptions.MatchIsNotCompletedException;
import pet.project.exceptions.NamePlayerNotInMatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Match {
    public static final int MIN_SCORE_TO_WIN = 2;

    private final List<TennisSet> listTennisSet;

    public Match(){
        listTennisSet = new ArrayList<>();
    }

    public void addPointTo(PlayerPosition winnerPosition){
        if(isFinished()){
            throw new MatchIsAlreadyCompletedException();
        }

        if(listTennisSet.isEmpty()){
            listTennisSet.add(new TennisSet());
        }

        TennisSet tennisSet = getLastTennisSetInList();

        tennisSet.addPointTo(winnerPosition);

        if(tennisSet.isFinished()){
            if(isFinished()){
                return;
            }
            else{
                listTennisSet.add(new TennisSet());
            }
        }
    }

    public boolean isFinished(){
        if(getScorePlayer(PlayerPosition.FIRST) >= MIN_SCORE_TO_WIN){
            return true;
        }
        else return getScorePlayer(PlayerPosition.SECOND) >= MIN_SCORE_TO_WIN;
    }

    private TennisSet getLastTennisSetInList(){
        return listTennisSet.isEmpty() ? new TennisSet() : listTennisSet.get(listTennisSet.size() - 1);
    }

    public int getScorePlayer(PlayerPosition playerPosition){
        long score = listTennisSet.stream()
                .filter(tennisSet -> tennisSet.getWinner()
                        .filter(p -> p == playerPosition).isPresent())
                .count();

        return (int) score;
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
