package pet.project.models;

import pet.project.enums.PlayerPosition;
import pet.project.exceptions.MatchIsAlreadyCompletedException;
import pet.project.exceptions.MatchIsNotCompletedException;
import pet.project.exceptions.NamePlayerNotInMatchException;

public class Match {
    private int counterFirstPlayer;
    private int counterSecondPlayer;
    private boolean isMatchCompleted;
    private TennisPlayer firstPlayer;
    private TennisPlayer secondPlayer;
    private Set set;

    private static final int INITIAL_VALUE = 0;
    private static final int WIN_VALUE = 2;

    public Match(TennisPlayer firstPlayer, TennisPlayer secondPlayer){
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.counterFirstPlayer = INITIAL_VALUE;
        this.counterSecondPlayer = INITIAL_VALUE;
        set = new Set();
    }

    public void addPointTo(String nameFirstPlayer){
        if(isMatchCompleted){
            throw new MatchIsAlreadyCompletedException();
        }

        PlayerPosition playerPosition = isFirstPlayer(nameFirstPlayer);

        int counter = (playerPosition == PlayerPosition.FIRST) ? counterFirstPlayer : counterSecondPlayer;
        int opponentCounter = (playerPosition == PlayerPosition.FIRST) ? counterSecondPlayer : counterFirstPlayer;

        set.addPointTo(playerPosition);
        if(set.isSetCompleted()){
            counter++;
            if(counter >= WIN_VALUE){
                isMatchCompleted = true;
            }
            else{
                set = new Set();
            }
        }

        counterFirstPlayer = (playerPosition == PlayerPosition.FIRST) ? counter : opponentCounter;
        counterSecondPlayer = (playerPosition == PlayerPosition.FIRST) ? opponentCounter : counter;
    }

    public PlayerPosition isFirstPlayer(String nameFirstPlayer){
        if(firstPlayer.name().equals(nameFirstPlayer)){
            return PlayerPosition.FIRST;
        }
        else if(secondPlayer.name().equals(nameFirstPlayer)){
            return PlayerPosition.SECOND;
        }
        else{
            throw new NamePlayerNotInMatchException(nameFirstPlayer);
        }
    }

    public TennisPlayer getWinner(){
        if(counterFirstPlayer >= 2){
            return firstPlayer;
        }
        else if(counterSecondPlayer >= 2){
            return secondPlayer;
        }
        else{
            throw new MatchIsNotCompletedException();
        }
    }

    public int getCounterFirstPlayer() {
        return counterFirstPlayer;
    }

    public Set getSet() {
        return set;
    }
}
