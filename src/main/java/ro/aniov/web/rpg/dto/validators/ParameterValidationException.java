package ro.aniov.web.rpg.dto.validators;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Created by Marius on 1/27/2017.
 */
/** We need it to handle our @RequestParam validation errors in Controllers*/
@ControllerAdvice
public class ParameterValidationException {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleValidationException(ConstraintViolationException e){

        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()){

            System.out.println(constraintViolation.getInvalidValue() + " " + constraintViolation.getMessage());
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
