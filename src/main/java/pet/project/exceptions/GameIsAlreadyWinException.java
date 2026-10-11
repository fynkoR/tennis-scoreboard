package pet.project.exceptions;

public class GameIsAlreadyWinException extends RuntimeException{
    public GameIsAlreadyWinException() {
        super("Game is already win !");
    }
}
