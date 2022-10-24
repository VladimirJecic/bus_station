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

/**
 *
 * @author Vladimir Jecić
 */
public class TableModelStation extends AbstractTableModel {

    public TableModelStation() {
        stationList = new ArrayList<>();
    }

    public TableModelStation(List<Station> stationList) {
        this.stationList = stationList;
    }

    @Override
    public int getRowCount() {
        return stationList.size();
    }

    @Override
    public int getColumnCount() {
        return columnClasses.length;

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Station station = stationList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return station.getStationID();
            case 1:
                return station.getStationName();
            default:
                System.out.println("TableModelStation->Chosen non-existent column\n");
                return "n/a";
        }
    }
    private List<Station> stationList;
    String[] columnNames = new String[]{"Station ID", "Name"};
    Class[] columnClasses = new Class[]{Long.class, String.class};

    @Override
    public Class getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Station removeStationAt(int selectedRow) {
        Station removed = stationList.remove(selectedRow);
        fireTableDataChanged();
        return removed;
    }
        public Station getStationAt(int selectedRow) {
        return stationList.get(selectedRow);
    }

    public void setStationList(List<Station> stationList) {
        this.stationList = stationList;
        fireTableDataChanged();
    }



}
