package pet.project.exceptions;

public class SameNamePlayersException extends RuntimeException {
    public SameNamePlayersException(String firstName, String secondName) {
        super(firstName + " and " + secondName + " : identical names");
    }
}
