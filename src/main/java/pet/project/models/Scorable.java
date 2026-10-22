package pet.project.models;

import pet.project.enums.PlayerPosition;

public interface Scorable {
    void addPointTo(PlayerPosition playerPosition);
    boolean isFinished();
}
