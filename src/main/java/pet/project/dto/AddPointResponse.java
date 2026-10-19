package pet.project.dto;

public class AddPointResponse {
    private PlayerDto firstPlayer;
    private PlayerDto secondPlayer;
    private String winnerName;

    public AddPointResponse(){}

    public AddPointResponse(PlayerDto firstPlayer, PlayerDto secondPlayer, String winnerName) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.winnerName = winnerName;
    }

    public PlayerDto getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(PlayerDto firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public PlayerDto getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(PlayerDto secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }
}
