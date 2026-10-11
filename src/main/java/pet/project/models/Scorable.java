package pet.project.models;

public interface Scorable {
    void addPointTo(boolean isFirstPlayer);
    boolean isFinished();
    void showScore();
}
