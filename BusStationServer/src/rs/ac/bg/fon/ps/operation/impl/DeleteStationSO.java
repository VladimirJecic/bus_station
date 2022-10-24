/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.impl;

import rs.ac.bg.fon.ps.communication.exchangeable.TransferObject;
import rs.ac.bg.fon.ps.domain.Station;
import rs.ac.bg.fon.ps.exception.constraint.ConstraintException;
import rs.ac.bg.fon.ps.operation.AbstractSO;
import rs.ac.bg.fon.ps.util.constant.State;

/**
 *
 * @author Vladimir Jecić
 */
public class DeleteStationSO extends AbstractSO {

    public DeleteStationSO(TransferObject tObj) {
        super(tObj);
    }

    @Override
    protected boolean preconditions() throws Exception {
        try {
            executePreconditions(tObj.getGdo());
            Station station = (Station) tObj.getGdo();
            if (!station.getState().equals(State.REMOVED)) {
                throw new ConstraintException(station.message17());
            }
            return true;
        } catch (ConstraintException cex) {
            StringBuilder sb = new StringBuilder();
           sb.append(new Station().message11()).append(":\nPreconditions not satisfied:")
                    .append(cex.getClass().getSimpleName()).append("\n").append(cex.getMessage());
            tObj.setMessage(sb.toString());
            return false;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    protected void executeSO() throws Exception {
        boolean signal = false;
        Station station = (Station) tObj.getGdo();
        try {
            signal = repository.deleteRecord(station);
        } catch (Exception e) {
            throw e;
        } finally {
            tObj.setSignal(signal);
            if (signal) {
                tObj.setMessage(station.message10());
            } else {
                tObj.setMessage(station.message11());
            }
        }

    }

    @Override
    protected boolean postconditions() throws Exception {
        try {
            executePostconditions(tObj.getGdo());
            return true;
        } catch (ConstraintException cex) {
            StringBuilder sb = new StringBuilder();
            sb.append(new Station().message11()).append(":\nPostconditions not satisfied:")
                    .append(cex.getClass().getSimpleName()).append("\n").append(cex.getMessage());
            tObj.setMessage(sb.toString());
            tObj.setSignal(false);
            return false;
        } catch (Exception ex) {
            tObj.setSignal(false);
            throw ex;
        }
    }

}
