/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.exceptions;

/**
 *
 * @author alber
 */
public class ModalitatException extends RuntimeException {

    public ModalitatException() {
    }

    public ModalitatException(String message) {
        super(message);
    }

    public ModalitatException(String message, Throwable cause) {
        super(message, cause);
    }
}
