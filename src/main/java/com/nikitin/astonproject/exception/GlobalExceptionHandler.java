package com.nikitin.astonproject.exception;

import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  @NonNull HttpHeaders headers,
                                                                  HttpStatusCode statusCode,
                                                                  WebRequest request) {
        var body = ExceptionResponseDto.builder()
                .status(String.valueOf(statusCode.value()))
                .message(exception.getBindingResult().getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.joining("; ")))
                .path(request.getContextPath())
                .build();

        return handleExceptionInternal(exception, body, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception exception, Object body,
                                                             @NonNull HttpHeaders headers,
                                                             @NonNull HttpStatusCode statusCode,
                                                             @NonNull WebRequest request) {
        if (Objects.isNull(body)) {
            return ResponseEntity.status(statusCode.value())
                    .body(ExceptionResponseDto.builder()
                            .status(String.valueOf(statusCode.value()))
                            .message(exception.getMessage())
                            .path(request.getContextPath())
                            .build()
                    );
        }

        return ResponseEntity.status(statusCode.value())
                .body(body);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ExceptionResponseDto> handleBadRequestException(Exception exception, WebRequest request) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus.value())
                .body(ExceptionResponseDto.builder()
                        .status(String.valueOf(httpStatus.value()))
                        .message(exception.getMessage())
                        .path(request.getContextPath())
                        .build()
                );
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleNotFoundException(Exception exception, WebRequest request) {
        var httpStatus = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(httpStatus.value())
                .body(ExceptionResponseDto.builder()
                        .status(String.valueOf(httpStatus.value()))
                        .message(exception.getMessage())
                        .path(request.getContextPath())
                        .build()
                );
    }
}
