package pet.project.entities;

import jakarta.persistence.*;

@Entity
@Table(
        name = "Matches",
        check = {
                @CheckConstraint(
                        name = "chk_player1_player2_different",
                        constraint = "player1_id <> player2_id"
                ),
                @CheckConstraint(
                        name = "chk_winner_is_players",
                        constraint = "winner_id = player1_id OR winner_id = player2_id"
                )
        }
)
public class Matches{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player1_id", nullable = false)
    private Player player1;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player2_id", nullable = false)
    private Player player2;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "winner_id", nullable = false)
    private Player winner;

    protected Matches(){}

    public Matches(Player player1, Player player2, Player winner){
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }

    public int getId() {
        return id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getWinner() {
        return winner;
    }
}