/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.form;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.domain.Station;

/**
 *
 * @author Vladimir Jecić
 */
public class FrmStation extends javax.swing.JDialog {

    /**
     * Creates new form FrmDeleteStation
     */
    public FrmStation(java.awt.Frame parent, boolean modal){
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

        panelAllComponents = new javax.swing.JPanel();
        scPaneStation = new javax.swing.JScrollPane();
        tblStation = new javax.swing.JTable();
        jPanelStationName = new javax.swing.JPanel();
        cbFilterStation = new javax.swing.JCheckBox();
        txtStationName = new javax.swing.JTextField();
        lblStationName = new javax.swing.JLabel();
        lblStationNameError = new javax.swing.JLabel();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        scPaneStation.setBorder(javax.swing.BorderFactory.createTitledBorder("Stations"));

        tblStation.setModel(new javax.swing.table.DefaultTableModel(
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
        scPaneStation.setViewportView(tblStation);

        jPanelStationName.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cbFilterStation.setText("Filter Name");

        lblStationName.setText("Station Name:");

        javax.swing.GroupLayout jPanelStationNameLayout = new javax.swing.GroupLayout(jPanelStationName);
        jPanelStationName.setLayout(jPanelStationNameLayout);
        jPanelStationNameLayout.setHorizontalGroup(
            jPanelStationNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStationNameLayout.createSequentialGroup()
                .addGroup(jPanelStationNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStationNameError, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelStationNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelStationNameLayout.createSequentialGroup()
                            .addGap(19, 19, 19)
                            .addComponent(txtStationName, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelStationNameLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(cbFilterStation)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lblStationName))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanelStationNameLayout.setVerticalGroup(
            jPanelStationNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStationNameLayout.createSequentialGroup()
                .addComponent(lblStationNameError, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelStationNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStationName)
                    .addComponent(cbFilterStation))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtStationName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnSave.setText("Save");

        btnCancel.setText("Cancel");

        javax.swing.GroupLayout panelAllComponentsLayout = new javax.swing.GroupLayout(panelAllComponents);
        panelAllComponents.setLayout(panelAllComponentsLayout);
        panelAllComponentsLayout.setHorizontalGroup(
            panelAllComponentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAllComponentsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAllComponentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scPaneStation, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelStationName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelAllComponentsLayout.setVerticalGroup(
            panelAllComponentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAllComponentsLayout.createSequentialGroup()
                .addComponent(jPanelStationName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scPaneStation, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addGap(4, 4, 4)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancel)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelAllComponents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelAllComponents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox cbFilterStation;
    private javax.swing.JPanel jPanelStationName;
    private javax.swing.JLabel lblStationName;
    private javax.swing.JLabel lblStationNameError;
    private javax.swing.JPanel panelAllComponents;
    private javax.swing.JScrollPane scPaneStation;
    private javax.swing.JTable tblStation;
    private javax.swing.JTextField txtStationName;
    // End of variables declaration//GEN-END:variables


    public void saveStationActionListener(ActionListener actionListener){
        btnSave.addActionListener(actionListener);
    }
    public void deleteStationActionListener(ActionListener actionListener){
        btnDelete.addActionListener(actionListener);
    }
    public void cancelStationActionListener(ActionListener actionListener){
        btnCancel.addActionListener(actionListener);
    }
    public void filterStationActionListener(ItemListener itemListener){
        cbFilterStation.addItemListener(itemListener);
    }
    
    public javax.swing.JButton getBtnCancel() {
        return btnCancel;
    }
    public javax.swing.JButton getBtnDelete() {
        return btnDelete;
    }

    public javax.swing.JButton getBtnSave() {
        return btnSave;
    }

    public javax.swing.JCheckBox getCbFilterStation() {
        return cbFilterStation;
    }

    public javax.swing.JLabel getLblStationName() {
        return lblStationName;
    }

    public javax.swing.JScrollPane getScPaneStation() {
        return scPaneStation;
    }

    public javax.swing.JTable getTblStation() {
        return tblStation;
    }
    public javax.swing.JTextField getTxtStationName() {
        return txtStationName;
    }
    public void setTxtStationName(javax.swing.JTextField txtStationName) {
        this.txtStationName = txtStationName;
    }
    public javax.swing.JLabel getLblStationNameError() {
        return lblStationNameError;
    }
    public void setLblStationNameError(javax.swing.JLabel lblStationNameError) {
        this.lblStationNameError = lblStationNameError;
    }
    
    
}
