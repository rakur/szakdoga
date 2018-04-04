package hu.unideb.inf.szakdoga.exceptions;

public class InvalidPlacingPositionException extends Exception {
    private static final String DEFAULT_ERROR_MESSAGE ="Unable to place at current position";

    public InvalidPlacingPositionException() {
        super (DEFAULT_ERROR_MESSAGE);
    }
}
