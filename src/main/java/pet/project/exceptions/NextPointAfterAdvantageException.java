package pet.project.exceptions;

import pet.project.enums.Point;

public class NextPointAfterAdvantageException extends RuntimeException{
    public NextPointAfterAdvantageException(Point point) {
        super("No next score after +" + point);
    }
}
