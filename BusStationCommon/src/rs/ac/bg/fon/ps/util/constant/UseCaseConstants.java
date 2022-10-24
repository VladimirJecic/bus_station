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
public enum UseCaseConstants {
    LOGIN_USER("Login User",1),
    NEW_STATION("New Station",2),
    DELETE_STATION("Delete Station",3),
    NEW_LINE("New Line",4),
    EDIT_LINE("Edit Line",5),
    DELETE_LINE("Delete Line",6),
    NEW_DEPARTURE("New Departure on Line",7),
    DELETE_DEPARTURE("Delete Departure from Line",8),
    NEW_TICKET("New Ticket",9),
    DELETE_TICKET("Delete Ticket",10);
    private String name;
    private int number;
    private UseCaseConstants(String name,int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }
    
//    public int toInt(){
//        return number;
//    }
    
//    @Override
//    public String toString() {
//        return name;
//    }
    
}
