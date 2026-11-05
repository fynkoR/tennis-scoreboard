package pet.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddPointRequest(
        @NotBlank(message = "Name player should be not empty")
        @Size(min = 3, max = 20, message = "First player name should be between 3 and 20 characters")
        @Pattern(
                regexp = "^[\\p{L}\\d][\\p{L}\\d.()_%$?#@^&*\\-]{2,19}$",
                message = "Invalid first player name: 3-20 chars, must start with letter/digit"
        )
        String name
) {
}
