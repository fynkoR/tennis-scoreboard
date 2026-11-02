package pet.project.exceptions;

public class TieBreakIsAlreadyWinException extends RuntimeException {
    public TieBreakIsAlreadyWinException() {
        super("Tie break is already win !");
    }
}
