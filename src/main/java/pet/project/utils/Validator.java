package pet.project.utils;

import pet.project.dto.CreateMatchRequest;
import pet.project.exceptions.ValidationException;

public final class Validator {

    private Validator(){}

    public static Integer validateIntegerPage(Integer page){
        if(page >= 0) return page;
        throw new ValidationException("No valid value page !");
    }

    public static boolean checkIdenticalNames(CreateMatchRequest createMatchRequest){
        return createMatchRequest.firstPlayerName().equalsIgnoreCase(createMatchRequest.secondPlayerName());
    }
}
