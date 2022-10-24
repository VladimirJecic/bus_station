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
public class ConstraintException extends SOException{

    public ConstraintException() {
    }

    public ConstraintException(String message) {
        super(message);
    }

    public ConstraintException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConstraintException(Throwable cause) {
        super(cause);
    }
    

    
    
    
}
