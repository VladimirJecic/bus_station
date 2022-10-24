/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.form.component.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Stop;
import rs.ac.bg.fon.ps.domain.Ticket;

/**
 *
 * @author Vladimir Jecić
 */
public class TableModelTicket extends AbstractTableModel {

    public TableModelTicket() {
        ticketList = new ArrayList<>();
    }

    public TableModelTicket(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @Override
    public int getRowCount() {
        return ticketList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Ticket ticket = ticketList.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return ticket.getSeatNumber();
            case 1:
                return ticket.getFirstName();
            case 2:
                return ticket.getLastName();
            case 3:
                return ticket.getExitStop();
            default:
                System.out.println("TableModelTicket->Chosen non-existent column\n");
                return "n/a";

        }
    }
    List<Ticket> ticketList;
    String[] columnNames = new String[]{"Seat Number", "First Name", "Last Name", "Last Stop"};
    Class[] columnClasses = new Class[]{Integer.class, String.class, String.class, Stop.class};

////<editor-fold defaultstate="collapsed" desc="comment">
//    @Override
//    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//        Ticket ticket = tickets.get(rowIndex);
//
//        switch (columnIndex) {
//            case 0:
//                ticket.setSeatNumber((int) aValue);
//                break;
//            case 1:
//                ticket.setFirstName((String) aValue);
//                break;
//            case 2:
//                ticket.setLastName((String) aValue);
//                break;
//            case 3:
//                ticket.setExitStop((Stop) aValue);
//                break;
//            default:
//                System.out.println("TableModelTicket->Chosen non-existent column\n");
//        }
//    }
//</editor-fold>
    @Override
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

    public List<Ticket> getAllTicket() {
        return ticketList;
    }

    public Ticket getTicketAt(int selectedRow) {
        return ticketList.get(selectedRow);
    }
    public Ticket removeTicketAt(int selectedRow) {
        Ticket removed = ticketList.remove(selectedRow);
        fireTableDataChanged();
        return removed;
    }

}
