package pet.project.exceptions;

public class MatchIsAlreadyCompletedException extends RuntimeException {
    public MatchIsAlreadyCompletedException() {
        super("Match is already completed !");
    }
}
