package pet.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.dao.MatchDao;
import pet.project.dto.ListFinishedMatchDto;
import pet.project.entities.Matches;
import pet.project.entities.Player;
import pet.project.exceptions.PlayerNotFoundByNameException;
import pet.project.mappers.MatchMapper;
import pet.project.models.ActiveMatch;
import pet.project.models.Match;
import pet.project.utils.Validator;

import java.util.List;
import java.util.Objects;

@Service
public class FinishedMatchService {
    private final PlayerService playerService;
    private final MatchDao matchDao;
    private final MatchMapper matchMapper;

    @Autowired
    public FinishedMatchService(PlayerService playerService, MatchDao matchDao, MatchMapper matchMapper) {
        this.playerService = playerService;
        this.matchDao = matchDao;
        this.matchMapper = matchMapper;
    }

    @Transactional
    protected void saveFinishedMatch(ActiveMatch currentMatch){
        Player firstPlayer = playerService.findByName(currentMatch.getFirstPlayer().name());
        Player secondPlayer = playerService.findByName(currentMatch.getSecondPlayer().name());

        String winnerName = currentMatch.getWinner().name();
        Player winnerPlayer = Objects.equals(winnerName, firstPlayer.getName()) ? firstPlayer : secondPlayer;

        Matches matches = new Matches(firstPlayer, secondPlayer, winnerPlayer);

        matchDao.save(matches);
    }

    @Transactional(readOnly = true)
    public ListFinishedMatchDto getFinishedMatches(Integer page, String playerName, int pageSize){

        page = Validator.validateIntegerPage(page);

        int offset = page * pageSize;

        if(playerName != null && !playerService.existsByName(playerName)){
            return matchMapper.mapMatchesToDto(List.of(), page, 0, pageSize);
        }

        List<Matches> listMatches = (playerName == null) ? matchDao.findFinishedMatches(pageSize, offset)
                : matchDao.findFinishedMatchesByPlayerName(pageSize, offset, playerName);

        Long totalRecords = (playerName == null) ? matchDao.countAll()
                : matchDao.countAllByPlayerName(playerName);

        return matchMapper.mapMatchesToDto(listMatches, page, totalRecords, pageSize);

    }
}
