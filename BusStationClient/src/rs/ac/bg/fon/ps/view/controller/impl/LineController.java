/*
 * To change frm license header, choose License Headers in Project Properties.
 * To change frm template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller.impl;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Departure;
import rs.ac.bg.fon.ps.domain.Employee;
import rs.ac.bg.fon.ps.domain.Line;
import rs.ac.bg.fon.ps.domain.Station;
import rs.ac.bg.fon.ps.domain.Stop;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.view.form.component.table.TableModelDeparture;
import rs.ac.bg.fon.ps.view.form.component.table.TableModelStop;
import rs.ac.bg.fon.ps.view.controller.ViewController;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmLine;
import rs.ac.bg.fon.ps.exception.TableModelException;
import rs.ac.bg.fon.ps.exception.UseCaseException;
import rs.ac.bg.fon.ps.exception.ValidationException;
import rs.ac.bg.fon.ps.exception.constraint.StructureConstraintException;
import rs.ac.bg.fon.ps.util.DateUtilities;
import rs.ac.bg.fon.ps.util.JTableUtilities;
import static rs.ac.bg.fon.ps.util.constant.MapParams.CURRENT_DEPARTURE;
import static rs.ac.bg.fon.ps.util.constant.MapParams.CURRENT_LINE;
import static rs.ac.bg.fon.ps.util.constant.MapParams.CURRENT_USER;
import rs.ac.bg.fon.ps.util.constant.State;
import rs.ac.bg.fon.ps.util.constant.UseCaseConstants;
import static rs.ac.bg.fon.ps.util.constant.UseCaseConstants.NEW_LINE;
import rs.ac.bg.fon.ps.util.MyStringBuilder;

/**
 *
 * @author Vladimir Jecić
 */
public class LineController extends ViewController<FrmLine> {

    public LineController(FrmLine frm) {
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
        frm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                refreshLineForm();
            }

            private void refreshLineForm() {
                if (frm != null) {
                    refreshTableData();
                }

            }
        });
        frm.addStopActionListener(e -> addStop());
        frm.editStopActionListener(e -> editStop());
        frm.deleteStopActionListener(e -> deleteStop());
        frm.saveLineActionListener(e -> save());
        frm.cancelLineActionListener(e -> cancelForm());
        frm.addDepartureActionListener(e -> addDeparture());
        frm.editDepartureActionListener(e -> editDeparture());
        frm.deleteDepartureActionListener(e -> deleteDeparture());
        frm.addTicketActionListener(e -> addTicket());
        frm.deleteTicketActionListener(e -> deleteTicket());
        frm.cbDepartureActionListener(itemEvent -> {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                Station station = (Station) itemEvent.getItem();
                setFirstStop(station);
            }
        });
        frm.cbDestinationActionListener(itemEvent -> {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                Station station = (Station) itemEvent.getItem();
                setLastStop(station);
            }
        });
        frm.getTblDeparture().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (frm.getTblDeparture().getSelectedRow() == -1) {
                    frm.getTblDeparture().setRowSelectionInterval(0, 0);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });

    }

    @Override
    protected void prepareView() throws Exception {
        try {
            frm.setLocationRelativeTo(frm.getParent().getParent());//relative to mainform
            fillForm();
            setupForm(useCase);

        } catch (Exception ex) {
            throw ex;
        }

    }

    @Override
    protected void validateForm() throws ValidationException {
        StringBuilder sb = new StringBuilder();
        if (frm.getTxtName().getText().isEmpty()) {
            Station departureStation = (Station) frm.getCbDeparture().getSelectedItem();
            Station arrivalStation = (Station) frm.getCbDestination().getSelectedItem();
            if (departureStation != null && arrivalStation != null) {
                frm.getTxtName().setText(MyStringBuilder.concatStrings(departureStation.getStationName(), "->", arrivalStation.getStationName()));
            } else {
                sb.append("\nLine Name can not be empty!\n");
            }
        }
        if (frm.getTxtDistance().getText().isEmpty()) {
            sb.append("\nDistance can not be empty!\n");
        }
        if (frm.getTxtTravelTime().getText().isEmpty() && frm.getTblDeparture().getRowCount() == 0) {
            sb.append("\nExpected Travel Time can not be empty!\n");
        }
        if (frm.getCbDeparture().getSelectedItem() == null) {
            sb.append("\nDeparture Station can not be empty!\n");
        }
        if (frm.getCbDestination().getSelectedItem() == null) {
            sb.append("\nDestination Station can not be empty!\n");
        }
        String errorMessage = sb.toString();
        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }

    private void fillForm() throws ParseException, Exception {
        Line lineCopy = getLineCopy();
        List<Station> stations = new ArrayList();
        String message = Communication.getInstance().getStationList((List<?>) stations);
        fillCbDeparture(stations);
        fillCbDestination(stations);
        prepareTableStop(lineCopy);
        prepareTableDeparture(lineCopy);
        prepareTableStopColumnStation(stations);
        prepareLineFieldsAndComboBoxes(lineCopy);
        if (lineCopy == null) {
            MainCordinator.getInstance().putParam(CURRENT_LINE, new Line());
        }

    }

    private void prepareTableStop(Line currentLine) {
        JTableUtilities.resizeColumnWidth(frm.getTblStop());
        TableModelStop modelStop = null;
        if (currentLine == null) {
            modelStop = new TableModelStop();
        } else {
            List<Stop> stops = currentLine.getStops();
            modelStop = new TableModelStop(stops);
        }
        frm.getTblStop().setModel(modelStop);
        JTableUtilities.setCellAlignment(frm.getTblStop(), SwingConstants.CENTER);

    }

    private void prepareTableDeparture(Line currentLine) throws Exception {
        JTableUtilities.resizeColumnWidth(frm.getTblDeparture());
        TableModelDeparture modelDeparture = null;
        if (currentLine == null) {
            modelDeparture = new TableModelDeparture();
        } else {
            List<Departure> departures = currentLine.getDepartures();
            if (useCase.equals(UseCaseConstants.NEW_TICKET) | useCase.equals(UseCaseConstants.DELETE_TICKET)) {
                if (departures.isEmpty()) {
                    throw new Exception("Cannot add or delete tickets from line with no departures!");
                }
            }
            modelDeparture = new TableModelDeparture(departures);
        }
        frm.getTblDeparture().setModel(modelDeparture);
        JTableUtilities.setCellAlignment(frm.getTblDeparture(), SwingConstants.CENTER);

    }

    private void setupForm(UseCaseConstants useCase) throws UseCaseException, UseCaseException {
        frm.getTxtUser().setEditable(false);//because program should set field after change
        frm.getTblStop().getSelectionModel().setSelectionMode(SINGLE_SELECTION);
        frm.getTblDeparture().getSelectionModel().setSelectionMode(SINGLE_SELECTION);
        String tipEmptyName = "<html><font face =\"sansserif\" color=\"green\">Can be left empty, if Departure and Destination stops are selected.<br>On save,this field will be automatically filled in format \"DepartureStop -> DestinationStop\"";
        String tipEmptyTravelTime = "<html><font face =\"sansserif\" color=\"green\">Can be left empty if there are departures in table.<br>On save,this field will be automatically filled with mean travel time of all departures";
        frm.getTxtName().setToolTipText(tipEmptyName);
        frm.getTxtTravelTime().setToolTipText(tipEmptyTravelTime);
        switch (useCase) {
            case NEW_LINE:
                //not-visible
                frm.getLblUser().setVisible(false);
                frm.getTxtUser().setVisible(false);
                //not-enabled
                frm.getBtnAddStop().setEnabled(false);
                frm.getBtnEditStop().setEnabled(false);
                frm.getBtnDeleteStop().setEnabled(false);
                frm.getBtnAddTicket().setEnabled(false);
                frm.getBtnDeleteTicket().setEnabled(false);
                break;
            case EDIT_LINE:
                //not-enabled
                if (frm.getTblDeparture().getRowCount() > 0) {
                    frm.getTblDeparture().requestFocusInWindow();
                }
                frm.getBtnAddTicket().setEnabled(false);
                frm.getBtnDeleteTicket().setEnabled(false);
                frm.getBtnAddDeparture().setEnabled(false);
                frm.getBtnDeleteDeparture().setEnabled(false);
                //user-friendly
                frm.getTxtName().setSelectionStart(0);
                break;
            case NEW_DEPARTURE:
                //not-editable
                frm.getTxtName().setEditable(false);
                frm.getTxtDistance().setEditable(false);
                frm.getTxtTravelTime().setEditable(false);
                frm.getTxtUser().setEditable(false);
                //not-enabled
                frm.getCbDeparture().setEnabled(false);
                frm.getCbDestination().setEnabled(false);
                frm.getBtnAddStop().setEnabled(false);
                frm.getBtnEditStop().setEnabled(false);
                frm.getBtnDeleteStop().setEnabled(false);
                frm.getBtnEditDeparture().setEnabled(false);
                frm.getBtnDeleteDeparture().setEnabled(false);
                frm.getBtnAddTicket().setEnabled(false);
                frm.getBtnDeleteTicket().setEnabled(false);
                break;
            case DELETE_DEPARTURE:
                frm.getTblDeparture().requestFocusInWindow();
                //not-editable
                frm.getTxtName().setEditable(false);
                frm.getTxtDistance().setEditable(false);
                frm.getTxtTravelTime().setEditable(false);
                frm.getTxtUser().setEditable(false);
                //not-enabled
                frm.getBtnSave().setEnabled(false);
                frm.getCbDeparture().setEnabled(false);
                frm.getCbDestination().setEnabled(false);
                frm.getBtnAddStop().setEnabled(false);
                frm.getBtnEditStop().setEnabled(false);
                frm.getBtnDeleteStop().setEnabled(false);
                frm.getBtnAddDeparture().setEnabled(false);
                frm.getBtnEditDeparture().setEnabled(false);
                frm.getBtnAddTicket().setEnabled(false);
                frm.getBtnDeleteTicket().setEnabled(false);
                break;
            case NEW_TICKET:
                frm.getTblDeparture().requestFocusInWindow();
                //not-editable
                frm.getTxtName().setEditable(false);
                frm.getTxtDistance().setEditable(false);
                frm.getTxtTravelTime().setEditable(false);
                frm.getTxtUser().setEditable(false);
                //not-enabled
                frm.getBtnSave().setEnabled(false);
                frm.getCbDeparture().setEnabled(false);
                frm.getCbDestination().setEnabled(false);
                frm.getBtnAddStop().setEnabled(false);
                frm.getBtnEditStop().setEnabled(false);
                frm.getBtnDeleteStop().setEnabled(false);
                frm.getBtnAddDeparture().setEnabled(false);
                frm.getBtnEditDeparture().setEnabled(false);
                frm.getBtnDeleteDeparture().setEnabled(false);
                frm.getBtnDeleteTicket().setEnabled(false);
                break;
            case DELETE_TICKET:
                frm.getTblDeparture().requestFocusInWindow();
                //not-editable
                frm.getTxtName().setEditable(false);
                frm.getTxtDistance().setEditable(false);
                frm.getTxtTravelTime().setEditable(false);
                frm.getTxtUser().setEditable(false);
                //not-enabled
                frm.getBtnSave().setEnabled(false);
                frm.getCbDeparture().setEnabled(false);
                frm.getCbDestination().setEnabled(false);
                frm.getBtnAddStop().setEnabled(false);
                frm.getBtnEditStop().setEnabled(false);
                frm.getBtnDeleteStop().setEnabled(false);
                frm.getBtnAddDeparture().setEnabled(false);
                frm.getBtnEditDeparture().setEnabled(false);
                frm.getBtnDeleteDeparture().setEnabled(false);
                frm.getBtnAddTicket().setEnabled(false);
                break;
            default:
                throw new UseCaseException("Illegal UseCase:" + useCase);

        }

    }

    private void fillCbDeparture(List<Station> stations) {
        frm.getCbDeparture().setModel(new DefaultComboBoxModel(stations.toArray()));
    }

    private void fillCbDestination(List<Station> stations) {
        frm.getCbDestination().setModel(new DefaultComboBoxModel(stations.toArray()));
    }

    private void prepareTableStopColumnStation(List<Station> stations) {
        JComboBox cbStation = new JComboBox(new DefaultComboBoxModel(stations.toArray()));
        TableColumn tcStation = frm.getTblStop().getColumnModel().getColumn(1);
        tcStation.setCellEditor(new DefaultCellEditor(cbStation));
    }

    private void addStop() {
        TableModelStop model = (TableModelStop) frm.getTblStop().getModel();
//        Line currentLine = (Line) MainCordinator.getInstance().getParam(CURRENT_LINE);
//        Line lineCopy = getLineCopy();
//        stop.setLine(lineCopy);
        model.addStop(new Stop());
    }

    private void editStop() {
        TableModelStop model = (TableModelStop) frm.getTblStop().getModel();
        int selectedRow = frm.getTblStop().getSelectedRow();
        try {
            if (selectedRow != -1) {
                model.editStopAt(selectedRow);
            } else {
                throw new TableModelException("Couldn't complete operation: No Rows Selected");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frm,
                    ex.getMessage(), "System couldn't edit stop\n",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStop() {
        TableModelStop model = (TableModelStop) frm.getTblStop().getModel();
        int selectedRow = frm.getTblStop().getSelectedRow();
        try {
            if (selectedRow != -1) {
                model.removeStopAt(selectedRow);
            } else {
                throw new TableModelException("Couldn't complete operation: No Rows Selected");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frm,
                    "System couldn't delete stop\n" + ex.getMessage(), useCase.getName(),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void save() {
        try {
            validateForm();
            Line toSave = getLineFromThisForm();
            if (toSave.getState().equals(State.NOT_CHANGED)) {
                throw new Exception("No changes on the Line detected!");
            }
            switch (useCase) {
                case NEW_LINE: {
                    if (!confirmSave()) {
                        break;
                    }
//                    line.getEmployee().setUserName("CLIENT");
                    String message = Communication.getInstance().saveLine(toSave);
                    JOptionPane.showMessageDialog(frm, message, useCase.getName(), JOptionPane.INFORMATION_MESSAGE);
                    toSave.resetState();
                    MainCordinator.getInstance().putParam(CURRENT_LINE, null);//because of setupForm in case of new Line
                    addMoreLines();
                    break;
                }
                case EDIT_LINE: {
                    if (!confirmEditLine()) {
                        break;
                    }
                    String message = Communication.getInstance().saveLine(toSave);
                    JOptionPane.showMessageDialog(frm, message, useCase.getName(), JOptionPane.INFORMATION_MESSAGE);
                    toSave.setStops(((TableModelStop) frm.getTblStop().getModel()).getExistingStops());
                    toSave.resetState();
                    updateCurrentLine(toSave);
                    frm.dispose();
                    break;
                }

                case NEW_DEPARTURE: {
                    if (!confirmSave()) {
                        break;
                    }
                    TableModelDeparture modelDepartures = (TableModelDeparture) frm.getTblDeparture().getModel();
                    String message = Communication.getInstance().saveDepartures(modelDepartures.getDepartureList());
                    JOptionPane.showMessageDialog(frm, message, useCase.getName(), JOptionPane.INFORMATION_MESSAGE);
                    toSave.resetState();
                    updateCurrentLine(toSave);
                    cancelForm();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frm, e.getMessage(), useCase.getName(),
                    JOptionPane.ERROR_MESSAGE);
//            frm.dispose(); not disposing because it is the last step of use case
        }
    }

    private void addDeparture() {
        TableModelDeparture model = (TableModelDeparture) frm.getTblDeparture().getModel();
        Departure departure = new Departure();
        model.addDeparture(departure);
    }

    private void editDeparture() {
        try {
            TableModelDeparture model = (TableModelDeparture) frm.getTblDeparture().getModel();
            int selectedRow = frm.getTblDeparture().getSelectedRow();
            if (selectedRow != -1) {
                model.editDepartureAt(selectedRow);
            } else {
                throw new TableModelException("Couldn't complete operation: No Rows Selected");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frm,
                    "System couldn't edit departure:\n" + ex.getMessage(), useCase.getName(),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteDeparture() {
        TableModelDeparture model = (TableModelDeparture) frm.getTblDeparture().getModel();
        int selectedRow = frm.getTblDeparture().getSelectedRow();
        try {
            if (selectedRow != -1) {
                if (!confirmDelete()) {
                    return;
                }
                Departure toDelete = model.getDepartureAt(selectedRow);
                if (useCase.equals(UseCaseConstants.DELETE_DEPARTURE)) {
                    toDelete.setState(State.REMOVED);
                    String message = Communication.getInstance().deleteDeparture(toDelete);
                    model.removeDepartureAt(selectedRow);
                    JOptionPane.showMessageDialog(frm, message, useCase.getName(), JOptionPane.INFORMATION_MESSAGE);
                    frm.getTxtTravelTime().setText(DateUtilities.calculateMeanLineTravelTime(toDelete.getLine()));
                    updateCurrentLine();
                    cancelForm();
                } else {
                    model.removeDepartureAt(selectedRow);
                }
            } else {
                throw new TableModelException("Couldn't complete operation: No Rows Selected");
            }
        } catch (Exception ex) {
            ex.getMessage();
            //there is no need to reset state of removed in case of SO failure,because we are working 
            //on deep copy of the line,which won't be updated if operation fails
            JOptionPane.showMessageDialog(frm, ex.getMessage(), useCase.getName(),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addTicket() {
        try {
            TableModelDeparture model = (TableModelDeparture) frm.getTblDeparture().getModel();
            int selectedRow = frm.getTblDeparture().getSelectedRow();
            if (selectedRow != -1) {
                Departure departure = model.getDepartureAt(selectedRow);
                departure.setLine((Line) MainCordinator.getInstance().getParam(CURRENT_LINE));
                if (departure.getMaxPassengerNumber() == departure.getPassengerNumber()) {
                    throw new Exception("New Tickets can't be added, all seats are already taken");
                }
                Communication.getInstance().getTicketsForDeparture(departure);
                MainCordinator.getInstance().putParam(CURRENT_DEPARTURE, departure);
                MainCordinator.getInstance().openTicketForm();
                updateCurrentLine();
                cancelForm();

            } else {
                throw new TableModelException("Couldn't complete operation: No Rows Selected");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frm, ex.getMessage(), useCase.getName(), JOptionPane.ERROR_MESSAGE);
            frm.dispose();
        }
    }

    private void deleteTicket() {
        try {
            TableModelDeparture model = (TableModelDeparture) frm.getTblDeparture().getModel();
            int selectedRow = frm.getTblDeparture().getSelectedRow();
            if (selectedRow != -1) {
                Departure departure = model.getDepartureAt(selectedRow);
                Communication.getInstance().getTicketsForDeparture(departure);
                MainCordinator.getInstance().putParam(CURRENT_DEPARTURE, departure);
                MainCordinator.getInstance().openTicketForm();
                updateCurrentLine();
                cancelForm();
            } else {
                throw new TableModelException("Couldn't complete operation: No Rows Selected");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frm, ex.getMessage(), useCase.getName(),
                    JOptionPane.ERROR_MESSAGE);
            frm.dispose();
        }

    }

    private void prepareLineFieldsAndComboBoxes(Line line) throws ParseException {
        if (line != null) {
            frm.getTxtName().setText(line.getLineName());
            frm.getTxtDistance().setText(String.valueOf(line.getDistance()));
            frm.getTxtTravelTime().setText(DateUtilities.calculateMeanLineTravelTime(line));
            frm.getTxtUser().setText(line.getEmployee().toString());
            frm.getCbDeparture().setSelectedItem(line.getFirstStation());
            frm.getCbDestination().setSelectedItem(line.getLastStation());
        } else {
//            frm.getCbDeparture().setSelectedItem(null);
//            frm.getCbDestination().setSelectedItem(null);
            frm.getCbDeparture().setSelectedIndex(-1);
            frm.getCbDestination().setSelectedIndex(-1);
            frm.getTxtName().setText("");
            frm.getTxtDistance().setText("");
            frm.getTxtTravelTime().setText("");
            frm.getTxtUser().setText("");
        }
    }

    private Line getLineCopy() {
        Line currentLine = (Line) MainCordinator.getInstance().getParam(CURRENT_LINE);
        return currentLine == null ? null : currentLine.copy();
    }

    private Line getLineFromThisForm() throws ParseException,NumberFormatException {
        TableModelStop tmStop = (TableModelStop) frm.getTblStop().getModel();
        TableModelDeparture tmDeparture = (TableModelDeparture) frm.getTblDeparture().getModel();
        Line line = new Line();
        line.setLineName(frm.getTxtName().getText());
        line.setDistance(new BigDecimal(frm.getTxtDistance().getText()));
        line.setFirstStation((Station) frm.getCbDeparture().getSelectedItem());
        line.setLastStation((Station) frm.getCbDestination().getSelectedItem());
        line.setEmployee((Employee) MainCordinator.getInstance().getParam(CURRENT_USER));
        List<Stop> stopList = tmStop.getAllStops();
        List<Departure> departureList = tmDeparture.getDepartureList();
        for (Stop stop : stopList) {
            stop.setLine(line);
        }
        for (Departure departure : departureList) {
            departure.setLine(line);
            for (Ticket ticket : departure.getTickets()) {
                ticket.setLine(line);
            }
        }
        line.setStops(stopList);
        line.setDepartures(departureList);

        String travelTime = frm.getTxtTravelTime().getText();
        if (travelTime.isEmpty() && departureList.size() > 0) {
            travelTime = DateUtilities.calculateMeanLineTravelTime(line);
            frm.getTxtTravelTime().setText(travelTime);
        }
        line.setTravelTime(travelTime);
        if (useCase.equals(UseCaseConstants.NEW_LINE)) {
            line.setState(State.NEW);
        } else {
            Line oldLine = (Line) MainCordinator.getInstance().getParam(CURRENT_LINE);
            line.setLineID(oldLine.getLineID());
            if (line.equals(oldLine)) {
                line.setState(State.NOT_CHANGED);
            } else {
                line.setState(State.CHANGED);
            }
        }
        return line;
    }

    private void updateCurrentLine(Line newLine) {
        Line oldLine = (Line) MainCordinator.getInstance().getParam(CURRENT_LINE);
        oldLine.paste(newLine.copy());
    }

    private void updateCurrentLine() throws ParseException {
        Line newLine = getLineFromThisForm();
        if (!newLine.getState().equals(State.NOT_CHANGED)) {
            updateCurrentLine(newLine);
        }

    }

    private void setFirstStop(Station station) {
        TableModelStop model = (TableModelStop) frm.getTblStop().getModel();
        model.setFirstStop(station);
        if (frm.getBtnAddStop().isEnabled() == false && model.getLastStop().getStation() != null) {
            frm.getBtnAddStop().setEnabled(true);
            frm.getBtnEditStop().setEnabled(true);
            frm.getBtnDeleteStop().setEnabled(true);
        }
    }

    private void setLastStop(Station station) {
        TableModelStop model = (TableModelStop) frm.getTblStop().getModel();
        model.setLastStop(station);
        if (frm.getBtnAddStop().isEnabled() == false && model.getFirstStop().getStation() != null) {
            frm.getBtnAddStop().setEnabled(true);
            frm.getBtnEditStop().setEnabled(true);
            frm.getBtnDeleteStop().setEnabled(true);
        }
    }

    public void refreshTableData() {
        ((TableModelDeparture) frm.getTblDeparture().getModel()).refreshView();
    }

    private void addMoreLines() throws Exception {
        MainCordinator.getInstance().putParam(CURRENT_LINE, null);
        int input = JOptionPane.showConfirmDialog(frm, "Add more lines?", "Select an Option...",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        // 0=yes, 1=no 
        if (input == 0) {
            fillForm();//this is necessary because state of all Departures and Stops need to be set to NOT_CHANGED
        } else {
            frm.dispose();
        };

    }

    private boolean confirmEditLine() {
        int input = JOptionPane.showConfirmDialog((Component) frm, "Save changes?", "Confirm operation",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        // 0=yes, 1=no, 2=cancel
        return input == 0;
    }

    @Override
    protected boolean confirmDelete() {
        int input = JOptionPane.showConfirmDialog((Component) frm, "Delete departure?", "Confirm operation",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        // 0=yes, 1=no, 2=cancel
        return input == 0;
    }
}
