/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.impl;

import rs.ac.bg.fon.ps.communication.exchangeable.TransferObject;
import rs.ac.bg.fon.ps.domain.GeneralDObject;
import rs.ac.bg.fon.ps.domain.Line;
import rs.ac.bg.fon.ps.exception.constraint.ConstraintException;
import rs.ac.bg.fon.ps.operation.AbstractSO;
import rs.ac.bg.fon.ps.util.constant.State;

/**
 *
 * @author Vladimir Jecić
 */
public class DeleteLineSO extends AbstractSO{

    public DeleteLineSO(TransferObject tObj) {
        super(tObj);
    }
    @Override
    protected boolean preconditions() throws Exception {
        try {
            executePreconditions(tObj.getGdo());
            Line line = (Line) tObj.getGdo();
            if (!line.getState().equals(State.REMOVED)) {
                throw new ConstraintException(line.message10());
            }
            return true;
        } catch (ConstraintException cex) {
            StringBuilder sb = new StringBuilder();
             sb.append(new Line().message14()).append(":\nPreconditions not satisfied:")
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
        Line line = (Line) tObj.getGdo();
        try{
            signal = repository.deleteRecord(line);
        } catch (Exception e) {
            signal = false;
            throw e;
        } finally {
            tObj.setSignal(signal);
            if (signal) {
                tObj.setMessage(line.message13());
            } else {
                tObj.setMessage(line.message14());
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
             sb.append(new Line().message14()).append(":\nPostconditions not satisfied:")
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
