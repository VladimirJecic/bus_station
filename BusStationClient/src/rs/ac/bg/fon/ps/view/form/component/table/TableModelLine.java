/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.form.component.table;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Line;
import rs.ac.bg.fon.ps.domain.Station;
import rs.ac.bg.fon.ps.domain.Stop;
import rs.ac.bg.fon.ps.util.DateUtilities;

/**
 *
 * @author Vladimir Jecić
 */
public class TableModelLine extends AbstractTableModel {

    public TableModelLine() {
    }

    public TableModelLine(List<Line> lineList) {
        this.lineList = lineList;
    }

    @Override
    public int getRowCount() {
        return lineList.size();
    }

    @Override
    public int getColumnCount() {
        return columnClasses.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Line line = lineList.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return line;
            case 1:
                return line.getDistance();
            case 2: {
                try {
                    return DateUtilities.calculateMeanLineTravelTime(line);
                } catch (ParseException ex) {
                    System.out.println("Error in calculating travel time:\n"+ex.getMessage());
                    return line.getTravelTime();
                }
            }
            case 3:
                return line.getFirstStation();
            case 4:
                return line.getLastStation();
            case 5:
                String stops = "";
                for (Stop stop : line.getStops()) {
                    stops += stop.getStation().getStationName() + ";";
                }
                return stops;
            default:
                System.out.println("TableModelLine->Chosen non-existent column\n");
                return "n/a";

        }
    }

    private List<Line> lineList;
    String[] columnNames = new String[]{"Route", "Distance[km]", "Expected Travel Time[HH:mm]", "Departure Station", "Arrival Station", "Stops"};
    Class[] columnClasses = new Class[]{Line.class, BigDecimal.class, String.class, Station.class, Station.class, String.class};
//    String filterName;
//    Station filterDeparture;
//    Station filterDestination;
//    boolean filter1;
//    boolean filter2;
//    boolean filter3;
//
//    public void setFilters(String filterName, Station filterDeparture, Station filterDestination, boolean filter1, boolean filter2, boolean filter3) {
//        this.filterName = filterName;
//        this.filterDeparture = filterDeparture;
//        this.filterDestination = filterDestination;
//        this.filter1 = filter1;
//        this.filter2 = filter2;
//        this.filter3 = filter3;
//    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void setLineList(List<Line> lines) {
        this.lineList = lines;
        fireTableDataChanged();
    }

    public Line removeLineAt(int selectedRow) {
        Line removed = lineList.remove(selectedRow);
        fireTableDataChanged();
        return removed;
    }

    public Line getLineAt(int selectedRow) {
        return lineList.get(selectedRow);
    }

    public void refreshView() {
        fireTableDataChanged();
    }

}
