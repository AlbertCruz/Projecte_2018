
package info.infomila.appbolos.models.exceptions;

public class TorneigException extends RuntimeException {

    public TorneigException(String message) {
        super(message);
    }

    public TorneigException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
