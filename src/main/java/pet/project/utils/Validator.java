package pet.project.utils;

import pet.project.exceptions.ValidationException;

public class Validator {
    public static Integer validateIntegerPage(Integer page){
        if(page == null){
            return 0;
        }
        else if(page >= 0){
            return page;
        }
        else{
            throw new ValidationException("No valid value page !");
        }
    }
}
