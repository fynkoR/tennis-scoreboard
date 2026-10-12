package pet.project.enums;

import pet.project.exceptions.NextPointAfterAdvantageException;

public enum Point {
    LOVE,
    FIFTEEN,
    THIRTY,
    FORTY,
    ADVANTAGE;

    public Point next(){
        return switch (this) {
            case LOVE -> FIFTEEN;
            case FIFTEEN -> THIRTY;
            case THIRTY -> FORTY;
            case FORTY -> ADVANTAGE;
            default -> throw new NextPointAfterAdvantageException(this);
        };
    }
}
