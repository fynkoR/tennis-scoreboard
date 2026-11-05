package pet.project.models;

import java.util.Optional;

public interface Scorable {
    void addPointTo(PlayerPosition playerPosition);
    boolean isFinished();
    GameScore getGameScore();
    Optional<PlayerPosition> getWinner();
}
