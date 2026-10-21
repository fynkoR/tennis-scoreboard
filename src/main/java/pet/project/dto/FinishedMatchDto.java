package pet.project.dto;

public class FinishedMatchDto {
    private String firstPlayerName;
    private String secondPlayerName;
    private String winnerName;

    public FinishedMatchDto(){}

    public FinishedMatchDto(String firstPlayerName, String secondPlayerName, String winnerName) {
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.winnerName = winnerName;
    }

    public String getFirstPlayerName() {
        return firstPlayerName;
    }

    public void setFirstPlayerName(String firstPlayerName) {
        this.firstPlayerName = firstPlayerName;
    }

    public String getSecondPlayerName() {
        return secondPlayerName;
    }

    public void setSecondPlayerName(String secondPlayerName) {
        this.secondPlayerName = secondPlayerName;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }
}
