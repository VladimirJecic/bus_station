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
public enum MapParams {
    CURRENT_USER, CURRENT_LINE, CURRENT_DEPARTURE,
    CURRENT_USE_CASE,CLIENT_LIST;
    
    @Override
    public String toString() {
        return this.name();
    }
}
