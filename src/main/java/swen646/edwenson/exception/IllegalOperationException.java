package swen646.edwenson.exception;

public class IllegalOperationException extends RuntimeException{
    public IllegalOperationException() {
    }

    public IllegalOperationException(String message) {
        super(message);
    }
}
