package com.ortech.bookstore.order.order_service.exception;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InValidItemException.class)
    public ProblemDetail handleInValidItemException(InValidItemException inValidItemException)
    {
        ProblemDetail problemDetail=ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,inValidItemException.getLocalizedMessage());
        problemDetail.setType(URI.create("https://api.sivalabs-bookstore.com/errors/not-found"));
        problemDetail.setTitle("InValid Order Details");
        problemDetail.setProperty("Time Stamp", Instant.now());
        problemDetail.setProperty("service", "order-service");
        problemDetail.setProperty("error_category", "Medium");
        return problemDetail;

    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ProblemDetail handleOrderNotFoundException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("https://api.sivalabs-bookstore.com/errors/not-found"));
        problemDetail.setTitle("Order Details Not Found");
        problemDetail.setProperty("Time Stamp", Instant.now());
        problemDetail.setProperty("service", "order-service");
        problemDetail.setProperty("error_category", "Medium");
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errorMessages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errorMessages.add(errorMessage);
        });
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage() );
        problemDetail.setType(URI.create("https://api.sivalabs-bookstore.com/errors/bad-data"));
        problemDetail.setTitle("Invalid request Body");
        problemDetail.setProperty("errors", errorMessages);
        problemDetail.setProperty("Service", "order-service");
        problemDetail.setProperty("error_category", "Generic");
        problemDetail.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnknownException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setType(URI.create("https://api.sivalabs-bookstore.com/errors/internal-server-error"));
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setProperty("Time Stamp", Instant.now());
        problemDetail.setProperty("service", "order-service");
        problemDetail.setProperty("error_category", "Medium");
        return problemDetail;
    }
}
