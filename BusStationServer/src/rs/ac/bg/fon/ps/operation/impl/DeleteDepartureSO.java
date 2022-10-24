/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.impl;

import rs.ac.bg.fon.ps.communication.exchangeable.TransferObject;
import rs.ac.bg.fon.ps.domain.Departure;
import rs.ac.bg.fon.ps.domain.GeneralDObject;
import rs.ac.bg.fon.ps.exception.constraint.ConstraintException;
import rs.ac.bg.fon.ps.operation.AbstractSO;
import rs.ac.bg.fon.ps.util.constant.State;

/**
 *
 * @author Vladimir Jecić
 */
public class DeleteDepartureSO extends AbstractSO{

    public DeleteDepartureSO(TransferObject tObj) {
        super(tObj);
    }

    @Override
    protected boolean preconditions() throws Exception {
        try {
            executePreconditions(tObj.getGdo());
            Departure departure = (Departure) tObj.getGdo();
            if (!departure.getState().equals(State.REMOVED)) {
                throw new ConstraintException(departure.message11());
            }
            return true;
        } catch (ConstraintException cex) {
            StringBuilder sb = new StringBuilder();
            sb.append(new Departure().message15()).append(":\nPreconditions not satisfied:")
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
        Departure departure = (Departure) tObj.getGdo();
        try{
            signal = repository.deleteRecord(departure);
        } catch (Exception e) {
            signal = false;
            throw e;
        } finally {
            tObj.setSignal(signal);
            if (signal) {
                tObj.setMessage(departure.message14());
            } else {
                tObj.setMessage(departure.message15());
            }
        }
    }

    @Override
    protected boolean postconditions() throws Exception {
        try {
            GeneralDObject gdo = tObj.getGdo();
            executePostconditions(gdo);
            return true;
        } catch (ConstraintException cex) {
            StringBuilder sb = new StringBuilder();
             sb.append(new Departure().message15()).append(":\nPostconditions not satisfied:")
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
