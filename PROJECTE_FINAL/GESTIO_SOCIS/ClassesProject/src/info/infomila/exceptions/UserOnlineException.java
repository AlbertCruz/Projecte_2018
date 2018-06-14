/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.exceptions;

/**
 *
 * @author Mr. Robot
 */
public class UserOnlineException extends RuntimeException {

    public UserOnlineException() {
    }

    public UserOnlineException(String message) {
        super(message);
    }

    public UserOnlineException(String message, Throwable cause) {
        super(message, cause);
    }

    
    
}
