package pet.project.exceptions;

public class SetIsAlreadyCompletedException extends RuntimeException {
    public SetIsAlreadyCompletedException() {
        super("Set is already completed !");
    }
}
