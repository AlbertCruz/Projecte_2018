/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.exceptions;

/**
 *
 * @author Sergio
 */
public class TorneigException extends RuntimeException {

    public TorneigException(String message) {
        super(message);
    }

    public TorneigException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
