package pet.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pet.project.dao.MatchDao;
import pet.project.dto.*;
import pet.project.entities.Matches;
import pet.project.entities.Player;
import pet.project.exceptions.PlayerNotFoundByNameException;
import pet.project.exceptions.SameNamePlayersException;
import pet.project.models.Match;
import pet.project.models.TennisPlayer;
import pet.project.storage.MatchStorage;
import pet.project.utils.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class MatchService {

    private static final int LIMIT_VALUE = 2;

    private final PlayerService playerService;
    private final MatchStorage matchStorage;
    private final MatchDao matchDao;

    @Autowired
    public MatchService(PlayerService playerService, MatchDao matchDao) {
        this.playerService = playerService;
        this.matchDao = matchDao;
        this.matchStorage = new MatchStorage();
    }

    @Transactional
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

    @Transactional
    public MatchDto addPoint(UUID uuid, AddPointRequest addPointRequest){
        Match currentMatch = matchStorage.findMatchByUUID(uuid);
        currentMatch.addPointTo(addPointRequest.getName());

        PlayerDto firstPlayerDto = playerService.playerToPlayerDto(currentMatch.getFirstPlayer().name(), currentMatch);
        PlayerDto secondPlayerDto = playerService.playerToPlayerDto(currentMatch.getSecondPlayer().name(), currentMatch);
        MatchDto matchDto = new MatchDto(firstPlayerDto, secondPlayerDto, null);

        if(currentMatch.isMatchCompleted()){
            System.out.println(getClass().getName());
            saveFinishedMatch(currentMatch, matchDto);
        }

        return matchDto;
    }

    @Transactional
    public void saveFinishedMatch(Match currentMatch, MatchDto matchDto){
        String winnerName = currentMatch.getWinner().name();
        matchDto.setWinnerName(winnerName);

        Player firstPlayer = playerService.findByName(currentMatch.getFirstPlayer().name());
        Player secondPlayer = playerService.findByName(currentMatch.getSecondPlayer().name());
        Player winnerPlayer = Objects.equals(winnerName, firstPlayer.getName()) ? firstPlayer : secondPlayer;

        Matches matches = new Matches(firstPlayer, secondPlayer, winnerPlayer);

        matchDao.save(matches);
    }

    @Transactional(readOnly = true)
    public ListFinishedMatchDto getFinishedMatches(Integer page, String playerName){

        page = Validator.validateIntegerPage(page);

        if(playerName != null && !playerService.existsByName(playerName)){
            throw new PlayerNotFoundByNameException(playerName);
        }

        int offset = page * LIMIT_VALUE;

        List<Matches> listMatches = (playerName == null) ? matchDao.findFinishedMatches(LIMIT_VALUE, offset)
                : matchDao.findFinishedMatchesByPlayerName(LIMIT_VALUE, offset, playerName);

        Long totalRecords = (playerName == null) ? matchDao.countByFindFinishedMatches()
                : matchDao.countByFindFinishedMatchesByPlayerName(playerName);

        return listMatchesToDto(listMatches, page, totalRecords);
    }

    public ListFinishedMatchDto listMatchesToDto(List<Matches> listMatches, int page, long totalRecords){
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
        double totalPagesDouble = (totalRecords * 1.0) / LIMIT_VALUE;
        long totalPages = (long) Math.ceil(totalPagesDouble);

        listFinishedMatchDto.setTotalPages(totalPages);

        return listFinishedMatchDto;
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
}
