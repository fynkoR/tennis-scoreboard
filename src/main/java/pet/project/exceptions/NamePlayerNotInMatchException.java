package pet.project.exceptions;

public class NamePlayerNotInMatchException extends RuntimeException {
    public NamePlayerNotInMatchException(String namePlayer) {
        super("Name: " + namePlayer + " not in match !");
    }
}
