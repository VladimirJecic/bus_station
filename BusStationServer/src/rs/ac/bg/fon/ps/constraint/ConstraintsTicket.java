/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.constraint;

import rs.ac.bg.fon.ps.domain.Departure;
import rs.ac.bg.fon.ps.domain.GeneralDObject;
import rs.ac.bg.fon.ps.domain.Line;
import rs.ac.bg.fon.ps.domain.Stop;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.exception.constraint.ComplexValueConstraintException;
import rs.ac.bg.fon.ps.exception.constraint.SimpleValueConstraintException;
import rs.ac.bg.fon.ps.exception.constraint.StructureConstraintException;
import rs.ac.bg.fon.ps.repository.db.DbRepository;
import rs.ac.bg.fon.ps.util.MyStringBuilder;

/**
 *
 * @author Vladimir Jecić
 */
public class ConstraintsTicket extends Constraints {

    public ConstraintsTicket(DbRepository repository) {
        super(repository);
    }

    @Override
    public void preconditionSimpleValueConstraint_insert(GeneralDObject gdo) throws SimpleValueConstraintException {
        preconditionSimpleValueConstraint_update(gdo);
    }

    @Override
    public void preconditionSimpleValueConstraint_update(GeneralDObject gdo) throws SimpleValueConstraintException {
        Ticket ticket = (Ticket) gdo;
        StringBuilder sb = new StringBuilder();
        if (ticket.getLine() == null || ticket.getLine().getLineID() == null) {
            sb.append("\n").append(ticket.message3());
        }
        if (ticket.getDeparture() == null || ticket.getDeparture().getDepartureID() == null) {
            sb.append("\n").append(ticket.message4());
        }
        if (ticket.getSeatNumber() <= 0) {
            sb.append("\n").append(ticket.message5());
        }
        if (ticket.getFirstName() == null) {
            sb.append("\n").append(ticket.message6());
        }
        if (ticket.getLastName() == null) {
            sb.append("\n").append(ticket.message7());
        }
        if (ticket.getExitStop() == null || ticket.getExitStop().getStation() == null || ticket.getExitStop().getStation().getStationID() == null) {
            sb.append("\n").append(ticket.message8());
        }
        String errorMessage = sb.toString();
        if (!errorMessage.isEmpty()) {
            sb.setLength(0);
            sb.append(this.getClassName()).append(errorMessage);
            throw new SimpleValueConstraintException(sb.toString());
        }
    }

    @Override
    public void preconditionSimpleValueConstraint_delete(GeneralDObject gdo) throws SimpleValueConstraintException {
        Ticket ticket = (Ticket) gdo;
        StringBuilder sb = new StringBuilder();
        if (ticket.getLine() == null || ticket.getLine().getLineID() == null) {
            sb.append("\n").append(ticket.message3());
        }
        if (ticket.getDeparture() == null || ticket.getDeparture().getDepartureID() == null) {
            sb.append("\n").append(ticket.message4());
        }
        String errorMessage = sb.toString();
        if (!errorMessage.isEmpty()) {
            sb.setLength(0);
            sb.append(this.getClassName()).append(errorMessage);
            throw new SimpleValueConstraintException(sb.toString());
        }
    }

    @Override
    public void preconditionStructureConstraint_insert(GeneralDObject gdo) throws StructureConstraintException, Exception {
        preconditionStructureConstraint_update(gdo);
    }

    @Override
    public void preconditionStructureConstraint_update(GeneralDObject gdo) throws StructureConstraintException, Exception {
        Ticket ticket = (Ticket) gdo;
        Line line = ticket.getLine();
        Departure departure = new Departure(line, ticket.getDeparture().getDepartureID());
        Stop stop = new Stop(line, ticket.getExitStop().getStation());
        line = (Line) repository.findRecord(line);
        departure = (Departure) repository.findRecord(departure);
        stop = (Stop) repository.findRecord(stop);
        if (line.getLineName() == null) {
            String message9 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", ticket.message9());
            throw new StructureConstraintException(message9);
        }
        if (departure.getDepartureTime() == null) {
            String message10 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", ticket.message10());
            throw new StructureConstraintException(message10);
        }
        if (stop.getStopNumber() == 0) {
            String message11 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", ticket.message11());
            throw new StructureConstraintException(message11);
        }
    }

    @Override
    public void postconditionSimpleValueConstraint_insert(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
        postconditionSimpleValueConstraint_update(gdo);
    }

    @Override
    public void postconditionSimpleValueConstraint_update(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
        Ticket ticket = new Ticket(((Ticket) gdo).getLine(), ((Ticket) gdo).getDeparture(), ((Ticket) gdo).getSeatNumber());
        ticket = (Ticket) repository.findRecord(ticket);
        if (ticket.getFirstName() == null) {
            String message13 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", ((Ticket) gdo).message13());
            throw new SimpleValueConstraintException(message13);
        }
    }

    @Override
    public void postconditionSimpleValueConstraint_delete(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
        Ticket ticket = new Ticket(((Ticket) gdo).getLine(), ((Ticket) gdo).getDeparture(), ((Ticket) gdo).getSeatNumber());
        ticket = (Ticket) repository.findRecord(ticket);
        if (ticket.getFirstName() != null) {
            String message15 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", ((Ticket) gdo).message15());
            throw new SimpleValueConstraintException(message15);
        }
    }

    @Override
    public void postconditionComplexValueConstraint_insert(GeneralDObject gdo) throws ComplexValueConstraintException, Exception {
        //**under comment because it is already checked in ConstraintsDeparture         
        //postconditionComplexValueConstraint_update();
    }

    @Override
    public void postconditionComplexValueConstraint_update(GeneralDObject gdo) throws ComplexValueConstraintException, Exception {
        //**under comment because it is already checked in ConstraintsDeparture
        //Departure departure = ((Ticket) gdo).getDeparture();
        //int passengerNumber = departure.getPassengerNumber();
        //List<Ticket> ticketList = new ArrayList();
        //Ticket exampleTicket = new Ticket(departure.getLine(),departure)
        //ticketList = repository.findRecords(exampleTicket, ticketList, exampleTicket.getTicketsForDeparture_Where());
        //if (passengerNumber != ticketList.size()) {
        //    String message19 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", departure.message19());
        //    throw new ComplexValueConstraintException(message19);
        //}
    }

    @Override
    public void postconditionComplexValueConstraint_delete(GeneralDObject gdo) throws ComplexValueConstraintException, Exception {
        //**under comment because it is already checked in ConstraintsDeparture
        //postconditionComplexValueConstraint_update();
    }

}
