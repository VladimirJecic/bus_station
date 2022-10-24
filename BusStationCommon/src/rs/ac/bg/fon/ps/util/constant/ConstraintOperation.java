/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.util.constant;

/**
 *
 * @author Vladimir Jecić
 */
public class ConstraintOperation {

    public static String forState(State state) throws IllegalStateException {
        switch (state) {
            case NOT_CHANGED:
                return "";
            case NEW:
                return "insert";
            case CHANGED:
                return "update";
            case REMOVED:
                return "delete";
            default:
                throw new IllegalStateException("IllegalState:" + state);

        }
    }

}
