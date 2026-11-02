package pet.project.exceptions;

public class SetIsAlreadyCompletedException extends RuntimeException {
    public SetIsAlreadyCompletedException() {
        super("TennisSet is already completed !");
    }
}
