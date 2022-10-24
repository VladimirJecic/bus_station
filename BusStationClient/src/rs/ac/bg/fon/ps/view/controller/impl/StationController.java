/*
 * To change frm license header, choose License Headers in Project Properties.
 * To change frm template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller.impl;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Station;
import rs.ac.bg.fon.ps.view.form.component.table.TableModelStation;
import rs.ac.bg.fon.ps.view.controller.ViewController;
import rs.ac.bg.fon.ps.view.form.FrmStation;
import rs.ac.bg.fon.ps.exception.TableModelException;
import rs.ac.bg.fon.ps.exception.UseCaseException;
import rs.ac.bg.fon.ps.exception.ValidationException;
import rs.ac.bg.fon.ps.util.JTableUtilities;
import rs.ac.bg.fon.ps.util.MyStringBuilder;
import rs.ac.bg.fon.ps.util.constant.State;
import rs.ac.bg.fon.ps.util.constant.UseCaseConstants;

/**
 *
 * @author Vladimir Jecić
 */
public class StationController extends ViewController<FrmStation> {

    public StationController(FrmStation frm) {
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
        frm.filterStationActionListener(e -> {
            if (((ItemEvent) e).getStateChange() == ItemEvent.SELECTED) {
                applyFilter();
            }
        });
        frm.saveStationActionListener(e -> saveStation());
        frm.deleteStationActionListener(e -> deleteStation());
        frm.cancelStationActionListener(e -> cancelForm());

    }

    @Override
    protected void prepareView() throws Exception {
        frm.setLocationRelativeTo(frm.getParent());
        frm.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frm.getLblStationNameError().setForeground(Color.RED);
        setupForm();

    }

    private Station getStation() {
        Station station = new Station();
        station.setStationName(frm.getTxtStationName().getText().trim());
        station.setState(State.NEW);
        return station;
    }

    private void prepareTableStation() throws Exception {
        List<Station> stations = new ArrayList();
        Communication.getInstance().getStationList(stations);
        TableModelStation model = new TableModelStation(stations);
        frm.getTblStation().setModel(model);
        JTableUtilities.setCellAlignment(frm.getTblStation(), SwingConstants.CENTER);
        frm.getTblStation().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    private void applyFilter() {
        List<Station> stationList = new ArrayList<>();
        try {
            frm.getLblStationNameError().setText("");
            validateForm();
            String where = getWhere();
            String message = Communication.getInstance().findStations(where, stationList);
            TableModelStation model = (TableModelStation) frm.getTblStation().getModel();
            model.setStationList(stationList);
            JOptionPane.showMessageDialog(frm, message, useCase.getName(), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frm, ex.getMessage(), useCase.getName(), JOptionPane.ERROR_MESSAGE);
        }

    }

    private void deleteStation() {
        if (!confirmDelete()) {
            return;
        }
        TableModelStation model = (TableModelStation) frm.getTblStation().getModel();
        int selectedRow = frm.getTblStation().getSelectedRow();
        Station removed = null;
        try {
            if (selectedRow != -1) {
                removed = model.getStationAt(selectedRow);
                removed.setState(State.REMOVED);
                String message = Communication.getInstance().deleteStation(removed);
                model.removeStationAt(selectedRow);
                JOptionPane.showMessageDialog(frm, message, useCase.getName(), JOptionPane.INFORMATION_MESSAGE);
                cancelForm();
            } else {
                throw new TableModelException("Couldn't complete operation: No Rows Selected");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            if(removed!=null){removed.resetState();}
            JOptionPane.showMessageDialog(frm, ex.getMessage(), useCase.getName(), JOptionPane.ERROR_MESSAGE);
        }

    }

    private void saveStation() {
        try {
            if (!confirmSave()) {
                return;
            };
            frm.getLblStationNameError().setText("");
            validateForm();
            Station station = getStation();
            String message = Communication.getInstance().saveStation(station);
            JOptionPane.showMessageDialog(frm, message, useCase.getName(), JOptionPane.INFORMATION_MESSAGE);
            addMoreStations();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frm,
                    e.getMessage(), useCase.getName(),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addMoreStations() {
        int input = JOptionPane.showConfirmDialog(frm, "Add more Stations?", "Question",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        // 0=yes, 1=no
        if (input == 0) {
            frm.getTxtStationName().setText("");
            frm.getTxtStationName().grabFocus();
        } else {
            frm.dispose();
        }
    }

    @Override
    protected void validateForm() throws ValidationException {
        String errorMessage = "";
        if (useCase.equals(UseCaseConstants.NEW_STATION) && frm.getTxtStationName().getText().isEmpty()) {
            frm.getLblStationNameError().setText("Station Name can not be empty!");
            errorMessage += "\nStation Name can not be empty!\n";
        }

        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }

    private void setupForm() throws UseCaseException, Exception {
        switch (useCase) {
            case NEW_STATION:
                frm.getScPaneStation().setVisible(false);
                frm.getCbFilterStation().setVisible(false);
                frm.getBtnDelete().setVisible(false);
                frm.getBtnSave().setVisible(true);
                break;
            case DELETE_STATION:
                prepareTableStation();
                frm.getLblStationName().setVisible(false);
                frm.getBtnSave().setVisible(false);
                break;
            default:
                throw new UseCaseException("Illegal UseCase:" + useCase);
        }
    }

    private String getWhere() {
        String stationName = "";
        stationName = frm.getTxtStationName().getText();
        return MyStringBuilder.concatStrings("station.stationName LIKE CONCAT('%','", stationName, "','%')");
    }

}
