/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.form.component.table;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.thread.HandleClientThread;

/**
 *
 * @author Vladimir Jecić
 */
public class TableModelClient extends AbstractTableModel {

    public TableModelClient() {
        clientList = new ArrayList();
    }

    public TableModelClient(List<HandleClientThread> clientList) {
        this.clientList = clientList;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex]; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    
    @Override
    public int getRowCount() {
        return clientList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        HandleClientThread client = clientList.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return client.getSocket().getInetAddress().getHostAddress();
            case 1:
                return client.getSocket().getPort();
            case 2:
                return client.getClient().getUserName();
            default:
                System.out.println("TableModelClient->Chosen non-existent column\n");
                return "n/a";
        }

    }
    List<HandleClientThread> clientList;
    String[] columnNames = new String[]{"IP Adress", "Port", "UserName"};
    Class[] columnClasses = new Class[]{String.class, Integer.class, String.class};

    public void setClientList(List<HandleClientThread> list) {
        clientList = list; 
        fireTableDataChanged();
    }
    
    
}
