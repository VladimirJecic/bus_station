/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import rs.ac.bg.fon.ps.logger.MyLogger;
import rs.ac.bg.fon.ps.thread.RefreshTableThread;
import rs.ac.bg.fon.ps.thread.ServerThread;
import rs.ac.bg.fon.ps.util.JTableUtilities;
import rs.ac.bg.fon.ps.util.constant.MapParams;
import rs.ac.bg.fon.ps.view.form.FrmDbConfiguration;
import rs.ac.bg.fon.ps.view.form.FrmMain;
import rs.ac.bg.fon.ps.view.form.FrmServerConfiguration;
import rs.ac.bg.fon.ps.view.form.component.table.TableModelClient;

/**
 *
 * @author Vladimir Jecić
 */
public class MainController {

    FrmMain frm;
    ServerThread serverThread;
    RefreshTableThread refreshTableThread;
    public static MainController instance;
    Map<MapParams, Object> params;

    private MainController(FrmMain frm) {
        this.frm = frm;
        params = new HashMap<>();
        addActionListeners();
    }

    public static MainController getInstance() {
        if (instance == null) {
            instance = new MainController(new FrmMain());
        }
        return instance;
    }

    private void openForm() {
        try {
            frm.setTitle("Bus Station Server");
            prepareView();
            frm.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog((Component) frm, "Error in view initialization\n" + e.getMessage(), frm.getTitle(), JOptionPane.ERROR_MESSAGE);
            frm.dispose();
        }
    }

    protected void addActionListeners() {
        frm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancelForm();
            }

        });
        frm.jmiStartServerActionListener(e -> startServer());
        frm.jmiStopServerActionListener(e -> stopServer());
        frm.jmiDatabaseConfiguration(e -> openDbConfigurationForm());
        frm.jmiServerConfiguration(e -> openServerConfigurationForm());

    }

    private void prepareView() throws Exception {
        TableModelClient model = new TableModelClient();
        frm.setLocationRelativeTo(null);
        frm.getTblClient().setModel(model);
        frm.getJmiStartServer().setBackground(java.awt.Color.GREEN);
        frm.getJmiStopServer().setEnabled(false);
        JTableUtilities.setCellAlignment(frm.getTblClient(), SwingConstants.CENTER);
        startRefreshTable();
    }

    private void cancelForm() {
        int input = JOptionPane.showConfirmDialog(frm, "Cancel " + frm.getTitle() + "?", "Select an Option...",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        // 0=yes, 1=no, -1 = x clicked
        if (input == JOptionPane.YES_OPTION) {
            stopRefreshTable();
            stopServer();
            frm.dispose();
            System.exit(0);
        }
    }

    private void startServer() {
        if (serverThread == null || !serverThread.isAlive()) {
            serverThread = new ServerThread();
            serverThread.setDaemon(true);
            serverThread.start();
            MyLogger.getLogger(this.getClass()).log(Level.INFO, "ServerThread STARTED!");
            frm.getJmiStartServer().setEnabled(false);
            frm.getJmiStartServer().setBackground(new java.awt.Color(240, 240, 240));
            frm.getJmiStopServer().setBackground(java.awt.Color.RED);
            frm.getJmiStopServer().setEnabled(true);
            frm.getJmiDatabaseConfiguration().setEnabled(false);
            frm.getJmiServerConfiguration().setEnabled(false);
            frm.getLblStatus().setText("Status: running");
        }
    }

    private void stopServer() {
        if (serverThread != null && serverThread.isAlive()) {
            serverThread.stopServer();
//            serverThread.interrupt();
            frm.getJmiStopServer().setEnabled(false);
            frm.getJmiStartServer().setBackground(java.awt.Color.GREEN);
            frm.getJmiStopServer().setBackground(new java.awt.Color(240, 240, 240));
            frm.getJmiStartServer().setEnabled(true);
            frm.getJmiDatabaseConfiguration().setEnabled(true);
            frm.getJmiServerConfiguration().setEnabled(true);
            frm.getLblStatus().setText("Status: stopped");
        }
    }

    private void stopRefreshTable() {
        if (refreshTableThread != null && refreshTableThread.isAlive()) {
            refreshTableThread.stopRTThread();
        }
    }

    private void startRefreshTable() {
        if (refreshTableThread == null || !refreshTableThread.isAlive()) {
            refreshTableThread = new RefreshTableThread(frm.getTblClient());
            refreshTableThread.start();
        }
    }

    public void startApp() {
        openForm();
    }

    public void putParam(MapParams key, Object value) {
        params.put(key, value);
    }

    public Object getParam(MapParams key) {
        return params.get(key);
    }

    private void openDbConfigurationForm() {
        DbConfigurationController controller = new DbConfigurationController(new FrmDbConfiguration(frm, true));
        controller.openForm();
    }

    private void openServerConfigurationForm() {
        ServerConfigurationController controller = new ServerConfigurationController(new FrmServerConfiguration(frm, true));
        controller.openForm();
    }

}
