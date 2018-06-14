package info.infomila.appbolos.models.exceptions;


public class SociException extends RuntimeException {

    public SociException() {
    }

    public SociException(String message) {
        super(message);
    }

    public SociException(String message, Throwable cause) {
        super(message, cause);
    }

}