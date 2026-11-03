package pet.project.services;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.dao.MatchDao;
import pet.project.dto.*;
import pet.project.entities.Matches;
import pet.project.entities.Player;
import pet.project.exceptions.PlayerNotFoundByNameException;
import pet.project.exceptions.SameNamePlayersException;
import pet.project.exceptions.ValidationException;
import pet.project.mappers.PlayerMapper;
import pet.project.models.ActiveMatch;
import pet.project.models.TennisPlayer;
import pet.project.storage.MatchStorage;
import pet.project.utils.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class MatchService {
    private final PlayerService playerService;
    private final MatchStorage matchStorage;
    private final MatchDao matchDao;
    private final PlayerMapper playerMapper;

    @Autowired
    public MatchService(PlayerService playerService, MatchDao matchDao, MatchStorage matchStorage, PlayerMapper playerMapper) {
        this.playerService = playerService;
        this.matchDao = matchDao;
        this.matchStorage = matchStorage;
        this.playerMapper = playerMapper;
    }

    @Transactional
    public UUID createMatch(CreateMatchRequest createMatchRequest){
        // остановился тут
        if(Validator.checkIdenticalNames(createMatchRequest)) {
            throw new SameNamePlayersException(createMatchRequest.firstPlayerName(), createMatchRequest.secondPlayerName());
        }

        Player firstPlayer = playerService.findOrCreatePlayer(createMatchRequest.firstPlayerName());
        Player secondPlayer = playerService.findOrCreatePlayer(createMatchRequest.secondPlayerName());

        TennisPlayer tennisFirstPlayer = new TennisPlayer(firstPlayer.getName());
        TennisPlayer tennisSecondPlayer = new TennisPlayer(secondPlayer.getName());

        ActiveMatch activeMatch = new ActiveMatch(tennisFirstPlayer, tennisSecondPlayer);

        return matchStorage.addMatch(activeMatch);
    }

    @Transactional
    public MatchDto addPoint(UUID uuid, AddPointRequest addPointRequest){
        ActiveMatch currentMatch = matchStorage.findMatchByUUID(uuid);
        currentMatch.addPointTo(addPointRequest.getName());

        PlayerDto firstPlayerDto = playerMapper.entityToDto(currentMatch.getFirstPlayer().name(), currentMatch);
        PlayerDto secondPlayerDto = playerMapper.entityToDto(currentMatch.getSecondPlayer().name(), currentMatch);
        MatchDto matchDto = new MatchDto(firstPlayerDto, secondPlayerDto, null);

        if(currentMatch.isMatchCompleted()){
            System.out.println(getClass().getName());
            saveFinishedMatch(currentMatch, matchDto);
        }

        return matchDto;
    }

    @Transactional
    public void saveFinishedMatch(ActiveMatch currentMatch, MatchDto matchDto){
        String winnerName = currentMatch.getWinner().name();
        matchDto.setWinnerName(winnerName);

        Player firstPlayer = playerService.findByName(currentMatch.getFirstPlayer().name());
        Player secondPlayer = playerService.findByName(currentMatch.getSecondPlayer().name());
        Player winnerPlayer = Objects.equals(winnerName, firstPlayer.getName()) ? firstPlayer : secondPlayer;

        Matches matches = new Matches(firstPlayer, secondPlayer, winnerPlayer);

        matchDao.save(matches);
    }

    @Transactional(readOnly = true)
    public ListFinishedMatchDto getFinishedMatches(Integer page, String playerName, int pageSize){

        page = Validator.validateIntegerPage(page);

        if(playerName != null && !playerService.existsByName(playerName)){
            throw new PlayerNotFoundByNameException(playerName);
        }

        int offset = page * pageSize;

        List<Matches> listMatches = (playerName == null) ? matchDao.findFinishedMatches(pageSize, offset)
                : matchDao.findFinishedMatchesByPlayerName(pageSize, offset, playerName);

        Long totalRecords = (playerName == null) ? matchDao.countAll()
                : matchDao.countAllByPlayerName(playerName);

        return listMatchesToDto(listMatches, page, totalRecords, pageSize);
    }

    private ListFinishedMatchDto listMatchesToDto(List<Matches> listMatches, int page, long totalRecords, int pageSize){
        ListFinishedMatchDto listFinishedMatchDto = new ListFinishedMatchDto();
        listFinishedMatchDto.setMatches(new ArrayList<>());

        for(Matches matches : listMatches){
            FinishedMatchDto finishedMatchDto = new FinishedMatchDto();
            finishedMatchDto.setFirstPlayerName(matches.getPlayer1().getName());
            finishedMatchDto.setSecondPlayerName(matches.getPlayer2().getName());
            finishedMatchDto.setWinnerName(matches.getWinner().getName());

            listFinishedMatchDto.getMatches().add(finishedMatchDto);
        }

        listFinishedMatchDto.setCurrentPage(page);
        double totalPagesDouble = (totalRecords * 1.0) / pageSize;
        long totalPages = (long) Math.ceil(totalPagesDouble);

        listFinishedMatchDto.setTotalPages(totalPages);

        return listFinishedMatchDto;
    }

    public MatchDto getScore(UUID uuid){
        ActiveMatch currentMatch = matchStorage.findMatchByUUID(uuid);

        PlayerDto firstPlayerDto = playerMapper.entityToDto(currentMatch.getFirstPlayer().name(), currentMatch);
        PlayerDto secondPlayerDto = playerMapper.entityToDto(currentMatch.getSecondPlayer().name(), currentMatch);
        MatchDto matchDto = new MatchDto(firstPlayerDto, secondPlayerDto, null);

        if(currentMatch.isMatchCompleted()){
            matchDto.setWinnerName(currentMatch.getWinner().name());
        }

        return matchDto;
    }
}
