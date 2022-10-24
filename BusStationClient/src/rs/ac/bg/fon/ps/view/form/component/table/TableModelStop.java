/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.form.component.table;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Station;
import rs.ac.bg.fon.ps.domain.Stop;
import rs.ac.bg.fon.ps.util.constant.State;

/**
 *
 * @author Vladimir Jecić
 */
public class TableModelStop extends AbstractTableModel {

    public TableModelStop() {
        this.firstStop = new Stop(null, null, 1, State.NEW);
        this.lastStop = new Stop(null, null, 2, State.NEW);
        this.stopList = new ArrayList<>();
        stopList.add(firstStop);
        stopList.add(lastStop);
        this.removedStops = new ArrayList<>();
    }

    public TableModelStop(List<Stop> stopList) {
        this.stopList = stopList;
        this.firstStop = stopList.get(0);
        this.lastStop = stopList.get(stopList.size() - 1);
        this.removedStops = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return stopList.size();
    }

    @Override
    public int getColumnCount() {
        return columnClasses.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Stop stop = stopList.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return stop.getStopNumber();
            case 1:
                return stop.getStation();
            default:
                System.out.println("TableModelStop->Chosen non-existent column\n");
                return "n/a";
        }
    }
    private Stop firstStop;
    private Stop lastStop;
    List<Stop> stopList;
    List<Stop> removedStops;
    int editableRow = -1;
    String[] columnNames = new String[]{"Stop No.", "Station"};
    Class[] columnClasses = new Class[]{Integer.class, Station.class};

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Stop stop = stopList.get(rowIndex);

        switch (columnIndex) {
            case 1:
                stop.setStation((Station) aValue);
                break;
            default:
                System.out.println("TableModelStop->Chosen non-existent column\n");

        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return rowIndex == editableRow && columnIndex != 0;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public List<Stop> getAllStops() {
        List<Stop> allStops = new ArrayList<>();
        for (Stop stop1 : removedStops) {
            allStops.add(stop1);
        }
        for (Stop stop2 : stopList) {
            allStops.add(stop2);
        }
        return allStops;
    }
    public List<Stop> getExistingStops() {
        return stopList;
    }

    public void addStop(Stop newStop) {
        newStop.setState(State.NEW);
        newStop.setStation(stopList.get(stopList.size() - 2).getStation());//sets default station to penultimate stops's station
        stopList.add(stopList.size() - 1, newStop);//adds new stop to list
        lastStop.setStopNumber(stopList.size());//updates last stop's stopNumber
        newStop.setStopNumber(stopList.size() - 1);//sets new stop's stopNumber
        editableRow = stopList.size() - 2;//sets editable row to row in which new stop is in
        fireTableRowsInserted(stopList.size() - 2, stopList.size() - 1);
    }

    public Stop removeStopAt(int selectedRow) throws Exception {
        if (selectedRow == 0 || selectedRow == stopList.size() - 1) {
            throw new Exception("Departure and Destination can't be changed/removed from the table, use combo box instead");
        }
        Stop removedStop = stopList.remove(selectedRow);
        if (!removedStop.getState().equals(State.NEW)) {
            removedStop.setState(State.REMOVED);
            removedStops.add(removedStop);
        }
        int id = 1;
        for (Stop stop : stopList) {
            stop.setStopNumber(id++);
            if (id != 1 && !stop.getState().equals(State.NEW)) {
                stop.setState(State.CHANGED);//ordinal number changed
            }
        }
        editableRow = -1; //because I don't want first and last stop to become editable
        fireTableDataChanged();
        return removedStop;
    }

    public void editStopAt(int selectedRow) throws Exception {
        if (selectedRow == 0 || selectedRow == stopList.size() - 1) {
            throw new Exception("Departure and Destination can't be changed/removed from the table, use combo box instead");
        }
        Stop stop = stopList.get(selectedRow);
        if (!stop.getState().equals(State.NEW)) {
            Stop removedStop = stop.copy();
            removedStop.setState(State.REMOVED);
            removedStops.add(removedStop);//because Station is changed which is part of primary key!
            stop.setState(State.NEW);
        }
        editableRow = selectedRow;
    }

    public void setFirstStop(Station station) {
//        if (firstStop == null) {
//            firstStop = new Stop(null, station, 1, State.NEW);
//        } else if (!firstStop.getStation().equals(station)) {
//            firstStop.setStation(station);
//            firstStop.setState(State.CHANGED);
//        }
//        fireTableRowsUpdated(0, 0);

        firstStop.setStation(station);
        if (!firstStop.getState().equals(State.NEW)) {
            firstStop.setState(State.CHANGED);
        }
        fireTableRowsUpdated(0, 0);
    }

    public void setLastStop(Station station) {
//        if (lastStop == null) {
//            lastStop = new Stop(null, station, 2, State.NEW);
//        } else if(!lastStop.getStation().equals(station)){
//            lastStop.setStation(station);
//            lastStop.setState(State.CHANGED);
//        }
//        fireTableRowsUpdated(stops.size() - 1, stops.size() - 1);
        lastStop.setStation(station);
        if (!lastStop.getState().equals(State.NEW)) {
            lastStop.setState(State.CHANGED);

        }
        fireTableRowsUpdated(stopList.size() - 1, stopList.size() - 1);
    }

    public Stop getFirstStop() {
        return firstStop;
    }

    public Stop getLastStop() {
        return lastStop;
    }



}
