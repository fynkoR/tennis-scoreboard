package pet.project.dto;

import java.util.List;

public record ListFinishedMatchDto(List<FinishedMatchDto> matches, int currentPage, long totalPages) {

}
