/*
 * To change frm license header, choose License Headers in Project Properties.
 * To change frm template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller.impl;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Line;
import rs.ac.bg.fon.ps.domain.Station;
import rs.ac.bg.fon.ps.view.form.component.table.TableModelLine;
import rs.ac.bg.fon.ps.view.controller.ViewController;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmViewLines;
import rs.ac.bg.fon.ps.exception.TableModelException;
import rs.ac.bg.fon.ps.exception.UseCaseException;
import rs.ac.bg.fon.ps.exception.ValidationException;
import rs.ac.bg.fon.ps.util.JTableUtilities;
import rs.ac.bg.fon.ps.util.MyStringBuilder;
import static rs.ac.bg.fon.ps.util.constant.MapParams.CURRENT_LINE;
import rs.ac.bg.fon.ps.util.constant.State;
import rs.ac.bg.fon.ps.util.constant.UseCaseConstants;

/**
 *
 * @author Vladimir Jecić
 */
public class ViewLinesController extends ViewController<FrmViewLines> {

    public ViewLinesController(FrmViewLines frmViewLines) {
        super(frmViewLines);
    }

    @Override
    protected void prepareView() throws Exception {
        frm.setLocationRelativeTo(frm.getParent());
        frm.getTblLine().requestFocusInWindow();
        setupForm();
        List<Line> lineList = new ArrayList();
        Communication.getInstance().getLineList(lineList);
        for (Line line : lineList) {
            Collections.sort(line.getStops());
        }
        prepareTableLines(lineList);
        fillCbDepartureAndDestination(lineList);
    }

    @Override
    protected void addActionListeners() {
        frm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frm.dispose();
            }
        });
        frm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                refreshLinesViewForm();
            }

            public void refreshLinesViewForm() {
                if (frm != null) {
                    refreshTableData();
                }
            }
        });
        frm.applyFiltersViewLinesActionListener(e -> applyFilters());
        frm.clearFiltersViewLinesActionListener(e -> clearFilters());
        frm.editLineViewLinesActionListener(e -> editLine());
        frm.deleteLineViewLinesActionListener(e -> deleteLine());
        frm.cancelViewLinesActionListener(e ->frm.dispose());
        frm.getTblLine().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (frm.getTblLine().getSelectedRow() == -1) {
                    frm.getTblLine().setRowSelectionInterval(0, 0);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
    }

    private void fillCbDepartureAndDestination(List<Line> lines) {
        List<Station> departures = new ArrayList<>();
        List<Station> destinations = new ArrayList<>();

        for (Line line : lines) {
            if (!departures.contains(line.getFirstStation())) {
                departures.add(line.getFirstStation());
            }
            if (!destinations.contains(line.getLastStation())) {
                destinations.add(line.getLastStation());
            }
        }
        frm.getCbDeparture().setModel(new DefaultComboBoxModel(departures.toArray()));
        frm.getCbDestination().setModel(new DefaultComboBoxModel(destinations.toArray()));
    }

    private void prepareTableLines(List<Line> lines) {
        TableModelLine model = new TableModelLine(lines);
        frm.getTblLine().setModel(model);
        JTableUtilities.setCellAlignment(frm.getTblLine(), SwingConstants.CENTER);
        JTableUtilities.resizeColumnWidth(frm.getTblLine());
        frm.getTblLine().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    }

    public void refreshTableData() {
        ((TableModelLine) frm.getTblLine().getModel()).refreshView();
    }

    private void setupForm() throws UseCaseException {
        switch (useCase) {
            case EDIT_LINE:
            case NEW_DEPARTURE:
            case DELETE_DEPARTURE:
            case NEW_TICKET:
            case DELETE_TICKET:
                frm.getBtnDeleteLine().setVisible(false);
                break;
            case DELETE_LINE:
                frm.getBtnEditLine().setVisible(false);
                break;
            default:
                throw new UseCaseException("Illegal UseCase:" + useCase);

        }
    }

    private void applyFilters() {
        List<Line> lineList = new ArrayList();
        TableModelLine model = (TableModelLine) frm.getTblLine().getModel();
        try {
            validateForm();
            String where = getApplyFiltersWhere();
            String message = Communication.getInstance().findLines(where, lineList);
            JOptionPane.showMessageDialog(frm, message, useCase.getName(), JOptionPane.INFORMATION_MESSAGE);
            model.setLineList(lineList);
        } catch (Exception e) {
//            e.printStackTrace();
            JOptionPane.showMessageDialog(frm, e.getMessage(), useCase.getName(), JOptionPane.ERROR_MESSAGE);
        }

    }
    
    private void clearFilters() {
        List<Line> lineList = new ArrayList();
        TableModelLine model = (TableModelLine) frm.getTblLine().getModel();
        try {
            frm.getCheckBoxName().setSelected(false);
            frm.getCheckBoxDeparture().setSelected(false);
            frm.getCheckBoxDestination().setSelected(false);
            String where = getClearFiltersWhere();
            String message = Communication.getInstance().findLines(where, lineList);
            JOptionPane.showMessageDialog(frm, message, useCase.getName(), JOptionPane.INFORMATION_MESSAGE);
            model.setLineList(lineList);
        } catch (Exception e) {
//            e.printStackTrace();
            JOptionPane.showMessageDialog(frm, e.getMessage(), useCase.getName(), JOptionPane.ERROR_MESSAGE);
        }

    }


    private void editLine() {
        TableModelLine model = (TableModelLine) frm.getTblLine().getModel();
        int selectedRow = frm.getTblLine().getSelectedRow();//returns -1 if no row is selected
        try {
            if (selectedRow != -1) {
                Line line = model.getLineAt(selectedRow);
                Communication.getInstance().getDeparturesForLine(line);
                if(useCase.equals(UseCaseConstants.DELETE_DEPARTURE) && line.getDepartures().isEmpty()){
                    throw new Exception("There are no more departures left on this line!");
                }
                MainCordinator.getInstance().putParam(CURRENT_LINE, line);
                MainCordinator.getInstance().openLineForm();

            } else {
                throw new TableModelException("Couldn't complete operation: No Rows Selected");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frm, e.getMessage(), useCase.getName(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteLine() {
        if (!confirmDelete()) {
            return;
        }
        TableModelLine model = (TableModelLine) frm.getTblLine().getModel();
        int selectedRow = frm.getTblLine().getSelectedRow();
        Line toDelete= null;
        try {
            if (selectedRow != -1) {
                toDelete = model.getLineAt(selectedRow);
                toDelete.setState(State.REMOVED);
                String message = Communication.getInstance().deleteLine(toDelete);
                model.removeLineAt(selectedRow);
                JOptionPane.showMessageDialog(frm,message, useCase.getName(),JOptionPane.INFORMATION_MESSAGE);
                cancelForm();
            } else {
                throw new TableModelException("Couldn't complete operation: No Rows Selected");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            toDelete.resetState();
            JOptionPane.showMessageDialog(frm,ex.getMessage(), useCase.getName(),JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void validateForm() throws ValidationException {
        String errorMessage = "";
        if(!frm.getCheckBoxName().isSelected() && !frm.getCheckBoxDeparture().isSelected() && !frm.getCheckBoxDestination().isSelected()){
            errorMessage+="\nAt least one of filters must be selected!\n";
        }
        if (frm.getCheckBoxName().isSelected()) {
            String lineName = frm.getTxtLineName().getText();
            if(lineName.isEmpty()){
            errorMessage += "\nLine Name can not be empty!\n";
            }
            if(lineName.equals("\\")){
                 errorMessage += "\nForbidden search parameter for line name,try searching by some other part of the name!\n";
            }
        }
        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }



    private String getApplyFiltersWhere() {
        String lineName = "";
        String firstStationID = "";
        String lastStationID = "";
        boolean filterOneSelected = frm.getCheckBoxName().isSelected();
        boolean filterTwoSelected = frm.getCheckBoxDeparture().isSelected();
        boolean filterThreeSelected = frm.getCheckBoxDestination().isSelected();
        if (filterOneSelected) {
            lineName = frm.getTxtLineName().getText();
        }
        if (filterTwoSelected) {
            Station firstStation = (Station) frm.getCbDeparture().getSelectedItem();
            firstStationID = String.valueOf(firstStation.getStationID());
        }
        if (filterThreeSelected) {
            Station lastStation = (Station) frm.getCbDestination().getSelectedItem();
            lastStationID = String.valueOf(lastStation.getStationID());
        }
        return MyStringBuilder.concatStrings("line.lineName LIKE CONCAT('%','" + lineName + "','%')",
                " AND line.firstStationID LIKE CONCAT('%','",firstStationID,"','%')",
                " AND line.lastStationID LIKE CONCAT('%','",lastStationID,"','%')");
    }
    private String getClearFiltersWhere() {
        return "line.lineName LIKE CONCAT('%','','%') AND line.firstStationID LIKE CONCAT('%','','%') AND line.lastStationID LIKE CONCAT('%','','%')";
    }

}
