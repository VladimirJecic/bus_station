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
import rs.ac.bg.fon.ps.exception.constraint.SimpleValueConstraintException;
import rs.ac.bg.fon.ps.exception.constraint.StructureConstraintException;
import rs.ac.bg.fon.ps.repository.db.DbRepository;
import rs.ac.bg.fon.ps.util.MyStringBuilder;

/**
 *
 * @author Vladimir Jecić
 */
public class ConstraintsStation extends Constraints {

    public ConstraintsStation(DbRepository repository) {
        super(repository);
    }

    @Override
    public void preconditionSimpleValueConstraint_insert(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
        setPrimaryKey(gdo);
        preconditionSimpleValueConstraint_update(gdo);
        Station newStation  = (Station) gdo;
        Station existStation = new Station();
        String where = MyStringBuilder.concatStrings("station.stationName = '",newStation.getStationName().trim(),"'");
        existStation = (Station) repository.findRecord(existStation,where);
        if (newStation.getStationName().equals(existStation.getStationName())) {
            String message16 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", existStation.message16());
            throw new SimpleValueConstraintException(message16);
        }
    }

    @Override
    public void preconditionSimpleValueConstraint_update(GeneralDObject gdo) throws SimpleValueConstraintException {
        Station station = (Station) gdo;
        StringBuilder sb = new StringBuilder();
        if (station.getStationID() == null) {
            sb.append("\n").append(station.message3());
        }
        if (station.getStationName() == null || station.getStationName().isEmpty()) {
            sb.append("\n").append(station.message4());
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
        Station station = (Station) gdo;
        if (station.getStationID() == null) {
            String message3 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", station.message3());
            throw new SimpleValueConstraintException(message3);
        }
    }

    @Override
    public void preconditionStructureConstraint_delete(GeneralDObject gdo) throws StructureConstraintException, Exception {
        Station station = (Station) gdo;
        Line line = new Line();
        String where = MyStringBuilder.concatStrings("line.firstStationID = ", String.valueOf(station.getStationID()),
                " OR line.lastStationID = ", String.valueOf(station.getStationID()));
        line = (Line) repository.findRecord(line, where);
        if (line.getLineID() != null) {
            String message9 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", station.message9());
            throw new StructureConstraintException(message9);
        }
    }

    @Override
    public void postconditionSimpleValueConstraint_insert(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
        postconditionSimpleValueConstraint_update(gdo);
    }

    @Override
    public void postconditionSimpleValueConstraint_update(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
        Station station = new Station(((Station) gdo).getStationID());
        station = (Station) repository.findRecord(station);
        try {
            preconditionSimpleValueConstraint_update(station);
        } catch (SimpleValueConstraintException sce) {
            String message6 = MyStringBuilder.concatStrings(station.message6(), ":\n", sce.getMessage());
            throw new SimpleValueConstraintException(message6);
        }
    }

    @Override
    public void postconditionSimpleValueConstraint_delete(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
        Station station = new Station(((Station) gdo).getStationID());
        station = (Station) repository.findRecord(station);

        if (station.getStationName() != null) {
            String message11 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", station.message11());
            throw new SimpleValueConstraintException(message11);
        }

    }

    @Override
    public void postconditionStructureConstraint_delete(GeneralDObject gdo) throws StructureConstraintException, Exception {
        Station station = (Station) gdo;
        List<Stop> stopList = new ArrayList();
        Stop exampleStop = new Stop(null, station);
        stopList = repository.findRecords(exampleStop, stopList, exampleStop.getStopsForStation_Where());
        if (!stopList.isEmpty()) {
            String message15 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", station.message15());
            throw new SimpleValueConstraintException(message15);
        }
    }

    @Override
    protected void setPrimaryKey(GeneralDObject gdo) throws Exception {
        Station station = (Station) gdo;
        try {
            Long stationID = repository.getNewKey(station);
            station.setStationID(stationID);
        } catch (Exception ex) {
            throw new Exception(station.message2(), ex);
        }

    }

}
