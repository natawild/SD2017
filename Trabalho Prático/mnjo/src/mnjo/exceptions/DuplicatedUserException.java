/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.exceptions;

/**
 *
 * @author celia
 */
public class DuplicatedUserException extends Exception {
    public DuplicatedUserException() {
        super();
    }

    public DuplicatedUserException(String message) {
        super(message);
    }
      
}
