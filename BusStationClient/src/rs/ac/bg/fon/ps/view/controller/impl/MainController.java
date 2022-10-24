/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller.impl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.view.controller.ViewController;
import static javax.swing.SwingConstants.CENTER;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmMain;
import rs.ac.bg.fon.ps.exception.UseCaseException;
import rs.ac.bg.fon.ps.exception.ValidationException;
import rs.ac.bg.fon.ps.logger.MyLogger;
import static rs.ac.bg.fon.ps.util.constant.MapParams.CURRENT_USER;
import static rs.ac.bg.fon.ps.util.constant.MapParams.CURRENT_USE_CASE;
import rs.ac.bg.fon.ps.util.constant.PathConstants;
import rs.ac.bg.fon.ps.util.constant.UseCaseConstants;

/**
 *
 * @author Vladimir Jecić
 */
public class MainController extends ViewController<FrmMain> {

    public MainController(FrmMain frm) {
        super(frm, null);
    }

    @Override
    protected void addActionListeners() {
        frm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancelForm();
            }
        });
        frm.jmiStationNewActionListener(e -> setupForm(UseCaseConstants.NEW_STATION));
        frm.jmiStationDeleteActionListener(e -> setupForm(UseCaseConstants.DELETE_STATION));
        frm.jmiLineNewActionListener(e -> setupForm(UseCaseConstants.NEW_LINE));
        frm.jmiLineEditActionListener(e -> setupForm(UseCaseConstants.EDIT_LINE));
        frm.jmiLineDeleteActionListener(e -> setupForm(UseCaseConstants.DELETE_LINE));
        frm.jmiDepartureNewActionListener(e -> setupForm(UseCaseConstants.NEW_DEPARTURE));
        frm.jmiDepartureDeleteActionListener(e -> setupForm(UseCaseConstants.DELETE_DEPARTURE));
        frm.jmiTicketNewActionListener(e -> setupForm(UseCaseConstants.NEW_TICKET));
        frm.jmiTicketDeleteActionListener(e -> setupForm(UseCaseConstants.DELETE_TICKET));
        frm.btnLogOutActionListener(e -> cancelForm());
        frm.btnHelpActionListener(e -> displayHelp());

    }

    @Override
    protected void prepareView() throws Exception {
//**I prefer smaller/not centered main form, uncomment these 2 lines if different view is required
//        frm.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frm.setTitle("Main [logged user: " + MainCordinator.getInstance().getParam(CURRENT_USER) + "]");
        frm.getLblLogo().setVerticalAlignment(CENTER);
        frm.getBtnLogOut().setHorizontalAlignment(CENTER);
        ImageIcon icon = new javax.swing.ImageIcon(PathConstants.CLIENT_LOGO_JPG);
        frm.getLblLogo().setIcon(icon);
    }

    @Override
    public void cancelForm() {
        int input = JOptionPane.showConfirmDialog(frm, "You are about to log out, continue?", "Confirm Log Out",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        // 0=yes, 1=no, 2=cancel
        if (input == 0) {
            try {
                String message = Communication.getInstance().endCommunication();
                MyLogger.getLogger(this.getClass()).log(Level.INFO, message);
                frm.dispose();
                MainCordinator.getInstance().startApp();
            } catch (Exception ex) {
            ex.printStackTrace();
            MyLogger.getLogger(this.getClass()).log(Level.WARNING, "Couldn't end communication:", ex);
            }
        }
}

private void displayHelp() {
        File file = new File(PathConstants.CLIENT_HELP_TXT);
        String text = "";
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                text += sc.nextLine() + "\n";
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(frm, text, "System Use Cases", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setupForm(UseCaseConstants useCase) {
        MainCordinator.getInstance().putParam(CURRENT_USE_CASE, useCase);
        try {
            switch (useCase) {
                case NEW_STATION:
                case DELETE_STATION:
                    MainCordinator.getInstance().openStationForm();
                    break;
                case NEW_LINE:
                    MainCordinator.getInstance().openLineForm();
                    break;
                case EDIT_LINE:
                case DELETE_LINE:
                case NEW_DEPARTURE:
                case DELETE_DEPARTURE:
                case NEW_TICKET:
                case DELETE_TICKET:
                    MainCordinator.getInstance().openViewLinesForm();
                    break;
                default:
                    throw new UseCaseException("Illegal UseCase:" + useCase);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frm, ex.getMessage(), useCase.getName(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public FrmMain getFrmMain() {
        return frm;
    }

    @Override
        protected void validateForm() throws ValidationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
