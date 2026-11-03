package pet.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateMatchRequest(
        @NotBlank(message = "First player name should be not empty")
        @Size(min = 3, max = 20, message = "First player name should be between 3 and 20 characters")
        @Pattern(regexp = "^[\\p{L}\\d][\\p{L}\\d.()_%$?#@^&*\\-]{2,19}$")
        String firstPlayerName,

        @NotBlank(message = "Second player name should be not empty")
        @Size(min = 3, max = 20, message = "Second player name should be between 3 and 20 characters")
        @Pattern(regexp = "^[\\p{L}\\d][\\p{L}\\d.()_%$?#@^&*\\-]{2,19}$")
        String secondPlayerName
) {}
