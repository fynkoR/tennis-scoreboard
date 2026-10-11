package pet.project.exceptions;

public class MatchIsNotCompletedException extends RuntimeException {
    public MatchIsNotCompletedException() {
        super("Match is not completed !");
    }
}
