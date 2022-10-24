/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.exception.constraint;

/**
 *
 * @author Vladimir Jecić
 */
public class SOException extends Exception{

    public SOException() {
    }

    public SOException(String message) {
        super(message);
    }

    public SOException(String message, Throwable cause) {
        super(message, cause);
    }

    public SOException(Throwable cause) {
        super(cause);
    }
    
}
