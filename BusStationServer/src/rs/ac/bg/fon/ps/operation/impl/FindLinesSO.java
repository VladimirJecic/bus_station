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
import rs.ac.bg.fon.ps.domain.Line;
import rs.ac.bg.fon.ps.domain.Stop;
import rs.ac.bg.fon.ps.operation.AbstractSO;

/**
 *
 * @author Vladimir Jecić
 */
public class FindLinesSO extends AbstractSO {

    public FindLinesSO(TransferObject tObj) {
        super(tObj);
    }

    @Override
    protected boolean preconditions() throws Exception {
        return true;
    }

    @Override
    protected void executeSO() throws Exception {
        Line exampleLine = new Line();
        List<Line> lineList = new ArrayList<>();
        String where = tObj.getWhere();
        boolean signal;
        try {
            lineList = repository.findRecords(exampleLine, lineList, where);
            for (Line line : lineList) {
                Stop exampleStop = new Stop(line, null);
                List<Stop> stopList = new ArrayList();
                stopList = repository.findRecords(exampleStop, stopList, exampleStop.getStopsForLine_Where());
                line.setStops(stopList);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            signal = !lineList.isEmpty();
            tObj.setSignal(signal);
            if (signal) {
                tObj.setListGdo((List<GeneralDObject>)(List<?>)lineList);
                tObj.setMessage(exampleLine.message21());
            } else {
                tObj.setMessage(exampleLine.message22());
            }
        }
    }

    @Override
    protected boolean postconditions() throws Exception {
        return true;
    }
}
