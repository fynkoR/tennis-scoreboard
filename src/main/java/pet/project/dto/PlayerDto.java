package pet.project.dto;

import pet.project.enums.Point;

public class PlayerDto {

    private String name;
    private Point points;
    private int games;
    private int sets;
    private int tieBreakPoints;

    public PlayerDto(){}

    public PlayerDto(String name, Point points, int games, int sets, int tieBreakPoints) {
        this.name = name;
        this.points = points;
        this.games = games;
        this.sets = sets;
        this.tieBreakPoints = tieBreakPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public Point getPoints() {
        return points;
    }

    public void setPoints(Point points) {
        this.points = points;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getTieBreakPoints() {
        return tieBreakPoints;
    }

    public void setTieBreakPoints(int tieBreakPoints) {
        this.tieBreakPoints = tieBreakPoints;
    }
}
