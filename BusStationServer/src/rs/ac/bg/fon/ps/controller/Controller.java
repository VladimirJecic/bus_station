/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.controller;

import rs.ac.bg.fon.ps.communication.exchangeable.TransferObject;
import rs.ac.bg.fon.ps.exception.constraint.SOException;
import rs.ac.bg.fon.ps.operation.impl.DeleteDepartureSO;
import rs.ac.bg.fon.ps.operation.impl.DeleteLineSO;
import rs.ac.bg.fon.ps.operation.impl.DeleteStationSO;
import rs.ac.bg.fon.ps.operation.impl.DeleteTicketSO;
import rs.ac.bg.fon.ps.operation.impl.FindLinesSO;
import rs.ac.bg.fon.ps.operation.impl.FindStationsSO;
import rs.ac.bg.fon.ps.operation.impl.GetDeparturesForLineSO;
import rs.ac.bg.fon.ps.operation.impl.GetLineListSO;
import rs.ac.bg.fon.ps.operation.impl.GetStationListSO;
import rs.ac.bg.fon.ps.operation.impl.GetTicketsForDepartureSO;
import rs.ac.bg.fon.ps.operation.impl.LoginEmployeeSO;
import rs.ac.bg.fon.ps.operation.impl.SaveDeparturesSO;
import rs.ac.bg.fon.ps.operation.impl.SaveLineSO;
import rs.ac.bg.fon.ps.operation.impl.SaveStationSO;
import rs.ac.bg.fon.ps.operation.impl.SaveTicketSO;
import rs.ac.bg.fon.ps.util.constant.SystemOperations;

/**
 * Dispatcher, calls SO
 *
 * @author Vladimir Jecić
 */
public class Controller implements SystemOperations {

    private static Controller instance;

    private Controller() {
    }

    public synchronized static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    @Override
    public void loginEmployee(TransferObject tObj) throws SOException {
        try {
            LoginEmployeeSO so = new LoginEmployeeSO(tObj);
            so.generalExecuteSO();

        } catch (Exception e) {
            throw new SOException(e);
        }
    }//1

    @Override
    public void saveStation(TransferObject tObj) throws SOException {
        try {
            SaveStationSO so = new SaveStationSO(tObj);
            so.generalExecuteSO();
        } catch (Exception e) {
            throw new SOException(e);
        }
    }//2

    @Override
    public void getStationList(TransferObject tObj) throws SOException {
        try {
            GetStationListSO so = new GetStationListSO(tObj);
            so.generalExecuteSO();
        } catch (Exception e) {
            throw new SOException(e);
        }
    }//3

    @Override
    public void findStations(TransferObject tObj) throws SOException {
        try {
            FindStationsSO so = new FindStationsSO(tObj);
            so.generalExecuteSO();
        } catch (Exception e) {
            throw new SOException(e);
        }
    }//4

    @Override
    public void deleteStation(TransferObject tObj) throws SOException {
        try {
            DeleteStationSO so = new DeleteStationSO(tObj);
            so.generalExecuteSO();
        } catch (Exception e) {
            throw new SOException(e);
        }
    }//5

    @Override
    public void saveLine(TransferObject tObj) throws SOException {
        try {
            SaveLineSO so = new SaveLineSO(tObj);
            so.generalExecuteSO();
        } catch (Exception e) {
            throw new SOException(e);
        }
    }//6

    @Override
    public void getLineList(TransferObject tObj) throws SOException {
        try {
            GetLineListSO so = new GetLineListSO(tObj);
            so.generalExecuteSO();
        } catch (Exception e) {
            throw new SOException(e);
        }
    }//7

    @Override
    public void findLines(TransferObject tObj) throws SOException {
        try {
            FindLinesSO so = new FindLinesSO(tObj);
            so.generalExecuteSO();
        } catch (Exception e) {
            throw new SOException(e);
        }
    }//8

    @Override
    public void getDeparturesForLine(TransferObject tObj) throws SOException {
        try {
            GetDeparturesForLineSO so = new GetDeparturesForLineSO(tObj);
            so.generalExecuteSO();
        } catch (Exception e) {
            throw new SOException(e);
        }
    }//9

    @Override
    public void deleteLine(TransferObject tObj) throws SOException {
        try {
            DeleteLineSO so = new DeleteLineSO(tObj);
            so.generalExecuteSO();
        } catch (Exception e) {
            throw new SOException(e);
        }
    }//10

    @Override
    public void saveDepartures(TransferObject tObj) throws SOException {
        try {
            SaveDeparturesSO so = new SaveDeparturesSO(tObj);
            so.generalExecuteSO();
        } catch (Exception e) {
            throw new SOException(e);
        }
    }//11

    @Override
    public void deleteDeparture(TransferObject tObj) throws SOException {
        try {
            DeleteDepartureSO so = new DeleteDepartureSO(tObj);
            so.generalExecuteSO();
        } catch (Exception e) {
            throw new SOException(e);
        }
    }//12

    @Override
    public void getTicketsForDeparture(TransferObject tObj) throws SOException {
        try {
            GetTicketsForDepartureSO so = new GetTicketsForDepartureSO(tObj);
            so.generalExecuteSO();
        } catch (Exception e) {
            throw new SOException(e);
        }
    }//13

    @Override
    public void saveTicket(TransferObject tObj) throws SOException {
        try {
            SaveTicketSO so = new SaveTicketSO(tObj);
            so.generalExecuteSO();
        } catch (Exception e) {
            throw new SOException(e);
        }
    }//14

    @Override
    public void deleteTicket(TransferObject tObj) throws SOException {
        try {
            DeleteTicketSO so = new DeleteTicketSO(tObj);
            so.generalExecuteSO();
        } catch (Exception e) {
            throw new SOException(e);
        }
    }//15

}
