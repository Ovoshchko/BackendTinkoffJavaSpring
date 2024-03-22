package edu.java.scrapper.exception;

import edu.java.scrapper.dto.response.ApiErrorResponse;
import java.util.MissingFormatArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {
        NoResourceFoundException.class,
        MissingFormatArgumentException.class,
        IllegalArgumentException.class,
        MissingServletRequestParameterException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse badRequestError(Exception e) {
        return new ApiErrorResponse(
            "Некорректные параметры запроса",
            String.valueOf(HttpStatus.BAD_REQUEST.value())
        );
    }

    @ExceptionHandler(value = {NotFoundException.class}) //сделать класс ошибки
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse notFoundError(Exception e) {
        return new ApiErrorResponse(
            e.getMessage(),
            String.valueOf(HttpStatus.NOT_FOUND.value())
        );
    }

    @ExceptionHandler(value = {AlreadyExistsException.class}) //сделать класс ошибки
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse alreadyExistsError(Exception e) {
        return new ApiErrorResponse(
            e.getMessage(),
            String.valueOf(HttpStatus.CONFLICT.value())
        );
    }

}
