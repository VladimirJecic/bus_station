/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.util;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Vladimir Jecić
 */
public class JTableUtilities {

    //link:https://stackoverflow.com/questions/7433602/how-to-center-in-jtable-cell-a-value
    public static void setCellAlignment(JTable table, int alignment) {

        DefaultTableCellRenderer newRenderer = new DefaultTableCellRenderer();
        newRenderer.setHorizontalAlignment(alignment);

        TableModel tableModel = table.getModel();

        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(newRenderer);
        }
    }

    //link:https://stackoverflow.com/questions/17627431/auto-resizing-the-jtable-column-widths
    public static void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();

        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
//        if(width > 300)
//            width=300;
            //this part I wrote myself to assure that all headers are visible
            int columnNameLength = table.getColumnName(column).length();
            if (columnNameLength*7 > width) {
                width = 7*columnNameLength;
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
}
