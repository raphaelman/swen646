package swen646.edwenson.exception;

public class IllegalLoadException extends RuntimeException{
    public IllegalLoadException() {
    }

    public IllegalLoadException(String message) {
        super(message);
    }

    public IllegalLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
