package edu.java.scrapper.exception;

public class ServerUnavaliableError extends RuntimeException {
    public ServerUnavaliableError(String serverError) {
        super(serverError);
    }
}
