/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller.impl;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Employee;
import rs.ac.bg.fon.ps.view.controller.ViewController;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmLogin;
import rs.ac.bg.fon.ps.exception.ValidationException;
import static rs.ac.bg.fon.ps.util.constant.MapParams.CURRENT_USER;
import rs.ac.bg.fon.ps.logger.MyLogger;

/**
 *
 * @author Vladimir Jecić
 */
public class LoginController extends ViewController<FrmLogin> {

    public LoginController(FrmLogin frm) {
        super(frm);
    }

    @Override
    protected void addActionListeners() {
        frm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancelForm();
            }
        });
        frm.loginActionListener(e -> loginUser());
    }

    @Override
    protected void prepareView() throws Exception {
        frm.getLblUserNameError().setForeground(Color.RED);
        frm.getLblPasswordError().setForeground(Color.RED);
        frm.setLocationRelativeTo(null);
    }

    @Override
    public void cancelForm() {
        frm.dispose();
        MyLogger.getLogger(this.getClass()).log(Level.INFO, "Client Program ended by User:OK");
        System.exit(0);
    }

    private void loginUser() {
        try {
            resetForm();
            validateForm();
            Employee employee = new Employee();
            employee.setUserName(frm.getTxtUsername().getText().trim());
            employee.setPassword(String.valueOf(String.valueOf(frm.getTxtPassword().getPassword())));
            String message = Communication.getInstance().loginEmployee(employee);
            MainCordinator.getInstance().putParam(CURRENT_USER, employee);
            JOptionPane.showMessageDialog(frm, message, useCase.getName(), JOptionPane.INFORMATION_MESSAGE);
            frm.dispose();
            MainCordinator.getInstance().openMainForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frm, ex.getMessage(), useCase.getName(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        frm.getLblUserNameError().setText("");
        frm.getLblPasswordError().setText("");
    }

    @Override
    protected void validateForm() throws ValidationException {
        StringBuilder sb = new StringBuilder();
        if (frm.getTxtUsername().getText().isEmpty()) {
            frm.getLblUserNameError().setText("Username can not be empty!");
            sb.append("\nUsername can not be empty!\n");
        }
        if (String.valueOf(frm.getTxtPassword().getPassword()).isEmpty()) {
            frm.getLblPasswordError().setText("Password can not be empty!");
            sb.append("\nPassword can not be empty!\n");
        }
        String errorMessage = sb.toString();
        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }

}
