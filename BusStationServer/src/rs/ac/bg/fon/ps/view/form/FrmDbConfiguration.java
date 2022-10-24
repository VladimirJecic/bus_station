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
public class FrmDbConfiguration extends javax.swing.JDialog {

    /**
     * Creates new form FrmDatabaseConfiguration
     */
    public FrmDbConfiguration(java.awt.Frame parent, boolean modal) {
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

        lblUsername = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        lblUrl = new javax.swing.JLabel();
        txtUrl = new javax.swing.JTextField();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        lblUrlError = new javax.swing.JLabel();
        lblUsernameError = new javax.swing.JLabel();
        lblConnectionPoolSizeError = new javax.swing.JLabel();
        btnSaveDbConfiguration = new javax.swing.JButton();
        btnCancelDbConfiguration = new javax.swing.JButton();
        lblConnectionPoolSize = new javax.swing.JLabel();
        txtConnectionPoolSize = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblUsername.setText("Username:");

        lblPassword.setText("Password:");

        lblUrl.setText("URL:");

        btnSaveDbConfiguration.setText("Save");

        btnCancelDbConfiguration.setText("Cancel");

        lblConnectionPoolSize.setText("Connection Pool Size:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblConnectionPoolSizeError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(btnCancelDbConfiguration, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSaveDbConfiguration, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblConnectionPoolSize, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtConnectionPoolSize, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblUsername, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblUrl, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUsernameError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtUrl, javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(lblUrlError, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtUsername, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUrlError, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUsernameError, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConnectionPoolSize)
                    .addComponent(txtConnectionPoolSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblConnectionPoolSizeError, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveDbConfiguration)
                    .addComponent(btnCancelDbConfiguration))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelDbConfiguration;
    private javax.swing.JButton btnSaveDbConfiguration;
    private javax.swing.JLabel lblConnectionPoolSize;
    private javax.swing.JLabel lblConnectionPoolSizeError;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUrl;
    private javax.swing.JLabel lblUrlError;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblUsernameError;
    private javax.swing.JTextField txtConnectionPoolSize;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUrl;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
    public void saveDbConfigurationActionListener(ActionListener actionListener){
        btnSaveDbConfiguration.addActionListener(actionListener);
    }
    public void cancelDbConfigurationActionListener(ActionListener actionListener){
        btnCancelDbConfiguration.addActionListener(actionListener);
    }
    public javax.swing.JButton getBtnSave() {
        return btnSaveDbConfiguration;
    }

    public void setBtnSave(javax.swing.JButton btnSave) {
        this.btnSaveDbConfiguration = btnSave;
    }

    public javax.swing.JLabel getLblConnectionPoolSizeError() {
        return lblConnectionPoolSizeError;
    }

    public void setLblPasswordError(javax.swing.JLabel lblPasswordError) {
        this.lblConnectionPoolSizeError = lblPasswordError;
    }

    public javax.swing.JLabel getLblUrlError() {
        return lblUrlError;
    }

    public void setLblUrlError(javax.swing.JLabel lblUrlError) {
        this.lblUrlError = lblUrlError;
    }

    public javax.swing.JLabel getLblUsernameError() {
        return lblUsernameError;
    }

    public void setLblUsernameError(javax.swing.JLabel lblUsernameError) {
        this.lblUsernameError = lblUsernameError;
    }

    public javax.swing.JTextField getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(javax.swing.JTextField txtPassword) {
        this.txtPassword = txtPassword;
    }

    public javax.swing.JTextField getTxtUrl() {
        return txtUrl;
    }

    public void setTxtUrl(javax.swing.JTextField txtUrl) {
        this.txtUrl = txtUrl;
    }

    public javax.swing.JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(javax.swing.JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public javax.swing.JTextField getTxtConnectionPoolSize() {
        return txtConnectionPoolSize;
    }

    public void setTxtConnectionPoolSize(javax.swing.JTextField txtConnectionPoolSize) {
        this.txtConnectionPoolSize = txtConnectionPoolSize;
    }


}