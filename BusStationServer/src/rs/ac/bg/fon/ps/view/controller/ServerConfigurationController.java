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
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.exception.ValidationException;
import rs.ac.bg.fon.ps.util.constant.MyServerConstants;
import rs.ac.bg.fon.ps.util.constant.PathConstants;
import rs.ac.bg.fon.ps.view.form.FrmDbConfiguration;
import rs.ac.bg.fon.ps.view.form.FrmServerConfiguration;

/**
 *
 * @author Vladimir Jecić
 */
public class ServerConfigurationController {

    FrmServerConfiguration frm;

    public ServerConfigurationController(FrmServerConfiguration frm) {
        this.frm = frm;
        addActionListeners();
    }

    private void addActionListeners() {
        frm.saveServerConfigurationActionListener((e) -> saveServerConfiguration());
        frm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancelForm();
            }
        });
        frm.cancelServerConfigurationActionListener(e -> cancelForm());
    }

    public void openForm() {
        try {
            frm.setTitle("Server Configuration");
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
        frm.getTxtAddress().setEditable(false);
        frm.getLblAddresslError().setForeground(Color.RED);
        frm.getLblPortError().setForeground(Color.RED);
        frm.getLblMaxClientsError().setForeground(Color.RED);
        frm.getTxtPort().requestFocus();
        fillForm();
    }

    public void fillForm() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream(PathConstants.SERVER_CONFIG_PROPERTIES));
        String address = InetAddress.getLocalHost().getHostAddress();
        String portString = properties.getProperty(MyServerConstants.SERVER_PORT);
        int port = Integer.valueOf(properties.getProperty(MyServerConstants.SERVER_PORT));
        int maxClients = Integer.valueOf(properties.getProperty(MyServerConstants.MAX_CONNECTED_CLIENTS));
        frm.getTxtAddress().setText(address);
        frm.getTxtPort().setText(String.valueOf(port));
        frm.getTxtMaxClients().setText(String.valueOf(maxClients));
    }

    public void saveServerConfiguration() {
        StringBuilder sb = new StringBuilder();
        try {
            if (!confirmSave()) {
                return;
            };
            resetForm();
            validateForm();
            Properties properties = new Properties();
            String fileName = PathConstants.SERVER_CONFIG_PROPERTIES;
            properties.load(new FileInputStream(fileName));
//            String address = frm.getTxtAddress().getText();
            String port = frm.getTxtPort().getText().trim();
            String maxClients = frm.getTxtMaxClients().getText().trim();

//            properties.setProperty(MyServerConstants.SERVER_ADDRESS, address);
            properties.setProperty(MyServerConstants.SERVER_PORT, port);
            properties.setProperty(MyServerConstants.MAX_CONNECTED_CLIENTS, maxClients);
            Path path = Paths.get(fileName);
            File file = new File(fileName);
            if (!file.exists()) {
                Files.createFile(path);
            };
            properties.store(new FileOutputStream(file), null);
            sb.append(frm.getTitle()).append(" saved!\n\n")
                    .append(MyServerConstants.SERVER_PORT +" should be updated on the client side for client to function!");
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
        //address
        if (frm.getTxtAddress().getText().isEmpty()) {
            frm.getLblAddresslError().setText("Address can not be empty!");
            sb.append("Host address can not be empty!\n");
        }
        //port:start
        if (frm.getTxtPort().getText().isEmpty()) {
            frm.getLblPortError().setText("Port can not be empty!");
            sb.append("Port can not be empty!\n");
        }
        try {
            int port = Integer.valueOf(frm.getTxtPort().getText().toString().trim());
            if (port < 1) {
                frm.getLblPortError().setText("Port must be a positive number!\n");
                sb.append("Port must be a positive number!\n");
            }
        } catch (NumberFormatException numberFormatException) {
            frm.getLblPortError().setText("Port must be a number!\n");
            sb.append("Port must be a number!\n");
        }
        //port:end
        //maxClients:start
        if (frm.getTxtMaxClients().getText().isEmpty()) {
            frm.getLblMaxClientsError().setText("Max number of connected clients can not be empty!");
            sb.append("Max number of connected clients can not be empty!\n");
        }
        try {
            int maxClients = Integer.valueOf(frm.getTxtMaxClients().getText().toString().trim());
            if (maxClients < 1) {
                frm.getLblMaxClientsError().setText("Max number of connected clients must be a positive number!\n");
                sb.append("Max number of connected clients must be a positive number!\n");
            }
        } catch (NumberFormatException numberFormatException) {
            frm.getLblMaxClientsError().setText("Max number of connected clients must be a number!\n");
            sb.append("Max number of connected clients must be a number!\n");
        }
        //maxClients:end
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
        frm.getLblAddresslError().setText("");
        frm.getLblPortError().setText("");
    }
}
