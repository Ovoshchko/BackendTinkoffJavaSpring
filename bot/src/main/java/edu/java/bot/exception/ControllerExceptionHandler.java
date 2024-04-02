package edu.java.bot.exception;

import edu.java.bot.dto.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse badRequestError(Exception e) {
        return new ApiErrorResponse(
            "Некорректные параметры запроса",
            String.valueOf(HttpStatus.BAD_REQUEST.value())
        );
    }

    @ExceptionHandler(value = {ServerUnavaliableError.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse serverError(Exception e) {
        return new ApiErrorResponse(
            e.getMessage(),
            String.valueOf(INTERNAL_SERVER_ERROR)
        );
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ApiErrorResponse tooManyRequests(Exception e) {
        return new ApiErrorResponse(
            e.getMessage(),
            String.valueOf(HttpStatus.TOO_MANY_REQUESTS.value())
        );
    }

}
