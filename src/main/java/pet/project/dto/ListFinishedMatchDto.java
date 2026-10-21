package pet.project.dto;

import java.util.List;

public class ListFinishedMatchDto {
    private List<FinishedMatchDto> list;
    private int currentPage;
    private long totalPage;

    public ListFinishedMatchDto() {}

    public ListFinishedMatchDto(List<FinishedMatchDto> list, int currentPage, long totalPage) {
        this.list = list;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }

    public List<FinishedMatchDto> getList() {
        return list;
    }

    public void setList(List<FinishedMatchDto> list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }
}
