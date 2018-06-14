package info.infomila.appbolos.models.exceptions;


public class GrupException extends RuntimeException {

    public GrupException(String message) {
        super(message);
    }

    public GrupException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
