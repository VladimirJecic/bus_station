/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.form.component.table;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Departure;
import rs.ac.bg.fon.ps.logger.MyLogger;
import rs.ac.bg.fon.ps.util.DateUtilities;
import rs.ac.bg.fon.ps.util.constant.State;

/**
 *
 * @author Vladimir Jecić
 */
public class TableModelDeparture extends AbstractTableModel {

    public TableModelDeparture() {
        departureList = new ArrayList<>();
    }

    public TableModelDeparture(List<Departure> departureList) {
        this.departureList = departureList;
    }

    @Override
    public int getRowCount() {
        return departureList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Departure departure = departureList.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return departure.getDepartureID();
            case 1:
                Date departureTimeDate = departure.getDepartureTime();
                String departureTimeString = "";
                if (departureTimeDate != null) {
                    departureTimeString = DateUtilities.parse(departureTimeDate);
                }
                return departureTimeString;
            case 2:
                Date arrivalTimeDate = departure.getArrivalTime();
                String arrivalTimeString = "";
                if (arrivalTimeDate != null) {
                    arrivalTimeString = DateUtilities.parse(arrivalTimeDate);
                }
                return arrivalTimeString;
            case 3:
                return departure.getMaxPassengerNumber();
            case 4:
                return departure.getMaxPassengerNumber() - departure.getPassengerNumber();
            case 5:
                return departure.getPrice();
            case 6:
                return departure.getPlatform();
            default:
                System.out.println("TableModelDeparture->Chosen non-existent column\n");
                return "n/a";

        }
    }
    List<Departure> departureList;
    int editableRow = -1;
    String[] columnNames = new String[]{"Departure No.", "Departure Time", "Arrival Time", "Max Seats", "Free Seats", "Ticket Price", "Platform"};
    Class[] columnClasses = new Class[]{Long.class, String.class, String.class, Integer.class, Integer.class, BigDecimal.class, Integer.class};

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Departure departure = departureList.get(rowIndex);

        switch (columnIndex) {
            case 1:
                try {
                    Date departureTime = DateUtilities.format((String) aValue);
                    departure.setDepartureTime(departureTime);
                } catch (ParseException pex) {
                    System.out.println("Bad Date Format:\n" + pex.getMessage());
                }
                break;
            case 2:
                try {
                    Date arrivalTime = DateUtilities.format((String) aValue);
                    departure.setArrivalTime(arrivalTime);
                } catch (ParseException pex) {
                    System.out.println("Bad Date Format:\n" + pex.getMessage());
                }
                break;

            case 3:
                int maxNumberOfSeats = (int) aValue;
                if (maxNumberOfSeats > 0) {
                    departure.setMaxPassengerNumber(maxNumberOfSeats);
                } else {
                    System.out.println("Number of Seats must be a positive number\n");
                }
                break;

            case 5:
                BigDecimal price = (BigDecimal) aValue;
                if (price.compareTo(BigDecimal.ZERO) > 0) {
                    departure.setPrice(price);
                } else {
                    System.out.println("Ticket Price must be a positive number\n");
                }
                break;
            case 6:
                int platform = (int) aValue;
                if (platform > 0) {
                    departure.setPlatform(platform);
                } else {
                    System.out.println("Platform must be a positive number\n");
                }
                break;
            default:
                System.out.println("TableModelDeparture->Chosen non-existent column\n");

        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0 && columnIndex != 4 && rowIndex == editableRow;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Departure removeDepartureAt(int selectedRow) {
        Departure removed = departureList.remove(selectedRow);
        if (!removed.getState().equals(State.NEW)) {
            removed.setState(State.REMOVED);
        }
        editableRow = -1;
        fireTableDataChanged();
        return removed;
    }

    public void addDeparture(Departure departure) {
        departure.setState(State.NEW);
        String date = DateUtilities.parse(new Date());// to speed up input process
        try {
            departure.setDepartureTime(DateUtilities.format(date));
            departure.setArrivalTime(DateUtilities.format(date));
            editableRow = departureList.size();
            if(!departureList.isEmpty()){
            departure.setDepartureID(departureList.get(departureList.size()-1).getDepartureID()+1L);
            }else{
                departure.setDepartureID(1L);
            }
            departureList.add(departure);
            fireTableRowsInserted(departureList.size() - 1, departureList.size() - 1);
        } catch (ParseException pEx) {
            MyLogger.getLogger(getClass()).log(Level.WARNING,pEx.getMessage());
        }
    }

    public List<Departure> getDepartureList() {
        return departureList;
    }

    public List<Departure> getNewDepartures() {
        List<Departure> newDepartures = new ArrayList<>();
        for (Departure departure : departureList) {
            if (departure.getState().equals(State.NEW)) {
                newDepartures.add(departure);
            }
        }
        return newDepartures;
    }

    public void refreshView() {
        fireTableDataChanged();
    }

    public Departure getDepartureAt(int selectedRow) {
        editDepartureAt(selectedRow);
        return departureList.get(selectedRow);
    }

    public void editDepartureAt(int selectedRow) {
        Departure departure = departureList.get(selectedRow);
        if (!departure.getState().equals(State.NEW)) {
            departure.setState(State.CHANGED);
        }
        editableRow = selectedRow;

    }

}
