package pet.project.mappers;

import org.springframework.stereotype.Component;
import pet.project.dto.PlayerDto;
import pet.project.enums.PlayerPosition;
import pet.project.models.ActiveMatch;

@Component
public class PlayerMapper {

    public PlayerDto entityToDto(String name, ActiveMatch activeMatch){
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName(name);

        PlayerPosition playerPosition = activeMatch.getPlayerPositionInMatch(name);
        boolean isFirstPlayer = playerPosition == PlayerPosition.FIRST;

        if(activeMatch.isTieBreak()){
            Integer points = Integer.valueOf(isFirstPlayer ? activeMatch.getGameScore().scoreFirstPlayer() : activeMatch.getGameScore().scoreSecondPlayer());
            playerDto.setTieBreakPoints(points);
        }
        else{
            String points = isFirstPlayer ? activeMatch.getGameScore().scoreFirstPlayer() : activeMatch.getGameScore().scoreSecondPlayer();
            playerDto.setPoints(points);
        }

        playerDto.setGames(isFirstPlayer ? activeMatch.getSetScore().scoreFirstPlayer() : activeMatch.getSetScore().scoreSecondPlayer());
        playerDto.setSets(isFirstPlayer ? activeMatch.getMatchScore().scoreFirstPlayer() : activeMatch.getMatchScore().scoreSecondPlayer());

        return playerDto;
    }
}
