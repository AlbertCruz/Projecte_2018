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
public class InscripcioException extends RuntimeException {

    public InscripcioException(String message) {
        super(message);
    }

    public InscripcioException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
