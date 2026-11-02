package pet.project.models;

import pet.project.enums.PlayerPosition;
import pet.project.exceptions.MatchIsAlreadyCompletedException;
import pet.project.exceptions.MatchIsNotCompletedException;
import pet.project.exceptions.NamePlayerNotInMatchException;

public class Match {
    private int counterFirstPlayer;
    private int counterSecondPlayer;
    private boolean isMatchCompleted;
    private TennisSet tennisSet;

    private static final int INITIAL_VALUE = 0;
    private static final int WIN_VALUE = 2;

    public Match(){
        this.counterFirstPlayer = INITIAL_VALUE;
        this.counterSecondPlayer = INITIAL_VALUE;
        tennisSet = new TennisSet();
    }

    public void addPointTo(PlayerPosition winnerPosition){
        if(isMatchCompleted){
            throw new MatchIsAlreadyCompletedException();
        }

        int counter = (winnerPosition == PlayerPosition.FIRST) ? counterFirstPlayer : counterSecondPlayer;
        int opponentCounter = (winnerPosition == PlayerPosition.FIRST) ? counterSecondPlayer : counterFirstPlayer;

        tennisSet.addPointTo(winnerPosition);
        if(tennisSet.isFinished()){
            counter++;
            if(counter >= WIN_VALUE){
                isMatchCompleted = true;
            }
            else{
                tennisSet = new TennisSet();
            }
        }

        counterFirstPlayer = (winnerPosition == PlayerPosition.FIRST) ? counter : opponentCounter;
        counterSecondPlayer = (winnerPosition == PlayerPosition.FIRST) ? opponentCounter : counter;
    }

    public int getCounterFirstPlayer() {
        return counterFirstPlayer;
    }

    public int getCounterSecondPlayer() {
        return counterSecondPlayer;
    }

    public SetScore getSetScore(){
        return new SetScore(tennisSet.getCounterFirstPlayer(), tennisSet.getCounterSecondPlayer());
    }

    public boolean isMatchCompleted() {
        return isMatchCompleted;
    }

    public TennisSet getSet() {
        return tennisSet;
    }
}
