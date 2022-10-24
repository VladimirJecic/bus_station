/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.constraint;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Departure;
import rs.ac.bg.fon.ps.domain.GeneralDObject;
import rs.ac.bg.fon.ps.domain.Line;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.exception.constraint.ComplexValueConstraintException;
import rs.ac.bg.fon.ps.exception.constraint.SimpleValueConstraintException;
import rs.ac.bg.fon.ps.exception.constraint.StructureConstraintException;
import rs.ac.bg.fon.ps.repository.db.DbRepository;
import rs.ac.bg.fon.ps.util.MyStringBuilder;
import rs.ac.bg.fon.ps.util.constant.State;

/**
 *
 * @author Vladimir Jecić
 */
public class ConstraintsDeparture extends Constraints {

    public ConstraintsDeparture(DbRepository repository) {
        super(repository);
    }

    @Override
    public void preconditionSimpleValueConstraint_insert(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
        setPrimaryKey(gdo);
        preconditionSimpleValueConstraint_update(gdo);
    }

    @Override
    public void preconditionSimpleValueConstraint_update(GeneralDObject gdo) throws SimpleValueConstraintException {
        Departure departure = (Departure) gdo;
        StringBuilder sb = new StringBuilder();
        if (departure.getLine() == null || departure.getLine().getLineID() == null) {
            sb.append("\n").append(departure.message3());
        }
        if (departure.getDepartureID() == null) {
            sb.append("\n").append(departure.message4());
        }
        if (departure.getDepartureTime() == null) {
            sb.append("\n").append(departure.message5());
        }
        if (departure.getArrivalTime() == null) {
            sb.append("\n").append(departure.message6());
        }
        if (departure.getMaxPassengerNumber() <= 0) {
            sb.append("\n").append(departure.message7());
        }
        if (departure.getPassengerNumber() < 0) {
            sb.append("\n").append(departure.message8());
        }
        if (departure.getPrice() == null || departure.getPrice().compareTo(BigDecimal.ZERO) == -1) {
            sb.append("\n").append(departure.message9());
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
        Departure departure = (Departure) gdo;
        StringBuilder sb = new StringBuilder();
        if (departure.getLine() == null || departure.getLine().getLineID() == null) {
            sb.append("\n").append(departure.message3());
        }
        if (departure.getDepartureID() == null) {
            sb.append("\n").append(departure.message4());
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
        Line line = new Line(((Departure) gdo).getLine().getLineID());
        line = (Line) repository.findRecord(line);
        if (line.getLineName() == null) {
            String message16 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", ((Departure) gdo).message16());
            throw new StructureConstraintException(message16);
        }
    }

    @Override
    public void preconditionStructureConstraint_update(GeneralDObject gdo) throws StructureConstraintException, Exception {
        preconditionStructureConstraint_insert(gdo);
    }
 
    @Override
    public void postconditionSimpleValueConstraint_insert(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
        postconditionSimpleValueConstraint_update(gdo);
    }

    @Override
    public void preconditionComplexValueConstraint_insert(GeneralDObject gdo) throws ComplexValueConstraintException, Exception {
        preconditionComplexValueConstraint_update(gdo); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void preconditionComplexValueConstraint_update(GeneralDObject gdo) throws ComplexValueConstraintException, Exception {
        Departure departure = (Departure) gdo;
        if (!(departure.getDepartureTime().getTime() < departure.getArrivalTime().getTime())) {
            String message17 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", ((Departure) gdo).message17());
            throw new ComplexValueConstraintException(message17);
        }
        if (!(departure.getPassengerNumber() < departure.getMaxPassengerNumber())) {
            String message18 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", ((Departure) gdo).message18());
            throw new ComplexValueConstraintException(message18);
        }
    }

    @Override
    public void postconditionSimpleValueConstraint_update(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
        Departure departure = new Departure(((Departure) gdo).getLine(), ((Departure) gdo).getDepartureID());
        departure = (Departure) repository.findRecord(departure);
        try {
            preconditionSimpleValueConstraint_update(departure);
        } catch (SimpleValueConstraintException sce) {
            String message13 = MyStringBuilder.concatStrings(departure.message13(), ":\n", sce.getMessage());
            throw new SimpleValueConstraintException(message13);
        }
    }

    @Override
    public void postconditionSimpleValueConstraint_delete(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
        Departure departure = new Departure(((Departure) gdo).getLine(), ((Departure) gdo).getDepartureID());
        departure = (Departure) repository.findRecord(departure);
        departure.setState(State.REMOVED);
        if (departure.getDepartureTime() != null) {
            String message15 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", departure.message15());
            throw new SimpleValueConstraintException(message15);
        }
    }

    @Override
    public void postconditionComplexValueConstraint_insert(GeneralDObject gdo) throws ComplexValueConstraintException, Exception {
        Departure departure = (Departure) gdo;
        int passengerNumber = departure.getPassengerNumber();
        List<Ticket> ticketList = new ArrayList();
        Ticket exampleTicket = new Ticket(departure.getLine(), departure);
        ticketList = repository.findRecords(exampleTicket, ticketList,exampleTicket.getTicketsForDeparture_Where());
        if (passengerNumber != ticketList.size()) {
            String message19 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", departure.message19());
            throw new ComplexValueConstraintException(message19);
        }
    }

    @Override
    public void postconditionComplexValueConstraint_update(GeneralDObject gdo) throws ComplexValueConstraintException, Exception {
        postconditionComplexValueConstraint_insert(gdo); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void postconditionComplexValueConstraint_delete(GeneralDObject gdo) throws ComplexValueConstraintException, Exception {
        //number of tickets should be 0
        Departure departure = (Departure) gdo;
        List<Ticket> ticketList = new ArrayList();
        Ticket exampleTicket = new Ticket(departure.getLine(), departure);
        ticketList = repository.findRecords(exampleTicket, ticketList,exampleTicket.getTicketsForDeparture_Where());
        if (!ticketList.isEmpty()) {
            String message19 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", departure.message19());
            throw new ComplexValueConstraintException(message19);
        }
    }

    @Override
    public void postconditionStructureConstraint_delete(GeneralDObject gdo) throws StructureConstraintException, Exception {
        Departure departure = (Departure) gdo;
        List<Ticket> ticketList = new ArrayList();
        Ticket exampleTicket = new Ticket(departure.getLine(), departure);
        ticketList = repository.findRecords(exampleTicket, ticketList,exampleTicket.getTicketsForDeparture_Where());
        if (!ticketList.isEmpty()) {
            String message20 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", departure.message20());
            throw new StructureConstraintException(message20);
        }
    }


    public void preconditionSimpleValueConstraint_GetTicketsForDepartureSO(GeneralDObject gdo) throws SimpleValueConstraintException {
        Departure departure = (Departure) gdo;
        StringBuilder sb = new StringBuilder();
        if (departure.getLine() == null || departure.getLine().getLineID() == null) {
            sb.append("\n").append(departure.message3());
        }
        if (departure.getDepartureID() == null) {
            sb.append("\n").append(departure.message4());
        }
        String errorMessage = sb.toString();
        if (!errorMessage.isEmpty()) {
            sb.setLength(0);
            sb.append(this.getClassName()).append(errorMessage);
            throw new SimpleValueConstraintException(sb.toString());
        }
    }
    @Override
    protected void setPrimaryKey(GeneralDObject gdo) throws Exception {
        Departure departure = (Departure) gdo;
        try {
            Long departureID = repository.getNewKey(departure);
            departure.setDepartureID(departureID);
        } catch (Exception ex) {
            throw new Exception(departure.message2(), ex);
        }
    }
}
