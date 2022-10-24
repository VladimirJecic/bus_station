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
import rs.ac.bg.fon.ps.domain.Employee;
import rs.ac.bg.fon.ps.domain.GeneralDObject;
import rs.ac.bg.fon.ps.domain.Line;
import rs.ac.bg.fon.ps.domain.Station;
import rs.ac.bg.fon.ps.domain.Stop;
import rs.ac.bg.fon.ps.exception.constraint.ConstraintException;
import rs.ac.bg.fon.ps.exception.constraint.SimpleValueConstraintException;
import rs.ac.bg.fon.ps.exception.constraint.StructureConstraintException;
import rs.ac.bg.fon.ps.repository.db.DbRepository;
import rs.ac.bg.fon.ps.util.MyStringBuilder;

/**
 *
 * @author Vladimir Jecić
 */
public class ConstraintsLine extends Constraints {

    public ConstraintsLine(DbRepository repository) {
        super(repository);
    }

    @Override
    public void preconditionSimpleValueConstraint_update(GeneralDObject gdo) throws SimpleValueConstraintException {
        Line line = (Line) gdo;
        StringBuilder sb = new StringBuilder();
        if (line.getLineID() == null) {
            sb.append("\n").append(line.message3());
        }
        if (line.getLineName() == null) {
            sb.append("\n").append(line.message4());
        }
        if (line.getDistance() == null || line.getDistance().compareTo(BigDecimal.ZERO) == -1) {
            sb.append("\n").append(line.message5());
        }
        if (line.getTravelTime() == null) {
            sb.append("\n").append(line.message6());
        }
        if (line.getEmployee() == null || line.getEmployee().getEmployeeID() == null) {
            sb.append("\n").append(line.message7());
        }
        if (line.getFirstStation() == null || line.getFirstStation().getStationID() == null) {
            sb.append("\n").append(line.message8());
        }
        if (line.getLastStation() == null || line.getLastStation().getStationID() == null) {
            sb.append("\n").append(line.message9());
        }
        String errorMessage = sb.toString();
        if (!errorMessage.isEmpty()) {
            sb.setLength(0);
            sb.append(this.getClassName()).append(errorMessage);
            throw new SimpleValueConstraintException(sb.toString());
        }
    }

    @Override
    public void preconditionSimpleValueConstraint_insert(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
        setPrimaryKey(gdo);
        preconditionSimpleValueConstraint_update(gdo);
    }

    @Override
    public void preconditionSimpleValueConstraint_delete(GeneralDObject gdo) throws SimpleValueConstraintException {
        Line line = (Line) gdo;
        if (line.getLineID() == null) {
            String message3 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", line.message3());
            throw new SimpleValueConstraintException(message3);
        }
    }

    @Override
    public void preconditionStructureConstraint_insert(GeneralDObject gdo) throws StructureConstraintException, Exception {
        Employee employee = new Employee(((Line) gdo).getEmployee().getEmployeeID());
        employee = (Employee) repository.findRecord(employee);
        if (employee.getFirstName() == null) {
            String message15 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", ((Line) gdo).message15());
            throw new StructureConstraintException(message15);
        }
        Station firstStation = new Station(((Line) gdo).getFirstStation().getStationID());
        Station lastStation = new Station(((Line) gdo).getLastStation().getStationID());
        firstStation = (Station) repository.findRecord(firstStation);
        lastStation = (Station) repository.findRecord(lastStation);
        if (firstStation.getStationName() == null || lastStation.getStationName() == null) {
            String message16 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", ((Line) gdo).message16());
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
    public void postconditionSimpleValueConstraint_update(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
        Line line = new Line(((Line) gdo).getLineID());
        line = (Line) repository.findRecord(line);
        try {
            preconditionSimpleValueConstraint_update(line);
        } catch (SimpleValueConstraintException sce) {
            String message12 = MyStringBuilder.concatStrings(line.message12(), ":\n", sce.getMessage());
            throw new SimpleValueConstraintException(message12);
        }
    }

    @Override
    public void postconditionSimpleValueConstraint_delete(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
        Line line = new Line(((Line) gdo).getLineID());
        line = (Line) repository.findRecord(line);

        if (line.getLineName() != null) {
            String message14 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", ((Line) gdo).message13());
            throw new SimpleValueConstraintException(message14);
        }
    }

    @Override
    public void postconditionStructureConstraint_delete(GeneralDObject gdo) throws StructureConstraintException, Exception {
        Line line = (Line) gdo;
        List<Departure> departureList = new ArrayList();
        List<Stop> stopList = new ArrayList();
        
        Departure exampleDeparture = new Departure(line, null);
        Stop exampleStop = new Stop(line,null);
        departureList = repository.findRecords(exampleDeparture, departureList, exampleDeparture.getDeparturesForLine_Where());
        stopList = repository.findRecords(exampleStop, stopList, exampleStop.getStopsForLine_Where());
        if (!departureList.isEmpty()) {
            String message17 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", line.message17());
            throw new SimpleValueConstraintException(message17);
        }
        if (!stopList.isEmpty()) {
            String message18 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", line.message18());
            throw new SimpleValueConstraintException(message18);
        }
    }

    @Override
    protected void setPrimaryKey(GeneralDObject gdo) throws ConstraintException, Exception {
        Line line = (Line) gdo;
        try {
            Long lineID = repository.getNewKey(line);
            line.setLineID(lineID);
        } catch (Exception ex) {
            throw new Exception(line.message2(), ex);
        }
    }

    public void preconditionSimpleValueConstraint_GetDeparturesForLineSO(GeneralDObject gdo) throws SimpleValueConstraintException {
        Line line = (Line) gdo;
        if (line.getLineID() == null) {
            String message3 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", line.message3());
            throw new SimpleValueConstraintException(message3);
        }
    }
}
