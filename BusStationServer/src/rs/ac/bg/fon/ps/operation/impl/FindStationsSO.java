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
public class FindStationsSO extends AbstractSO {

    public FindStationsSO(TransferObject tObj) {
        super(tObj);
    }

    @Override
    protected boolean preconditions() throws Exception {
        return true;
    }

    @Override
    protected void executeSO() throws Exception {
        Station station = new Station();
        List<Station> stationList = new ArrayList<>();
        String where = tObj.getWhere();
        boolean signal;
        try {
            stationList = repository.findRecords(station, stationList, where);
        } catch (Exception e) {
            throw e;
        } finally {
            signal = !stationList.isEmpty();
            tObj.setSignal(signal);
            if (signal) {
                tObj.setListGdo((List<GeneralDObject>)(List<?>)stationList);
                tObj.setMessage(station.message12());
            } else {
                tObj.setMessage(station.message13());
            }
        }
    }

    @Override
    protected boolean postconditions() throws Exception {
        return true;
    }
}
