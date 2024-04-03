package edu.java.bot.exception;

public class ServerUnavaliableError extends RuntimeException {
    public ServerUnavaliableError(String serverError) {
        super(serverError);
    }
}
