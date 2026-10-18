package pet.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pet.project.dto.CreateMatchDTO;
import pet.project.entities.Player;
import pet.project.exceptions.SameNamePlayersException;
import pet.project.models.Match;
import pet.project.models.TennisPlayer;
import pet.project.storage.MatchStorage;

import java.util.UUID;

@Component
public class MatchService {

    private final PlayerService playerService;
    private final MatchStorage matchStorage;

    @Autowired
    public MatchService(PlayerService playerService) {
        this.playerService = playerService;
        this.matchStorage = new MatchStorage();
    }

    public UUID createMatch(CreateMatchDTO createMatchDTO){
        Player firstPlayer = playerService.findOrCreatePlayer(createMatchDTO.getFirstPlayerName());
        Player secondPlayer = playerService.findOrCreatePlayer(createMatchDTO.getSecondPlayerName());

        if(firstPlayer.equals(secondPlayer)){
            throw new SameNamePlayersException(firstPlayer.getName(), secondPlayer.getName());
        }

        TennisPlayer tennisFirstPlayer = new TennisPlayer(firstPlayer.getName());
        TennisPlayer tennisSecondPlayer = new TennisPlayer(secondPlayer.getName());

        UUID uuid = UUID.randomUUID();
        Match match = new Match(tennisFirstPlayer, tennisSecondPlayer);
        matchStorage.addMatch(uuid, match);
        return uuid;
    }
}
