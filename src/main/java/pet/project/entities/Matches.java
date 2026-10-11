package pet.project.entities;

public class Matches {
    private int id;
    private Player player1;
    private Player player2;
    private Player Winner;

    public Matches(int id, Player player2, Player player1, Player winner) {
        this.id = id;
        this.player2 = player2;
        this.player1 = player1;
        Winner = winner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getWinner() {
        return Winner;
    }

    public void setWinner(Player winner) {
        Winner = winner;
    }
}
