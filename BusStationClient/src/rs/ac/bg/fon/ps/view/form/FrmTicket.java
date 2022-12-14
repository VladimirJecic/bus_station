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
public class FrmTicket extends javax.swing.JDialog {


    /**
     * Creates new form FrmDeparture
     */
    public FrmTicket(java.awt.Frame parent, boolean modal) throws Exception {
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
        scPaneTicket = new javax.swing.JScrollPane();
        tblTicket = new javax.swing.JTable();
        btnDeleteTicket = new javax.swing.JButton();
        panelAddTicket = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSeatNumber = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbLastStop = new javax.swing.JComboBox<>();
        lblSeatNumberError = new javax.swing.JLabel();
        lblFirstNameError = new javax.swing.JLabel();
        lblLastNameError = new javax.swing.JLabel();
        btnGetSeatNumber = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnSaveTicket = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        tblTicket.setModel(new javax.swing.table.DefaultTableModel(
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
        scPaneTicket.setViewportView(tblTicket);

        btnDeleteTicket.setText("Delete Ticket");

        jLabel1.setText("Seat Number:");

        jLabel2.setText("First Name:");

        jLabel3.setText("Last Name:");

        jLabel4.setText("Last Stop:");

        cbLastStop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnGetSeatNumber.setText("Get Seat Number");

        javax.swing.GroupLayout panelAddTicketLayout = new javax.swing.GroupLayout(panelAddTicket);
        panelAddTicket.setLayout(panelAddTicketLayout);
        panelAddTicketLayout.setHorizontalGroup(
            panelAddTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddTicketLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAddTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(panelAddTicketLayout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtFirstName))
                        .addGroup(panelAddTicketLayout.createSequentialGroup()
                            .addGroup(panelAddTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panelAddTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtLastName)
                                .addComponent(cbLastStop, 0, 222, Short.MAX_VALUE))))
                    .addGroup(panelAddTicketLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSeatNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGetSeatNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAddTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblSeatNumberError, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addComponent(lblFirstNameError, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLastNameError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelAddTicketLayout.setVerticalGroup(
            panelAddTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddTicketLayout.createSequentialGroup()
                .addGroup(panelAddTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddTicketLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelAddTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAddTicketLayout.createSequentialGroup()
                                .addGroup(panelAddTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(txtSeatNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnGetSeatNumber))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lblSeatNumberError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panelAddTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelAddTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblFirstNameError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelAddTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelAddTicketLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblLastNameError, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addGroup(panelAddTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbLastStop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        btnCancel.setText("Cancel");

        btnSaveTicket.setText("Save Ticket");

        javax.swing.GroupLayout panelAllComponentsLayout = new javax.swing.GroupLayout(panelAllComponents);
        panelAllComponents.setLayout(panelAllComponentsLayout);
        panelAllComponentsLayout.setHorizontalGroup(
            panelAllComponentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAllComponentsLayout.createSequentialGroup()
                .addGroup(panelAllComponentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAllComponentsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelAllComponentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(btnSaveTicket, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDeleteTicket, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelAllComponentsLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(scPaneTicket, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(panelAddTicket, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelAllComponentsLayout.setVerticalGroup(
            panelAllComponentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAllComponentsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelAddTicket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scPaneTicket, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveTicket)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteTicket)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelAllComponents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDeleteTicket;
    private javax.swing.JButton btnGetSeatNumber;
    private javax.swing.JButton btnSaveTicket;
    private javax.swing.JComboBox<Object> cbLastStop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblFirstNameError;
    private javax.swing.JLabel lblLastNameError;
    private javax.swing.JLabel lblSeatNumberError;
    private javax.swing.JPanel panelAddTicket;
    private javax.swing.JPanel panelAllComponents;
    private javax.swing.JScrollPane scPaneTicket;
    private javax.swing.JTable tblTicket;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtSeatNumber;
    // End of variables declaration//GEN-END:variables

    public void getSeatNumberActionListener(ActionListener actionListener){
        btnGetSeatNumber.addActionListener(actionListener);
    }
    public void saveTicketActionListener(ActionListener actionListener){
        btnSaveTicket.addActionListener(actionListener);
    }
    public void deleteTicketActionListener(ActionListener actionListener){
        btnDeleteTicket.addActionListener(actionListener);
    }
    public void cancelTicketActionListener(ActionListener actionListener){
        btnCancel.addActionListener(actionListener);
    }
    
    
    
    public javax.swing.JButton getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(javax.swing.JButton btnCancel) {
        this.btnCancel = btnCancel;
    }

    public javax.swing.JButton getBtnDeleteTicket() {
        return btnDeleteTicket;
    }

    public void setBtnDeleteTicket(javax.swing.JButton btnDeleteTicket) {
        this.btnDeleteTicket = btnDeleteTicket;
    }

    public javax.swing.JButton getBtnSaveTicket() {
        return btnSaveTicket;
    }

    public void setBtnSaveTicket(javax.swing.JButton btnSaveTicket) {
        this.btnSaveTicket = btnSaveTicket;
    }

    public javax.swing.JComboBox<Object> getCbLastStop() {
        return cbLastStop;
    }

    public void setCbLastStop(javax.swing.JComboBox<Object> cbLastStop) {
        this.cbLastStop = cbLastStop;
    }

    public javax.swing.JLabel getjLabel3() {
        return jLabel3;
    }

    public void setjLabel3(javax.swing.JLabel jLabel3) {
        this.jLabel3 = jLabel3;
    }

    public javax.swing.JPanel getPanelAddTicket() {
        return panelAddTicket;
    }

    public void setPanelAddTicket(javax.swing.JPanel panelAddTicket) {
        this.panelAddTicket = panelAddTicket;
    }

    public javax.swing.JScrollPane getScPaneTicket() {
        return scPaneTicket;
    }

    public void setScPaneTicket(javax.swing.JScrollPane scPaneTicket) {
        this.scPaneTicket = scPaneTicket;
    }

    public javax.swing.JTable getTblTicket() {
        return tblTicket;
    }

    public void setTblTicket(javax.swing.JTable tblTicket) {
        this.tblTicket = tblTicket;
    }

    public javax.swing.JTextField getTxtFirstName() {
        return txtFirstName;
    }

    public void setTxtFirstName(javax.swing.JTextField txtFirstName) {
        this.txtFirstName = txtFirstName;
    }

    public javax.swing.JTextField getTxtLastName() {
        return txtLastName;
    }

    public void setTxtLastName(javax.swing.JTextField txtLastName) {
        this.txtLastName = txtLastName;
    }

    public javax.swing.JTextField getTxtSeatNumber() {
        return txtSeatNumber;
    }

    public void setTxtSeatNumber(javax.swing.JTextField txtSeatNumber) {
        this.txtSeatNumber = txtSeatNumber;
    }

    public javax.swing.JLabel getLblFirstNameError() {
        return lblFirstNameError;
    }

    public void setLblFirstNameError(javax.swing.JLabel lblFirstNameError) {
        this.lblFirstNameError = lblFirstNameError;
    }

    public javax.swing.JLabel getLblLastNameError() {
        return lblLastNameError;
    }

    public void setLblLastNameError(javax.swing.JLabel lblLastNameError) {
        this.lblLastNameError = lblLastNameError;
    }

    public javax.swing.JLabel getLblSeatNumberError() {
        return lblSeatNumberError;
    }

    public void setLblSeatNumberError(javax.swing.JLabel lblSeatNumberError) {
        this.lblSeatNumberError = lblSeatNumberError;
    }

    public javax.swing.JButton getBtnGetSeatNumber() {
        return btnGetSeatNumber;
    }

    public void setBtnGetSeatNumber(javax.swing.JButton btnGetSeatNumber) {
        this.btnGetSeatNumber = btnGetSeatNumber;
    }

    
}
