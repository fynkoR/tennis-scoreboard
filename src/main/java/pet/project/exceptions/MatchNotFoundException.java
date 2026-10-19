package pet.project.exceptions;

import java.util.UUID;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException(UUID uuid) {
        super("Match not found with UUID: " + uuid);
    }
}
