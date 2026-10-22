package pet.project.models;

import pet.project.enums.PlayerPosition;
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
    public void addPointTo(PlayerPosition playerPosition){

        if(firstPlayerWin){
            throw new GameIsAlreadyWinException();
        }

        Point winnerPoints = (playerPosition == PlayerPosition.FIRST) ? firstPlayerPoint : secondPlayerPoint;
        Point opponentPoints = (playerPosition == PlayerPosition.FIRST) ? secondPlayerPoint : firstPlayerPoint;

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

        firstPlayerPoint = (playerPosition == PlayerPosition.FIRST) ? winnerPoints : opponentPoints;
        secondPlayerPoint = (playerPosition == PlayerPosition.FIRST) ? opponentPoints : winnerPoints;
    }

    @Override
    public boolean isFinished() {
        return this.firstPlayerWin;
    }

    public Point getFirstPlayerPoint() {
        return this.firstPlayerPoint;
    }

    public Point getSecondPlayerPoint() {
        return this.secondPlayerPoint;
    }

}
