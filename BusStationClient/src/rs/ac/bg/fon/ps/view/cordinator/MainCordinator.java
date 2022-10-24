/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.cordinator;

import java.util.HashMap;
import java.util.Map;
import rs.ac.bg.fon.ps.domain.Employee;
import rs.ac.bg.fon.ps.view.controller.ViewController;
import rs.ac.bg.fon.ps.view.controller.impl.LineController;
import rs.ac.bg.fon.ps.view.controller.impl.LoginController;
import rs.ac.bg.fon.ps.view.controller.impl.MainController;
import rs.ac.bg.fon.ps.view.controller.impl.StationController;
import rs.ac.bg.fon.ps.view.controller.impl.TicketController;
import rs.ac.bg.fon.ps.view.controller.impl.ViewLinesController;
import rs.ac.bg.fon.ps.view.form.FrmLine;
import rs.ac.bg.fon.ps.view.form.FrmLogin;
import rs.ac.bg.fon.ps.view.form.FrmMain;
import rs.ac.bg.fon.ps.view.form.FrmStation;
import rs.ac.bg.fon.ps.view.form.FrmTicket;
import rs.ac.bg.fon.ps.view.form.FrmViewLines;
import rs.ac.bg.fon.ps.util.constant.MapParams;
import static rs.ac.bg.fon.ps.util.constant.MapParams.CURRENT_DEPARTURE;
import static rs.ac.bg.fon.ps.util.constant.MapParams.CURRENT_LINE;
import static rs.ac.bg.fon.ps.util.constant.MapParams.CURRENT_USE_CASE;
import static rs.ac.bg.fon.ps.util.constant.UseCaseConstants.LOGIN_USER;

/**
 *
 * @author Vladimir Jecić
 */
public class MainCordinator {

//    CURRENT_USER, CURRENT_LINE, CURRENT_DEPARTURE,
    private static MainCordinator instance;

    private final MainController mainController;
    Map<MapParams, Object> params;

    private MainCordinator() {
        mainController = new MainController(new FrmMain());
        params = new HashMap<>();
    }

    public synchronized static MainCordinator getInstance() {
        if (instance == null) {
            instance = new MainCordinator();
        }
        return instance;
    }

    public void startApp() {
        openLoginForm();
    }

    public void openLoginForm() {
        params.put(CURRENT_USE_CASE, LOGIN_USER);
        ViewController controller = new LoginController(new FrmLogin());
        controller.openForm();

    }

    public void openMainForm() throws Exception {
        mainController.openForm();
    }

    public void openStationForm() {
        ViewController controller = new StationController(
                new FrmStation(mainController.getFrmMain(), true));
        controller.openForm();

    }

    public void openViewLinesForm() throws Exception {
        ViewController controller = new ViewLinesController(new FrmViewLines(mainController.getFrmMain(), true));
        controller.openForm();
        putParam(CURRENT_LINE, null);

    }

    public void openLineForm() throws Exception {
        ViewController controller = new LineController(new FrmLine(mainController.getFrmMain(), true));
        controller.openForm();
        putParam(CURRENT_LINE, null);

    }

    public void openTicketForm() throws Exception {
        ViewController controller = new TicketController(new FrmTicket(mainController.getFrmMain(), true));
        controller.openForm();
        putParam(CURRENT_DEPARTURE, null);
    }

    public void putParam(MapParams key, Object value) {
        params.put(key, value);
    }

    public Object getParam(MapParams key) {
        return params.get(key);
    }

}
