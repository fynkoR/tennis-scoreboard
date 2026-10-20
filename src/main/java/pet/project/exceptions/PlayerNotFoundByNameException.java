package pet.project.exceptions;

public class PlayerNotFoundByNameException extends RuntimeException {
    public PlayerNotFoundByNameException(String name) {
        super("This player with name: " + name + " not found !");
    }
}
