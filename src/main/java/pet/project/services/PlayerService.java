package pet.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.dao.PlayerDao;
import pet.project.dto.PlayerDto;
import pet.project.entities.Player;
import pet.project.enums.PlayerPosition;
import pet.project.exceptions.PlayerNotFoundByNameException;
import pet.project.models.*;

import java.util.Optional;

@Service
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
        return playerDao.existsByName(name);
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
}
