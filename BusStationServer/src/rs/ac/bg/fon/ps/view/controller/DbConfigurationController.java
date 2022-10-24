/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.exception.ValidationException;
import rs.ac.bg.fon.ps.util.constant.MyServerConstants;
import rs.ac.bg.fon.ps.util.constant.PathConstants;
import rs.ac.bg.fon.ps.view.form.FrmDbConfiguration;

/**
 *
 * @author Vladimir Jecić
 */
public class DbConfigurationController {

    FrmDbConfiguration frm;

    public DbConfigurationController(FrmDbConfiguration frm) {
        this.frm = frm;
        addActionListeners();
    }

    private void addActionListeners() {
        frm.saveDbConfigurationActionListener((e) -> saveDbConfiguration());
        frm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancelForm();
            }
        });
        frm.cancelDbConfigurationActionListener(e -> cancelForm());
    }

    public void openForm() {
        try {
            frm.setTitle("Database Configuration");
            prepareView();
            frm.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog((Component) frm, "Error in view initialization\n" + e.getMessage(), frm.getTitle(), JOptionPane.ERROR_MESSAGE);
            frm.dispose();
        }
    }

    public void prepareView() throws Exception {
        frm.setLocationRelativeTo(frm.getParent());
        frm.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        frm.getLblUrlError().setForeground(Color.RED);
        frm.getLblUsernameError().setForeground(Color.RED);
        frm.getLblConnectionPoolSizeError().setForeground(Color.RED);
        frm.getTxtPassword().requestFocus();
        fillForm();
    }

    public void fillForm() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream(PathConstants.DB_CONFIG_PROPERTIES));
        String url = properties.getProperty(MyServerConstants.URL);
        String username = properties.getProperty(MyServerConstants.USERNAME);
        String password = properties.getProperty(MyServerConstants.PASSWORD);
        int cpl = Integer.valueOf(properties.getProperty(MyServerConstants.CONNECTION_POOL_SIZE));
        frm.getTxtUrl().setText(url);
        frm.getTxtUsername().setText(username);
        frm.getTxtPassword().setText(password);
        frm.getTxtConnectionPoolSize().setText(String.valueOf(cpl));
    }

    public void saveDbConfiguration() {
        StringBuilder sb = new StringBuilder();
        try {
            if (!confirmSave()) {
                return;
            };
            resetForm();
            validateForm();
            Properties properties = new Properties();
            String fileName = PathConstants.DB_CONFIG_PROPERTIES;
            properties.load(new FileInputStream(fileName));
            String url = frm.getTxtUrl().getText();
            String username = frm.getTxtUsername().getText();
            String password = frm.getTxtPassword().getText();
            String cpl = frm.getTxtConnectionPoolSize().getText().trim();

            properties.setProperty(MyServerConstants.URL, url);
            properties.setProperty(MyServerConstants.USERNAME, username);
            properties.setProperty(MyServerConstants.PASSWORD, password);
            properties.setProperty(MyServerConstants.CONNECTION_POOL_SIZE, cpl);
            Path path = Paths.get(fileName);
            File file = new File(fileName);
            if (!file.exists()) {
                Files.createFile(path);
            };
            properties.store(new FileOutputStream(file), null);
            sb.append(frm.getTitle()).append(" saved!\n");
            JOptionPane.showMessageDialog(frm, sb.toString(), "Save Configuration",
                    JOptionPane.INFORMATION_MESSAGE);
            cancelForm();
        } catch (Exception ex) {
            sb.append("System couldn't save ")
                    .append(frm.getTitle()).append("!\n").append(ex.getMessage());
            JOptionPane.showMessageDialog(frm, sb.toString(), "Save Configuration",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    protected void validateForm() throws ValidationException {
        StringBuilder sb = new StringBuilder();
        if (frm.getTxtUrl().getText().isEmpty()) {
            frm.getLblUrlError().setText("URL can not be empty!");
            sb.append("URL can not be empty!\n");
        }
        if (frm.getTxtUsername().getText().isEmpty()) {
            frm.getLblUsernameError().setText("Username can not be empty!");
            sb.append("Username can not be empty!\n");
        }

        if (frm.getTxtConnectionPoolSize().getText().isEmpty()) {
            frm.getLblConnectionPoolSizeError().setText("Connection pool size can not be empty!");
            sb.append("Connection pool size can not be empty!\n");
        }
        try {
            int cpl = Integer.valueOf(frm.getTxtConnectionPoolSize().getText().toString().trim());
            if (cpl < 1) {
                frm.getLblConnectionPoolSizeError().setText("Connection pool size must be a positive number!\n");
                sb.append("Connection pool size must be a positive number!\n");
            }
        } catch (NumberFormatException numberFormatException) {
            sb.append("Connection pool size must be a number!\n");
        }
        String errorMessage = sb.toString();
        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }

    private void cancelForm() {
        int input = JOptionPane.showConfirmDialog(frm, "Cancel " + frm.getTitle().toLowerCase() + "?", "Select an Option...",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        // 0=yes, 1=no, -1 = x clicked
        if (input == JOptionPane.YES_OPTION) {
            frm.dispose();
        }
    }

    private boolean confirmSave() {
        int input = JOptionPane.showConfirmDialog((Component) frm, "Save " + frm.getTitle().toLowerCase() + "?", "Confirm operation",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        // 0=yes, 1=no, 2=cancel
        return input == 0;
    }

    private void resetForm() {
        frm.getLblUrlError().setText("");
        frm.getLblUsernameError().setText("");
        frm.getLblConnectionPoolSizeError().setText("");
    }
}
