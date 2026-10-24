package pet.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pet.project.dao.PlayerDao;
import pet.project.dto.PlayerDto;
import pet.project.entities.Player;
import pet.project.enums.PlayerPosition;
import pet.project.exceptions.PlayerNotFoundByNameException;
import pet.project.models.Game;
import pet.project.models.Match;
import pet.project.models.Scorable;
import pet.project.models.TieBreak;

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

    public PlayerDto playerToPlayerDto(String name, Match match){
        PlayerDto player = new PlayerDto();
        player.setName(name);

        Scorable scorable = match.getSet().getCurrentGame();

        PlayerPosition playerPosition = match.isFirstPlayer(name);
        boolean isFirstPlayer = playerPosition == PlayerPosition.FIRST;

        if(scorable instanceof Game game){
            String points = isFirstPlayer ? game.getFirstPlayerPoint().getTitle() : game.getSecondPlayerPoint().getTitle();
            player.setPoints(points);
        }
        else {
            TieBreak tieBreak = (TieBreak) scorable;
            Integer points = isFirstPlayer ? tieBreak.getCounterFirstPlayer() : tieBreak.getCounterSecondPlayer();
            player.setTieBreakPoints(points);
        }

        player.setGames(isFirstPlayer ? match.getSet().getCounterFirstPlayer() : match.getSet().getCounterSecondPlayer());
        player.setSets(isFirstPlayer ? match.getCounterFirstPlayer() : match.getCounterSecondPlayer());

        return player;
    }
}
