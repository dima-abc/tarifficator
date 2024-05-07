package com.test2.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.ConnectException;
import java.util.Locale;
import java.util.Objects;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlingControllerAdvice {
    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindException(BindException exception, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST,
                        Objects.requireNonNull(this.messageSource.getMessage("errors.400.title", new Object[0],
                                "errors.400.title", locale)));
        problemDetail.setProperty("errors",
                exception.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList());
        return ResponseEntity.badRequest()
                .body(problemDetail);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ProblemDetail> handleWebClientResponseException(WebClientResponseException ex) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, "Запрос содержит ошибки.");
        problemDetail.setProperty("errors", ex.getResponseBodyAsString());
        return ResponseEntity.badRequest()
                .body(problemDetail);
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ProblemDetail> handleConnectException(ConnectException ex) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Сервис не доступен");
        problemDetail.setProperty("errors", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(problemDetail);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ProblemDetail> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Сервис не доступен");
        problemDetail.setProperty("errors", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(problemDetail);
    }
}
