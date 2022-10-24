/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.util.constant;

import rs.ac.bg.fon.ps.communication.exchangeable.TransferObject;
import rs.ac.bg.fon.ps.exception.constraint.SOException;


/**
 *
 * @author Vladimir Jecić
 */
public interface SystemOperations {

    public static final int LOGIN = 1;
    public static final int SAVE_STATION = 2;
    public static final int GET_STATION_LIST = 3;
    public static final int FIND_STATIONS = 4;
    public static final int DELETE_STATION = 5;
    public static final int SAVE_LINE = 6;
    public static final int GET_LINE_LIST = 7;
    public static final int FIND_LINES = 8;
    public static final int GET_DEPARTURES_FOR_LINE = 9;
    public static final int DELETE_LINE = 10;
    public static final int SAVE_DEPARTURES = 11;
    public static final int DELETE_DEPARTURE = 12;
    public static final int GET_TICKETS_FOR_DEPARTURE = 13;
    public static final int SAVE_TICKET = 14;
    public static final int DELETE_TICKET = 15;

    public static String getName(int operation) throws Exception {
        switch (operation) {
            case 1:
                return "LOGIN";
            case 2:
                return "SAVE STATION";
            case 3:
                return "GET_STATION_LIST";
            case 4:
                return "FIND_STATIONS";
            case 5:
                return "DELETE_STATION";
            case 6:
                return "SAVE_LINE";
            case 7:
                return "GET_LINE_LIST";
            case 8:
                return "FIND_LINES";
            case 9:
                return "GET_DEPARTURES_FOR_LINE";
            case 10:
                return "DELETE_LINE";
            case 11:
                return "SAVE_DEPARTURES";
            case 12:
                return "DELETE_DEPARTURE";
            case 13:
                return "GET_TICKETS_FOR_DEPARTURE";
            case 14:
                return "SAVE_TICKET";
            case 15:
                return "DELETE_TICKET";
            default:
                throw new Exception("Undefined SystemOperation:" + operation);
        }
    }
    abstract public void loginEmployee(TransferObject tObj) throws SOException;//1
    abstract public void saveStation(TransferObject tObj) throws SOException;//2
    abstract public void getStationList(TransferObject tObj) throws SOException;//3
    abstract public void findStations(TransferObject tObj) throws SOException;//4
    abstract public void deleteStation(TransferObject tObj) throws SOException;//5
    abstract public void saveLine(TransferObject tObj) throws SOException;//6
    abstract public void getLineList(TransferObject tObj) throws SOException;//7
    abstract public void findLines(TransferObject tObj) throws SOException;//8
    abstract public void getDeparturesForLine(TransferObject tObj) throws SOException;//9
    abstract public void deleteLine(TransferObject tObj) throws SOException;//10
    abstract public void saveDepartures(TransferObject tObj) throws SOException;//11
    abstract public void deleteDeparture(TransferObject tObj) throws SOException;//12
    abstract public void getTicketsForDeparture(TransferObject tObj) throws SOException;//13
    abstract public void saveTicket(TransferObject tObj) throws SOException;//14
    abstract public void deleteTicket(TransferObject tObj) throws SOException;//15

}
