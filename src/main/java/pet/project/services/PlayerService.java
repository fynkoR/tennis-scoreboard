package pet.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pet.project.dao.PlayerDao;
import pet.project.entities.Player;

import java.util.Optional;

@Component
public class PlayerService {
    private final PlayerDao playerDao;

    @Autowired
    public PlayerService(PlayerDao playerDao){
        this.playerDao = playerDao;
    }

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
}
