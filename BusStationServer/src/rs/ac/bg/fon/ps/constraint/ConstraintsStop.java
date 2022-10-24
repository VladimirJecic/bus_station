/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.constraint;

import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.GeneralDObject;
import rs.ac.bg.fon.ps.domain.Line;
import rs.ac.bg.fon.ps.domain.Station;
import rs.ac.bg.fon.ps.domain.Stop;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.exception.constraint.SimpleValueConstraintException;
import rs.ac.bg.fon.ps.exception.constraint.StructureConstraintException;
import rs.ac.bg.fon.ps.repository.db.DbRepository;
import rs.ac.bg.fon.ps.util.MyStringBuilder;
import rs.ac.bg.fon.ps.util.constant.State;

/**
 *
 * @author Vladimir Jecić
 */
public class ConstraintsStop extends Constraints {

    public ConstraintsStop(DbRepository repository) {
        super(repository);
    }

    @Override
    public void preconditionSimpleValueConstraint_insert(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
        preconditionSimpleValueConstraint_update(gdo);
        Stop existStop = new Stop(((Stop) gdo).getLine(), ((Stop) gdo).getStation());
        existStop = (Stop) repository.findRecord(existStop);
        if ( existStop.getStopNumber()!=0 && ((Stop)gdo).getStation().equals(existStop.getStation())) {
            String message11 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", existStop.message11());
            throw new SimpleValueConstraintException(message11);
        }
    }

    @Override
    public void preconditionSimpleValueConstraint_update(GeneralDObject gdo) throws SimpleValueConstraintException {
        Stop stop = (Stop) gdo;
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append(":");
        String className = sb.toString();
        sb.setLength(0);
        if (stop.getLine() == null || stop.getLine().getLineID() == null) {
            sb.append("\n").append(stop.message3());
        }
        if (stop.getStation() == null || stop.getStation().getStationID() == null) {
            sb.append("\n").append(stop.message4());
        }
        if (stop.getStopNumber() <= 0) {
            sb.append("\n").append(stop.message5());
        }
        String errorMessage = sb.toString();
        if (!errorMessage.isEmpty()) {
            sb.setLength(0);
            sb.append(className).append(errorMessage);
            throw new SimpleValueConstraintException(sb.toString());
        }
    }

    @Override
    public void preconditionSimpleValueConstraint_delete(GeneralDObject gdo) throws SimpleValueConstraintException {
        Stop stop = (Stop) gdo;
        StringBuilder sb = new StringBuilder();
        if (stop.getLine() == null || stop.getLine().getLineID() == null) {
            sb.append("\n").append(stop.message3());
        }
        if (stop.getStation() == null || stop.getStation().getStationID() == null) {
            sb.append("\n").append(stop.message4());
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
        Line line = new Line(((Stop) gdo).getLine().getLineID());
        Station station = new Station(((Stop) gdo).getStation().getStationID());
        line = (Line) repository.findRecord(line);
        station = (Station) repository.findRecord(station);
        if (line.getLineName() == null) {
            String message9 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", gdo.message9());
            throw new StructureConstraintException(message9);
        }
        if (station.getStationName() == null) {
            String message10 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", gdo.message10());
            throw new StructureConstraintException(message10);
        }
    }

    @Override
    public void preconditionStructureConstraint_delete(GeneralDObject gdo) throws StructureConstraintException, Exception {
        //number of tickets that have refference must be 0
        Stop stop = (Stop) gdo;
        List<Ticket> ticketList = new ArrayList();
        Ticket exampleTicket = new Ticket();
        exampleTicket.setLine(stop.getLine());
        exampleTicket.setExitStop(stop);
        ticketList = repository.findRecords(exampleTicket, ticketList, exampleTicket.getTicketsForStop_Where());
        if (!ticketList.isEmpty()) {
            String message19 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", stop.message12());
            throw new StructureConstraintException(message19);
        }
    }

    @Override
    public void postconditionSimpleValueConstraint_insert(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
        Stop stop = new Stop(((Stop) gdo).getLine(), ((Stop) gdo).getStation());
        stop = (Stop) repository.findRecord(stop);
        stop.setState(State.NEW);
        if (!stop.equals(gdo)) {
            String message7 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", ((Stop) gdo).message7());
            throw new SimpleValueConstraintException(message7);
        }
    }

    @Override
    public void postconditionSimpleValueConstraint_update(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
        Stop stop = new Stop(((Stop) gdo).getLine(), ((Stop) gdo).getStation());
        stop = (Stop) repository.findRecord(stop);
        stop.setState(State.CHANGED);
        if (!stop.equals(gdo)) {
            String message7 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", ((Stop) gdo).message7());
            throw new SimpleValueConstraintException(message7);
        }
    }

    @Override
    public void postconditionSimpleValueConstraint_delete(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
        Stop stop = new Stop(((Stop) gdo).getLine(), ((Stop) gdo).getStation());
        stop = (Stop) repository.findRecord(stop);
        stop.setState(State.REMOVED);
        if (stop.equals(gdo)) {
            String message8 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", ((Stop) gdo).message8());
            throw new SimpleValueConstraintException(message8);
        }
    }

}
