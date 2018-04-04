package hu.unideb.inf.szakdoga.exceptions;

public class InvalidShootingPositionException extends Exception{
    private static final String DEFAULT_ERROR_MESSAGE ="Unable to shoot at current position";

    public InvalidShootingPositionException() {
        super (DEFAULT_ERROR_MESSAGE);
    }
}
