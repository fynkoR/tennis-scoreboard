package pet.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pet.project.dao.PlayerDao;
import pet.project.dto.PlayerDto;
import pet.project.entities.Player;
import pet.project.enums.PlayerPosition;
import pet.project.exceptions.PlayerNotFoundByNameException;
import pet.project.models.*;

import java.util.Optional;

@Component
public class PlayerService {
    private final PlayerDao playerDao;

    @Autowired
    public PlayerService(PlayerDao playerDao){
        this.playerDao = playerDao;
    }

    @Transactional(readOnly = true)
    public Player findByName(String name){
        return playerDao.findByName(name).orElseThrow(() -> new PlayerNotFoundByNameException(name));
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name){
        return playerDao.findByName(name).isPresent();
    }

    @Transactional
    public Player findOrCreatePlayer(String name){
        Optional<Player> player = playerDao.findByName(name);
        if(player.isPresent()){
            return player.get();
        }
        else{
            Player playerNew = new Player(name);
            playerDao.save(playerNew);
            return playerNew;
        }
    }

    public PlayerDto playerToPlayerDto(String name, ActiveMatch activeMatch){
        PlayerDto player = new PlayerDto();
        player.setName(name);

        PlayerPosition playerPosition = activeMatch.getPlayerPositionInMatch(name);
        boolean isFirstPlayer = playerPosition == PlayerPosition.FIRST;

        if(activeMatch.isTieBreak()){
            Integer points = Integer.valueOf(isFirstPlayer ? activeMatch.getGameScore().scoreFirstPlayer() : activeMatch.getGameScore().scoreSecondPlayer());
            player.setTieBreakPoints(points);
        }
        else{
            String points = isFirstPlayer ? activeMatch.getGameScore().scoreFirstPlayer() : activeMatch.getGameScore().scoreSecondPlayer();
            player.setPoints(points);
        }

        player.setGames(isFirstPlayer ? activeMatch.getSetScore().scoreFirstPlayer() : activeMatch.getSetScore().scoreSecondPlayer());
        player.setSets(isFirstPlayer ? activeMatch.getMatchScore().scoreFirstPlayer() : activeMatch.getMatchScore().scoreSecondPlayer());

        return player;
    }
}
