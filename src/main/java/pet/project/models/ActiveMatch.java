package pet.project.models;

import pet.project.enums.PlayerPosition;
import pet.project.exceptions.MatchIsAlreadyCompletedException;
import pet.project.exceptions.MatchIsNotCompletedException;
import pet.project.exceptions.PlayerNotFoundByNameException;

public class ActiveMatch {
    private final TennisPlayer firstPlayer;
    private final TennisPlayer secondPlayer;
    private final Match match;


    public ActiveMatch(TennisPlayer firstPlayer, TennisPlayer secondPlayer){
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        match = new Match();
    }

    public void addPointTo(String winnerName){
        if(match.isFinished()){
            throw new MatchIsAlreadyCompletedException();
        }
        PlayerPosition winnerPosition = getPlayerPositionInMatch(winnerName);

        match.addPointTo(winnerPosition);
    }

    public PlayerPosition getPlayerPositionInMatch(String playerName){
        if(playerName.equals(firstPlayer.name())){
            return PlayerPosition.FIRST;
        }
        else if(playerName.equals(secondPlayer.name())){
            return PlayerPosition.SECOND;
        }
        throw new PlayerNotFoundByNameException(playerName);
    }

    public TennisPlayer getWinner(){
        if(match.getScorePlayer(PlayerPosition.FIRST) >= Match.MIN_SCORE_TO_WIN){
            return firstPlayer;
        }
        else if(match.getScorePlayer(PlayerPosition.SECOND) >= Match.MIN_SCORE_TO_WIN){
            return secondPlayer;
        }
        throw new MatchIsNotCompletedException();
    }

    public TennisPlayer getFirstPlayer() {
        return firstPlayer;
    }

    public TennisPlayer getSecondPlayer() {
        return secondPlayer;
    }

    public MatchScore getMatchScore(){
        return new MatchScore(match.getScorePlayer(PlayerPosition.FIRST), match.getScorePlayer(PlayerPosition.SECOND));
    }

    public SetScore getSetScore(){
        return match.getSetScore();
    }

    public boolean isMatchCompleted(){
        return match.isFinished();
    }

    public boolean isTieBreak(){
        return match.isTieBreak();
    }

    public GameScore getGameScore(){
        return match.getGameScore();
    }
}
