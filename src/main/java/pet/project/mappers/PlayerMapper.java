package pet.project.mappers;

import org.springframework.stereotype.Component;
import pet.project.dto.PlayerDto;
import pet.project.entities.Player;
import pet.project.enums.PlayerPosition;
import pet.project.models.ActiveMatch;
import pet.project.models.TennisPlayer;

@Component
public class PlayerMapper {

    public PlayerDto entityToDto(String name, ActiveMatch activeMatch){
        PlayerPosition playerPosition = activeMatch.getPlayerPositionInMatch(name);
        boolean isFirstPlayer = playerPosition == PlayerPosition.FIRST;

        Integer pointsTieBreak = null;
        String pointsGame = null;

        if(activeMatch.isTieBreak()){
            pointsTieBreak = Integer.valueOf(isFirstPlayer ? activeMatch.getGameScore().scoreFirstPlayer() : activeMatch.getGameScore().scoreSecondPlayer());
        }
        else{
            pointsGame = isFirstPlayer ? activeMatch.getGameScore().scoreFirstPlayer() : activeMatch.getGameScore().scoreSecondPlayer();
        }

        int games = isFirstPlayer ? activeMatch.getSetScore().scoreFirstPlayer() : activeMatch.getSetScore().scoreSecondPlayer();
        int sets = isFirstPlayer ? activeMatch.getMatchScore().scoreFirstPlayer() : activeMatch.getMatchScore().scoreSecondPlayer();

        return new PlayerDto(
                name,
                pointsGame,
                games,
                sets,
                pointsTieBreak
        );
    }

    public TennisPlayer playerToTennisPlayer(Player player){
        return new TennisPlayer(player.getName());
    }
}
