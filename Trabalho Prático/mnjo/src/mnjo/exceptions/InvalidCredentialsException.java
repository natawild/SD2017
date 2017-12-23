/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.exceptions;

/**
 *
 * @author luisoliveira
 */
public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super();
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
      
}
