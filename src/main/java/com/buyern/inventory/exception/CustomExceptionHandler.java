package com.buyern.inventory.exception;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.LimitExceededException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice(basePackages = {"com.buyern.inventory.Controllers"})
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest webRequest) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        logger.error("Exception stacktrace: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse("91", "Error!", details), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public final ResponseEntity<Object> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex, WebRequest webRequest) {

        logger.error("EntityAlreadyExistsException stacktrace: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse("04", ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest webRequest) {

        logger.error("RecordNotFoundException stacktrace: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse("05", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LimitReachedException.class)
    public final ResponseEntity<Object> handleLimitReachedException(LimitReachedException ex, WebRequest webRequest) {

        logger.error("LimitReachedException stacktrace: {}", ex.getMessage());
        return new ResponseEntity<Object>(new ErrorResponse("06", ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest webRequest) {

        logger.error("BadRequestException stacktrace: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse("07", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        logger.error("MethodArgumentNotValidException stacktrace: {}", ex.getMessage());

        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        return new ResponseEntity<>(new ErrorResponse("03", "Bad Request", details), HttpStatus.BAD_REQUEST);
    }

}
