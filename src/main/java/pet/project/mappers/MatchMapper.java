package pet.project.mappers;

import org.springframework.stereotype.Component;
import pet.project.dto.FinishedMatchDto;
import pet.project.dto.ListFinishedMatchDto;
import pet.project.entities.Matches;

import java.util.ArrayList;
import java.util.List;

@Component
public class MatchMapper {

    public ListFinishedMatchDto mapMatchesToDto(List<Matches> listMatches, int page, long totalRecords, int pageSize){
        ListFinishedMatchDto listFinishedMatchDto = new ListFinishedMatchDto();

        List<FinishedMatchDto> finishedMatchDtoList = getListFinishedMatchDto(listMatches);

        listFinishedMatchDto.setMatches(finishedMatchDtoList);
        listFinishedMatchDto.setCurrentPage(page);

        double totalPagesDouble = (totalRecords * 1.0) / pageSize;
        long totalPages = (long) Math.ceil(totalPagesDouble);
        listFinishedMatchDto.setTotalPages(totalPages);

        return listFinishedMatchDto;
    }

    private List<FinishedMatchDto> getListFinishedMatchDto(List<Matches> listMatches){
        List<FinishedMatchDto> finishedMatchDtoList = new ArrayList<>();

        for(Matches matches : listMatches){
            FinishedMatchDto finishedMatchDto = new FinishedMatchDto();

            finishedMatchDto.setFirstPlayerName(matches.getPlayer1().getName());
            finishedMatchDto.setSecondPlayerName(matches.getPlayer2().getName());
            finishedMatchDto.setWinnerName(matches.getWinner().getName());

            finishedMatchDtoList.add(finishedMatchDto);
        }

        return finishedMatchDtoList;

    }
}
