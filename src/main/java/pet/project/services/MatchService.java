package pet.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pet.project.dao.MatchDao;
import pet.project.dto.AddPointRequest;
import pet.project.dto.MatchDto;
import pet.project.dto.CreateMatchRequest;
import pet.project.dto.PlayerDto;
import pet.project.entities.Matches;
import pet.project.entities.Player;
import pet.project.exceptions.SameNamePlayersException;
import pet.project.models.Match;
import pet.project.models.TennisPlayer;
import pet.project.storage.MatchStorage;

import java.util.Objects;
import java.util.UUID;

@Component
public class MatchService {

    private final PlayerService playerService;
    private final MatchStorage matchStorage;
    private final MatchDao matchDao;

    @Autowired
    public MatchService(PlayerService playerService, MatchDao matchDao) {
        this.playerService = playerService;
        this.matchDao = matchDao;
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

        return uuid;
    }

    public MatchDto addPoint(UUID uuid, AddPointRequest addPointRequest){
        Match currentMatch = matchStorage.findMatchByUUID(uuid);
        currentMatch.addPointTo(addPointRequest.getName());

        PlayerDto firstPlayerDto = playerService.playerToPlayerDto(currentMatch.getFirstPlayer().name(), currentMatch);
        PlayerDto secondPlayerDto = playerService.playerToPlayerDto(currentMatch.getSecondPlayer().name(), currentMatch);
        MatchDto matchDto = new MatchDto(firstPlayerDto, secondPlayerDto, null);

        if(currentMatch.isMatchCompleted()){
            saveFinishedMatch(currentMatch, matchDto);
        }

        return matchDto;
    }

    public MatchDto getScore(UUID uuid){
        Match currentMatch = matchStorage.findMatchByUUID(uuid);

        PlayerDto firstPlayerDto = playerService.playerToPlayerDto(currentMatch.getFirstPlayer().name(), currentMatch);
        PlayerDto secondPlayerDto = playerService.playerToPlayerDto(currentMatch.getSecondPlayer().name(), currentMatch);
        MatchDto matchDto = new MatchDto(firstPlayerDto, secondPlayerDto, null);

        if(currentMatch.isMatchCompleted()){
            matchDto.setWinnerName(currentMatch.getWinner().name());
        }

        return matchDto;
    }

    public void saveFinishedMatch(Match currentMatch, MatchDto matchDto){
        String winnerName = currentMatch.getWinner().name();
        matchDto.setWinnerName(winnerName);

        Player firstPlayer = playerService.findByName(currentMatch.getFirstPlayer().name());
        Player secondPlayer = playerService.findByName(currentMatch.getSecondPlayer().name());
        Player winnerPlayer = Objects.equals(winnerName, firstPlayer.getName()) ? firstPlayer : secondPlayer;

        Matches matches = new Matches(firstPlayer, secondPlayer, winnerPlayer);

        matchDao.save(matches);
    }
}
