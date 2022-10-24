/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.form;

import java.awt.event.ActionListener;

/**
 *
 * @author Vladimir Jecić
 */
public class FrmViewLines extends javax.swing.JDialog {


    /**
     * Creates new form FrmLine
     */
    public FrmViewLines(java.awt.Frame parent, boolean modal) throws Exception {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFilter = new javax.swing.JPanel();
        checkBoxLineName = new javax.swing.JCheckBox();
        checkBoxDestination = new javax.swing.JCheckBox();
        checkBoxDeparture = new javax.swing.JCheckBox();
        txtLineName = new javax.swing.JTextField();
        cbDeparture = new javax.swing.JComboBox<>();
        cbDestination = new javax.swing.JComboBox<>();
        btnApplyFilters = new javax.swing.JButton();
        btnClearFilters = new javax.swing.JButton();
        panelLines = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLine = new javax.swing.JTable();
        btnEditLine = new javax.swing.JButton();
        btnDeleteLine = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        panelFilter.setBorder(javax.swing.BorderFactory.createTitledBorder("Filter Lines"));

        checkBoxLineName.setText("by name");

        checkBoxDestination.setText("by destination");

        checkBoxDeparture.setText("by departure");

        cbDeparture.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbDestination.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnApplyFilters.setText("Apply Filters");

        btnClearFilters.setText("Clear Filters");

        javax.swing.GroupLayout panelFilterLayout = new javax.swing.GroupLayout(panelFilter);
        panelFilter.setLayout(panelFilterLayout);
        panelFilterLayout.setHorizontalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFilterLayout.createSequentialGroup()
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(checkBoxDestination, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(checkBoxDeparture, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(checkBoxLineName))
                .addGap(18, 18, 18)
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLineName, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cbDeparture, 0, 223, Short.MAX_VALUE)
                        .addComponent(cbDestination, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnApplyFilters)
                    .addComponent(btnClearFilters))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelFilterLayout.setVerticalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFilterLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxLineName)
                    .addComponent(txtLineName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxDeparture)
                    .addComponent(cbDeparture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxDestination)
                    .addComponent(cbDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(panelFilterLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(btnApplyFilters)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClearFilters)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelLines.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblLine.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblLine);

        btnEditLine.setText("Edit Line");

        btnDeleteLine.setText("Delete Line");

        btnCancel.setText("Close");

        javax.swing.GroupLayout panelLinesLayout = new javax.swing.GroupLayout(panelLines);
        panelLines.setLayout(panelLinesLayout);
        panelLinesLayout.setHorizontalGroup(
            panelLinesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLinesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLinesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLinesLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelLinesLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(btnEditLine, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68)
                        .addComponent(btnDeleteLine, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52))))
        );
        panelLinesLayout.setVerticalGroup(
            panelLinesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLinesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelLinesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditLine)
                    .addComponent(btnDeleteLine)
                    .addComponent(btnCancel))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 143, Short.MAX_VALUE))
                    .addComponent(panelLines, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(panelFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(panelLines, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApplyFilters;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClearFilters;
    private javax.swing.JButton btnDeleteLine;
    private javax.swing.JButton btnEditLine;
    private javax.swing.JComboBox<Object> cbDeparture;
    private javax.swing.JComboBox<Object> cbDestination;
    private javax.swing.JCheckBox checkBoxDeparture;
    private javax.swing.JCheckBox checkBoxDestination;
    private javax.swing.JCheckBox checkBoxLineName;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelFilter;
    private javax.swing.JPanel panelLines;
    private javax.swing.JTable tblLine;
    private javax.swing.JTextField txtLineName;
    // End of variables declaration//GEN-END:variables
    public void applyFiltersViewLinesActionListener(ActionListener actionListener){
        btnApplyFilters.addActionListener(actionListener);
    }
    public void clearFiltersViewLinesActionListener(ActionListener actionListener){
        btnClearFilters.addActionListener(actionListener);
    }
    public void editLineViewLinesActionListener(ActionListener actionListener){
        btnEditLine.addActionListener(actionListener);
    }
    public void deleteLineViewLinesActionListener(ActionListener actionListener){
        btnDeleteLine.addActionListener(actionListener);
    }
    public void cancelViewLinesActionListener(ActionListener actionListener){
        btnCancel.addActionListener(actionListener);
    }

    
    
    public javax.swing.JButton getBtnApplyFilters() {
        return btnApplyFilters;
    }
    public javax.swing.JButton getBtnCancel() {
        return btnCancel;
    }
    public javax.swing.JButton getBtnDeleteLine() {
        return btnDeleteLine;
    }
    public javax.swing.JButton getBtnEditLine() {
        return btnEditLine;
    }
    public javax.swing.JComboBox<Object> getCbDeparture() {
        return cbDeparture;
    }
    public javax.swing.JComboBox<Object> getCbDestination() {
        return cbDestination;
    }
    public javax.swing.JCheckBox getCheckBoxDeparture() {
        return checkBoxDeparture;
    }
    public javax.swing.JCheckBox getCheckBoxDestination() {
        return checkBoxDestination;
    }
    public javax.swing.JCheckBox getCheckBoxName() {
        return checkBoxLineName;
    }
    public javax.swing.JTable getTblLine() {
        return tblLine;
    }
    public javax.swing.JTextField getTxtLineName() {
        return txtLineName;
    }

    public javax.swing.JButton getBtnClearFilters() {
        return btnClearFilters;
    }

}