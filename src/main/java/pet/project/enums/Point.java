package pet.project.enums;

import pet.project.exceptions.NextPointAfterAdvantageException;

public enum Point {
    LOVE,
    FIFTEEN,
    THIRTY,
    FORTY,
    ADVANTAGE;

    public Point next(){
        switch (this){
            case LOVE:
                return FIFTEEN;
            case FIFTEEN:
                return THIRTY;
            case THIRTY:
                return FORTY;
            case FORTY:
                return ADVANTAGE;
            default:
                throw new NextPointAfterAdvantageException(this);
        }
    }
}
