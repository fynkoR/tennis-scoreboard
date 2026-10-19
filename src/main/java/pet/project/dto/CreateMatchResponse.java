package pet.project.dto;

import java.util.UUID;

public class CreateMatchResponse {
    private UUID id;

    public CreateMatchResponse(UUID id){
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
