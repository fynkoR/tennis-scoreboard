package pet.project.models;

import pet.project.enums.Point;
import pet.project.exceptions.GameIsAlreadyWinException;

public class Game implements Scorable{

    private Point firstPlayerPoint;
    private Point secondPlayerPoint;
    private boolean firstPlayerWin;

    public Game(){
        this.firstPlayerPoint = Point.LOVE;
        this.secondPlayerPoint = Point.LOVE;
    }

    @Override
    public void addPointTo(boolean isFirstPlayer){
        if(firstPlayerWin){
            throw new GameIsAlreadyWinException();
        }
        Point winnerPoints = isFirstPlayer ? firstPlayerPoint : secondPlayerPoint;
        Point opponentPoints = isFirstPlayer ? secondPlayerPoint : firstPlayerPoint;

        if(winnerPoints == Point.FORTY && (opponentPoints != Point.FORTY && opponentPoints != Point.ADVANTAGE)){
            firstPlayerWin = true;
        }
        else if(winnerPoints == Point.ADVANTAGE){
            firstPlayerWin = true;
        }
        else if (opponentPoints == Point.ADVANTAGE) {
            opponentPoints = Point.FORTY;
        }
        else{
            winnerPoints = winnerPoints.next();
        }

        firstPlayerPoint = isFirstPlayer ? winnerPoints : opponentPoints;
        secondPlayerPoint = isFirstPlayer ? opponentPoints : winnerPoints;
    }

    @Override
    public boolean isFinished() {
        return firstPlayerWin;
    }

    public void showScore(){
        System.out.println("Score 1 player: " + firstPlayerPoint);
        System.out.println("Score 2 player: " + secondPlayerPoint);
    }

    public void setFirstPlayerPoint(Point firstPlayerPoint) {
        this.firstPlayerPoint = firstPlayerPoint;
    }

    public void setSecondPlayerPoint(Point secondPlayerPoint) {
        this.secondPlayerPoint = secondPlayerPoint;
    }

    public void setFirstPlayerWin(boolean firstPlayerWin) {
        this.firstPlayerWin = firstPlayerWin;
    }

    public Point getFirstPlayerPoint() {
        return firstPlayerPoint;
    }

    public Point getSecondPlayerPoint() {
        return secondPlayerPoint;
    }
}
