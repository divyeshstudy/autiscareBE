package com.api.autiscare.exception;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionMessage> notFoundException(NotFoundException exception){
        ExceptionMessage exceptionMessage = new ExceptionMessage(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionMessage);
    }

    @ExceptionHandler(DataConstraintVoilationException.class)
    public ResponseEntity<ExceptionMessage> dataConstraintVoilationException(DataConstraintVoilationException exception){
        ExceptionMessage exceptionMessage = new ExceptionMessage(HttpStatus.BAD_REQUEST,exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionMessage);
    }

    @ExceptionHandler(UnknownException.class)
    public ResponseEntity<ExceptionMessage> unknownException(UnknownException exception){
        ExceptionMessage exceptionMessage = new ExceptionMessage(HttpStatus.BAD_REQUEST,exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionMessage);
    }

    @ExceptionHandler(DuplicateRecordsException.class)
    public ResponseEntity<ExceptionMessage> duplicateRecordsException(DuplicateRecordsException exception){
        ExceptionMessage exceptionMessage = new ExceptionMessage(HttpStatus.CONFLICT,exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionMessage);
    }
}
