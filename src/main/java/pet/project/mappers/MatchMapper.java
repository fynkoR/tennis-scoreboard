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

        List<FinishedMatchDto> finishedMatchDtoList = getListFinishedMatchDto(listMatches);

        double totalPagesDouble = (totalRecords * 1.0) / pageSize;
        long totalPages = (long) Math.ceil(totalPagesDouble);

        return new ListFinishedMatchDto(
                finishedMatchDtoList,
                // т.к. уменьшаю в контроллере на 1 , для показа пользователю
                page + 1,
                totalPages
        );
    }

    private List<FinishedMatchDto> getListFinishedMatchDto(List<Matches> listMatches){
        List<FinishedMatchDto> finishedMatchDtoList = new ArrayList<>();

        for(Matches matches : listMatches){
            FinishedMatchDto finishedMatchDto = new FinishedMatchDto(
                    matches.getPlayer1().getName(),
                    matches.getPlayer2().getName(),
                    matches.getWinner().getName()
            );

            finishedMatchDtoList.add(finishedMatchDto);
        }

        return finishedMatchDtoList;

    }
}
