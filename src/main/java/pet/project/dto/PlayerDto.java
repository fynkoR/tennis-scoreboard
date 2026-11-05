package pet.project.dto;

import pet.project.enums.Point;

public record PlayerDto(String name, String points, int games, int sets, Integer tieBreakPoints) {
}
