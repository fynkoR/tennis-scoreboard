package pet.project.models;

import pet.project.enums.PlayerPosition;
import pet.project.enums.Point;
import pet.project.exceptions.GameIsAlreadyWinException;

public class Game implements Scorable{

    private Point firstPlayerPoint;
    private Point secondPlayerPoint;
    private boolean isFinished;

    public Game(){
        this.firstPlayerPoint = Point.LOVE;
        this.secondPlayerPoint = Point.LOVE;
    }

    @Override
    public void addPointTo(PlayerPosition winnerPosition){

        if(isFinished){
            throw new GameIsAlreadyWinException();
        }

        Point winnerPoints = (winnerPosition == PlayerPosition.FIRST) ? firstPlayerPoint : secondPlayerPoint;
        Point opponentPoints = (winnerPosition == PlayerPosition.FIRST) ? secondPlayerPoint : firstPlayerPoint;

        if(finishGameIfWinnerAtFortyAndOpponentBelowForty(winnerPoints, opponentPoints)){
            isFinished = true;
        }
        else if(winnerPoints == Point.ADVANTAGE){
            isFinished = true;
        }
        else if (opponentPoints == Point.ADVANTAGE) {
            opponentPoints = Point.FORTY;
        }
        else{
            winnerPoints = winnerPoints.next();
        }

        firstPlayerPoint = (winnerPosition == PlayerPosition.FIRST) ? winnerPoints : opponentPoints;
        secondPlayerPoint = (winnerPosition == PlayerPosition.FIRST) ? opponentPoints : winnerPoints;
    }

    private boolean finishGameIfWinnerAtFortyAndOpponentBelowForty(Point winnerPoints, Point opponentPoints){
        return winnerPoints == Point.FORTY
                && opponentPoints != Point.FORTY
                && opponentPoints != Point.ADVANTAGE;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    public Point getFirstPlayerPoint() {
        return firstPlayerPoint;
    }

    public Point getSecondPlayerPoint() {
        return secondPlayerPoint;
    }

}
