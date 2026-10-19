package pet.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pet.project.dto.AddPointRequest;
import pet.project.dto.AddPointResponse;
import pet.project.dto.CreateMatchRequest;
import pet.project.dto.PlayerDto;
import pet.project.entities.Player;
import pet.project.exceptions.SameNamePlayersException;
import pet.project.models.Game;
import pet.project.models.Match;
import pet.project.models.Scorable;
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

    public UUID createMatch(CreateMatchRequest createMatchRequest){
        Player firstPlayer = playerService.findOrCreatePlayer(createMatchRequest.getFirstPlayerName());
        Player secondPlayer = playerService.findOrCreatePlayer(createMatchRequest.getSecondPlayerName());

        if(firstPlayer.equals(secondPlayer)){
            throw new SameNamePlayersException(firstPlayer.getName(), secondPlayer.getName());
        }

        TennisPlayer tennisFirstPlayer = new TennisPlayer(firstPlayer.getName());
        TennisPlayer tennisSecondPlayer = new TennisPlayer(secondPlayer.getName());

        UUID uuid = UUID.randomUUID();
        Match match = new Match(tennisFirstPlayer, tennisSecondPlayer);
        matchStorage.addMatch(uuid, match);

        System.out.println(matchStorage.getMatches());

        return uuid;
    }

    public AddPointResponse addPoint(UUID uuid, AddPointRequest addPointRequest){
        Match currentMatch = matchStorage.findMatchByUUID(uuid);
        currentMatch.addPointTo(addPointRequest.getName());

        PlayerDto firstPlayerDto = playerService.playerToPlayerDto(currentMatch.getFirstPlayer().name(), currentMatch);

        PlayerDto secondPlayerDto = playerService.playerToPlayerDto(currentMatch.getSecondPlayer().name(), currentMatch);

        return new AddPointResponse(firstPlayerDto, secondPlayerDto, null);
    }
}
