package pet.project.models;

import pet.project.exceptions.SetIsAlreadyCompletedException;

public class Set {
    private int counterFirstPlayer;
    private int counterSecondPlayer;
    private boolean isSetCompleted;
    private Scorable currentGame;
    private boolean isTieBreak;

    private static final int INITIAL_VALUE_GAME = 0;
    private static final int WIN_VALUE_GAME = 6;
    private static final int DIFFERENCE_VALUE_GAME = 2;

    public Set(){
        counterFirstPlayer = INITIAL_VALUE_GAME;
        counterSecondPlayer = INITIAL_VALUE_GAME;
        currentGame = new Game();
    }

    public void addPointTo(boolean isFirstPlayer){
        if(isSetCompleted){
            throw new SetIsAlreadyCompletedException();
        }

        int counter = isFirstPlayer ? counterFirstPlayer : counterSecondPlayer;
        int opponentCounter = isFirstPlayer ? counterSecondPlayer : counterFirstPlayer;

        if(isTieBreak){
            currentGame.addPointTo(isFirstPlayer);
            if(currentGame.isFinished()){
                counter++;
                isSetCompleted = true;
            }
        }
        else{
            currentGame.addPointTo(isFirstPlayer);
            if(currentGame.isFinished()){
                counter++;
                if(counter >= WIN_VALUE_GAME && (counter - opponentCounter >= DIFFERENCE_VALUE_GAME)){
                    isSetCompleted = true;
                }
                else if(counter == WIN_VALUE_GAME && opponentCounter == WIN_VALUE_GAME){
                    currentGame = new TieBreak();
                    isTieBreak = true;
                }
                else{
                    currentGame = new Game();
                }
            }
        }
        counterFirstPlayer = isFirstPlayer ? counter : opponentCounter;
        counterSecondPlayer = isFirstPlayer ? opponentCounter : counter;
    }

    public boolean isSetCompleted() {
        return isSetCompleted;
    }

    public Scorable getCurrentGame() {
        return currentGame;
    }

    public int getCounterFirstPlayer() {
        return counterFirstPlayer;
    }

    public int getCounterSecondPlayer() {
        return counterSecondPlayer;
    }

    public void showScore(){
        System.out.println("Счетчик игр 1 игрока: " + getCounterFirstPlayer());
        System.out.println("Счетчик игр 2 игрока: " + getCounterSecondPlayer());
    }
}
