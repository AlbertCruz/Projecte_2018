package info.infomila.appbolos.models.exceptions;

public class ModalitatException extends RuntimeException {

    public ModalitatException()  {
    }

    public ModalitatException(String message) {
        super(message);
    }

    public ModalitatException(String message, Throwable cause) {
        super(message, cause);
    }

}