package pet.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.dto.*;
import pet.project.entities.Player;
import pet.project.exceptions.SameNamePlayersException;
import pet.project.mappers.PlayerMapper;
import pet.project.models.ActiveMatch;
import pet.project.models.TennisPlayer;
import pet.project.storage.MatchStorage;
import pet.project.utils.Validator;
import java.util.UUID;

@Service
public class MatchService {
    private final PlayerService playerService;
    private final MatchStorage matchStorage;
    private final PlayerMapper playerMapper;
    private final FinishedMatchService finishedMatchService;

    @Autowired
    public MatchService(PlayerService playerService, MatchStorage matchStorage, PlayerMapper playerMapper, FinishedMatchService finishedMatchService) {
        this.playerService = playerService;
        this.matchStorage = matchStorage;
        this.playerMapper = playerMapper;
        this.finishedMatchService = finishedMatchService;
    }

    @Transactional
    public UUID createMatch(CreateMatchRequest createMatchRequest){
        if(Validator.checkIdenticalNames(createMatchRequest)) {
            throw new SameNamePlayersException(createMatchRequest.firstPlayerName(), createMatchRequest.secondPlayerName());
        }

        Player firstPlayer = playerService.findOrCreatePlayer(createMatchRequest.firstPlayerName());
        Player secondPlayer = playerService.findOrCreatePlayer(createMatchRequest.secondPlayerName());

        TennisPlayer tennisFirstPlayer = playerMapper.playerToTennisPlayer(firstPlayer);
        TennisPlayer tennisSecondPlayer = playerMapper.playerToTennisPlayer(secondPlayer);

        ActiveMatch activeMatch = new ActiveMatch(tennisFirstPlayer, tennisSecondPlayer);

        return matchStorage.addMatch(activeMatch);
    }

    public MatchDto addPoint(UUID uuid, AddPointRequest addPointRequest){
        String winnerName = null;
        PlayerDto firstPlayerDto;
        PlayerDto secondPlayerDto;

        synchronized (this){
            ActiveMatch currentMatch = matchStorage.findMatchByUUID(uuid);
            currentMatch.addPointTo(addPointRequest.getName());

            firstPlayerDto = playerMapper.entityToDto(currentMatch.getFirstPlayer().name(), currentMatch);
            secondPlayerDto = playerMapper.entityToDto(currentMatch.getSecondPlayer().name(), currentMatch);

            if(currentMatch.isMatchCompleted()){
                winnerName = currentMatch.getWinner().name();
                finishedMatchService.saveFinishedMatch(currentMatch);
            }
        }

        return new MatchDto(firstPlayerDto, secondPlayerDto, winnerName);
    }

    public MatchDto getScore(UUID uuid){
        ActiveMatch currentMatch = matchStorage.findMatchByUUID(uuid);

        PlayerDto firstPlayerDto = playerMapper.entityToDto(currentMatch.getFirstPlayer().name(), currentMatch);
        PlayerDto secondPlayerDto = playerMapper.entityToDto(currentMatch.getSecondPlayer().name(), currentMatch);
        String winnerName = currentMatch.isMatchCompleted() ? currentMatch.getWinner().name() : null;

        return new MatchDto(firstPlayerDto, secondPlayerDto, winnerName);
    }
}
