package pet.project.models;

import pet.project.enums.PlayerPosition;
import pet.project.exceptions.GameIsAlreadyWinException;

public class TieBreak implements Scorable{
    private int counterFirstPlayer;
    private int counterSecondPlayer;
    private boolean winner;

    private static final int INITIAL_VALUE = 0;
    private static final int WIN_VALUE = 7;
    private static final int DIFFERENCE_VALUE = 2;


    public TieBreak(){
        this.counterFirstPlayer = INITIAL_VALUE;
        this.counterSecondPlayer = INITIAL_VALUE;
    }

    @Override
    public void addPointTo(PlayerPosition playerPosition) {
        if(winner){
            throw new GameIsAlreadyWinException();
        }

        int counter = (playerPosition == PlayerPosition.FIRST) ? counterFirstPlayer : counterSecondPlayer;
        int opponentCounter = (playerPosition == PlayerPosition.FIRST) ? counterSecondPlayer : counterFirstPlayer;

        counter++;

        if(counter >= WIN_VALUE && (counter - opponentCounter) >= DIFFERENCE_VALUE){
            winner = true;
        }

        counterFirstPlayer = (playerPosition == PlayerPosition.FIRST) ? counter : opponentCounter;
        counterSecondPlayer = (playerPosition == PlayerPosition.FIRST) ? opponentCounter : counter;
    }

    @Override
    public boolean isFinished() {
        return this.winner;
    }

    @Override
    public void showScore() {
        System.out.println("Score 1 player: " + counterFirstPlayer);
        System.out.println("Score 2 player: " + counterSecondPlayer);
    }
}
