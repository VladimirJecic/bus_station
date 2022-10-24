/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.impl;

import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.communication.exchangeable.TransferObject;
import rs.ac.bg.fon.ps.constraint.ConstraintsDeparture;
import rs.ac.bg.fon.ps.domain.Departure;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.exception.constraint.ConstraintException;
import rs.ac.bg.fon.ps.operation.AbstractSO;

/**
 *
 * @author Vladimir Jecić
 */
public class GetTicketsForDepartureSO extends AbstractSO {

    public GetTicketsForDepartureSO(TransferObject tObj) {
        super(tObj);
    }

    @Override
    protected boolean preconditions() throws Exception {
        try {
            ConstraintsDeparture constraint = new ConstraintsDeparture(repository);
            constraint.preconditionNullAndInstanceofConstraint(tObj.getGdo(), Departure.class);
            constraint.preconditionSimpleValueConstraint_GetTicketsForDepartureSO(tObj.getGdo());
            return true;
        } catch (ConstraintException cex) {
            StringBuilder sb = new StringBuilder();
            sb.append(new Departure().message22()).append(":\nPreconditions not satisfied:")
                    .append(cex.getClass().getSimpleName()).append("\n").append(cex.getMessage());
            tObj.setMessage(sb.toString());
            return false;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    protected void executeSO() throws Exception {
        boolean signal = true;
        Departure departure = (Departure) tObj.getGdo();
        List<Ticket> ticketList = new ArrayList<>();
        Ticket exampleTicket = new Ticket(departure.getLine(), departure);
        try {
            ticketList = repository.findRecords(exampleTicket, ticketList, exampleTicket.getTicketsForDeparture_Where());
        } catch (Exception e) {
            signal = false;
            throw e;
        } finally {
            tObj.setSignal(signal);
            if (signal) {
                departure.setTickets(ticketList);
                tObj.setGdo(departure);
                tObj.setMessage(departure.message21());
            } else {
                tObj.setMessage(departure.message22());

            }
        }
    }

    @Override
    protected boolean postconditions() throws Exception {
        return true;
    }

}
