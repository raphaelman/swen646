package swen646.edwenson.exception;

public class DuplicateObjectException extends RuntimeException{
    public DuplicateObjectException() {
    }

    public DuplicateObjectException(String message) {
        super(message);
    }
}
