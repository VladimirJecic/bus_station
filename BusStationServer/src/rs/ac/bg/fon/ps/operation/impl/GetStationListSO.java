/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.impl;

import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.communication.exchangeable.TransferObject;
import rs.ac.bg.fon.ps.domain.GeneralDObject;
import rs.ac.bg.fon.ps.domain.Station;
import rs.ac.bg.fon.ps.operation.AbstractSO;

/**
 *
 * @author Vladimir Jecić
 */
public class GetStationListSO extends AbstractSO {

    public GetStationListSO(TransferObject tObj) {
        super(tObj);
    }
    @Override
    protected boolean preconditions() throws Exception {return true;}
    
    @Override
    protected void executeSO() throws Exception {
        Station station = new Station();
        boolean signal = true;
        List<Station> stationList = new ArrayList<>();
        try {
            stationList = repository.findAllRecords(station, stationList);
        } catch (Exception e) {
            signal = false;
            throw e;
        } finally {
            tObj.setSignal(signal);
            if (signal) {
                tObj.setListGdo((List<GeneralDObject>)(List<?>)stationList);
                tObj.setMessage(station.message7());
            } else {
                tObj.setMessage(station.message8());
            }
        }
    }

    @Override
    protected boolean postconditions() throws Exception {return true;}

}
