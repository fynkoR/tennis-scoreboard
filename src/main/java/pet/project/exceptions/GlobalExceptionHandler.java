package pet.project.exceptions;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pet.project.dto.ErrorResponse;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SameNamePlayersException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(SameNamePlayersException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(MatchNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundMatchException(MatchNotFoundException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(MatchIsAlreadyCompletedException.class)
    public ResponseEntity<ErrorResponse> handleMatchIsAlreadyCompletedException(MatchIsAlreadyCompletedException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(PlayerNotFoundByNameException.class)
    public ResponseEntity<ErrorResponse> handlePlayerNotFoundByNameException(PlayerNotFoundByNameException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception){

        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));

        ErrorResponse errorResponse = new ErrorResponse(errorMessage);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlePersistenceException(Exception exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

}
