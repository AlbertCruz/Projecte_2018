/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.exceptions;

/**
 *
 * @author ALBER
 */
public class PartidaException extends RuntimeException {

    public PartidaException(String message) {
        super(message);
    }

    public PartidaException(String message, Throwable cause) {
        super(message, cause);
    }
    
}