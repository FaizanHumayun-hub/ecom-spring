package com.example.ecom.advice;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NoSuchElementException.class,DataIntegrityViolationException.class,RuntimeException.class})
    public Map<String, String> handleNoSuchElementException(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error_message", ex.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoResourceFoundException.class)
    public Map<String, String> handleNoResourceFoundException(NoResourceFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error_message", "This resource does not exist");
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error_message", "Invalid input");
        return errors;
    }


//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ExceptionHandler(AccessDeniedException.class)
//    public ProblemDetail accessDeniedProblem(AccessDeniedException ex) {
//        ProblemDetail errorDetail = ProblemDetail
//                .forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
//        errorDetail.setProperty("access_denied_reason","Not authorized");
//        return errorDetail;
//    }
//
////    @ResponseStatus(HttpStatus.FORBIDDEN)
////    @ExceptionHandler(ExpiredJwtException.class)
////    public ProblemDetail handleExpiredJwtException(ExpiredJwtException ex) {
////        ProblemDetail errorDetail = ProblemDetail
////                .forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
////        errorDetail.setProperty("access_denied_reason","Expired Token");
////        return errorDetail;
////    }
//@ResponseStatus(HttpStatus.FORBIDDEN)
//@ExceptionHandler(SignatureException.class)
//public Map<String, String> handleExpiredJwtException(SignatureException ex) {
//    Map<String, String> errors = new HashMap<>();
//    errors.put("error_message", "Token has expired");
//    return errors;
//}

}
