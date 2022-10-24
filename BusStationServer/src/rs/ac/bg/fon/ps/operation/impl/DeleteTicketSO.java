/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.impl;

import rs.ac.bg.fon.ps.communication.exchangeable.TransferObject;
import rs.ac.bg.fon.ps.domain.Departure;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.exception.constraint.ConstraintException;
import rs.ac.bg.fon.ps.operation.AbstractSO;
import rs.ac.bg.fon.ps.util.constant.State;

/**
 *
 * @author Vladimir Jecić
 */
public class DeleteTicketSO extends AbstractSO {

    public DeleteTicketSO(TransferObject tObj) {
        super(tObj);
    }

    @Override
    protected boolean preconditions() throws Exception {
        try {
            executePreconditions(tObj.getGdo());
            Ticket ticket = (Ticket) tObj.getGdo();
            if (!ticket.getState().equals(State.REMOVED)) {
                throw new ConstraintException(ticket.message16());
            }
            return true;
        } catch (ConstraintException cex) {
            StringBuilder sb = new StringBuilder();
            sb.append(new Ticket().message15()).append(":\nPreconditions not satisfied:")
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
        Ticket ticket = (Ticket) tObj.getGdo();
        try {
            signal = repository.deleteRecord(ticket);
        } catch (Exception e) {
            throw e;
        } finally {
            tObj.setSignal(signal);
            if (signal) {
                tObj.setMessage(ticket.message14());
            } else {
                tObj.setMessage(ticket.message15());
            }
        }
    }

    @Override
    protected boolean postconditions() throws Exception {
        Ticket ticket = (Ticket) tObj.getGdo();
        Departure departure = ticket.getDeparture();
        try {
            departure.setPassengerNumber(departure.getPassengerNumber() - 1);
            departure.setState(State.CHANGED);
            executePreconditions(departure);
            boolean signal = repository.updateRecord(departure);
            if (signal) {
                executePostconditions(departure);
            } else {
                throw new ConstraintException(departure.message23());
            }
            executePostconditions(ticket);
            return true;
        } catch (ConstraintException cex) {
            StringBuilder sb = new StringBuilder();
            sb.append(new Ticket().message15()).append(":\nPostconditions not satisfied:")
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
