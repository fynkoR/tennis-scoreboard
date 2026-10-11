package pet.project;

import pet.project.enums.Point;
import pet.project.models.*;

public class Main {
    public static void main(String[] args) {
        // Проверка Game

        /*Game game = new Game();
        game.showScore();
        System.out.println(" ");
        game.addPointTo(false);
        game.addPointTo(false);
        game.addPointTo(false);
        game.addPointTo(true);
        game.addPointTo(true);
        game.addPointTo(true);
        game.addPointTo(false);
        game.addPointTo(false);
        game.showScore();
        System.out.println(game.isFinished());*/

        // Проверка Set

        Set set = new Set();
        for(int i = 0; i < 4*5; i++){
            set.addPointTo(true);
        }
        for(int i = 0; i < 4*5; i++){
            set.addPointTo(false);
        }
        for(int i = 0; i < 4; i++){
            set.addPointTo(true);
        }
        for(int i = 0; i < 4; i++){
            set.addPointTo(false);
        }

        set.showScore();

        for(int i = 0; i < 6; i++){
            set.addPointTo(true);
        }
        for(int i = 0; i < 6; i++){
            set.addPointTo(false);
        }
        set.addPointTo(true);
        set.addPointTo(false);

        set.addPointTo(true);
        set.addPointTo(true);
        set.getCurrentGame().showScore();

        set.showScore();

        System.out.println(set.getCurrentGame().isFinished());

        System.out.println(" ");
        System.out.println("Проверка match");

        Match match = new Match(new TennisPlayer("firstPlayer"), new TennisPlayer("secondPlayer"));
        String firstPlayer = "firstPlayer";
        String secondPlayer = "secondPlayer";
        doSet(match, firstPlayer, secondPlayer);
        match.getSet().showScore();
        doSet(match, firstPlayer, secondPlayer);
        System.out.println(match.getCounterFirstPlayer());
        System.out.println(" ");
        System.out.println(match.getWinner());

    }

    public static void doSet(Match match, String firstPlayer, String secondPlayer){
        for(int i = 0; i < 4*5; i++){
            match.addPointTo(firstPlayer);
        }
        for(int i = 0; i < 4*5; i++){
            match.addPointTo(secondPlayer);
        }
        for(int i = 0; i < 4; i++){
            match.addPointTo(firstPlayer);
        }
        for(int i = 0; i < 4; i++){
            match.addPointTo(firstPlayer);
        }
    }
}
