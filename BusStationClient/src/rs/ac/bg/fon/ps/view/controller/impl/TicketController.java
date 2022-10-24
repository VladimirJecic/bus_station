/*
 * To change frm license header, choose License Headers in Project Properties.
 * To change frm template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller.impl;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Departure;
import rs.ac.bg.fon.ps.domain.Line;
import rs.ac.bg.fon.ps.domain.Station;
import rs.ac.bg.fon.ps.domain.Stop;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.view.form.component.table.TableModelTicket;
import rs.ac.bg.fon.ps.view.controller.ViewController;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmTicket;
import rs.ac.bg.fon.ps.exception.TableModelException;
import rs.ac.bg.fon.ps.exception.UseCaseException;
import rs.ac.bg.fon.ps.exception.ValidationException;
import rs.ac.bg.fon.ps.util.JTableUtilities;
import rs.ac.bg.fon.ps.util.constant.MapParams;
import static rs.ac.bg.fon.ps.util.constant.MapParams.CURRENT_DEPARTURE;
import static rs.ac.bg.fon.ps.util.constant.MapParams.CURRENT_LINE;
import rs.ac.bg.fon.ps.util.constant.State;

/**
 *
 * @author Vladimir Jecić
 */
public class TicketController extends ViewController<FrmTicket> {

    public TicketController(FrmTicket frmTicket) {
        super(frmTicket);
    }

    @Override
    protected void addActionListeners() {
        frm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancelForm();
            }
        });
        frm.saveTicketActionListener(e -> saveTicket());
        frm.deleteTicketActionListener(e -> deleteTicket());
        frm.cancelTicketActionListener(e -> cancelForm());
        frm.getSeatNumberActionListener(e -> findFirstEmptySeat());
    }

    @Override
    protected void prepareView() throws Exception {
        frm.setLocationRelativeTo(frm.getParent());
        fillTableColumnStations();
        switch (useCase) {
            case NEW_TICKET:
                prepareAddTicketForm();
                //not-editable
                frm.getTxtSeatNumber().setEditable(false);
                //not-visible
                frm.getScPaneTicket().setVisible(false);
                frm.getBtnDeleteTicket().setVisible(false);
                //better software usability by end-user
                frm.getTxtFirstName().grabFocus();
                break;
            case DELETE_TICKET:
                prepareDeleteTicketForm();
                if (frm.getTblTicket().getRowCount() > 0) {
                    frm.getTblTicket().getSelectionModel().setSelectionMode(SINGLE_SELECTION);
                    frm.getTblTicket().setRowSelectionInterval(0, 0);
                }
                //not-visible
                frm.getPanelAddTicket().setVisible(false);
                frm.getBtnSaveTicket().setVisible(false);
                break;
            default:
                throw new UseCaseException("Illegal UseCase:" + useCase);

        }

    }

    private void fillTableColumnStations() throws Exception {
        List<Station> stations = new ArrayList();
        String message = Communication.getInstance().getStationList(stations);
        JComboBox cbStation = new JComboBox(new DefaultComboBoxModel(stations.toArray()));
        TableColumn tc = frm.getTblTicket().getColumnModel().getColumn(3);
        tc.setCellEditor(new DefaultCellEditor(cbStation));
    }

    private void prepareAddTicketForm() {
        frm.getTxtSeatNumber().setText("");
        frm.getTxtFirstName().setText("");
        frm.getTxtLastName().setText("");
        frm.getLblSeatNumberError().setForeground(Color.red);
        frm.getLblFirstNameError().setForeground(Color.red);
        frm.getLblLastNameError().setForeground(Color.red);
        try {
            Line line = (Line) MainCordinator.getInstance().getParam(CURRENT_LINE);
            List<Stop> stops = line.getStops();
            frm.getCbLastStop().setModel(new DefaultComboBoxModel(stops.subList(1, stops.size()).toArray()));//because I don't want Departure station to be selectable
            frm.getCbLastStop().setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    if (value != null && value instanceof Stop) {
                        value = String.valueOf(((Stop) value).getStopNumber() - 1) + ". " + ((Stop) value).getStation();
                    }
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); //To change body of generated methods, choose Tools | Templates.
                }

            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frm, "Error in preparing Ticket form\n" + e.getMessage(), useCase.getName(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findFirstEmptySeat() {
        try {
            Departure currentDeparture = (Departure) MainCordinator.getInstance().getParam(CURRENT_DEPARTURE);
            List<Ticket> tickets = currentDeparture.getTickets();
            final int numberOfSeats = currentDeparture.getMaxPassengerNumber();
            int emptySeat = 1;
            int i = 0;
            int currentSeats = tickets.size();
            Collections.sort(tickets);
            //if empty
            if (currentSeats == 0) {
                //if full
            } else if (currentSeats == numberOfSeats) {
                throw new Exception("All seats are already taken");
                //if no seats in the middle
            } else if (currentSeats == tickets.get(currentSeats - 1).getSeatNumber()) {
                emptySeat = currentSeats + 1;
                //if there is an empty seat in the middle
            } else {
                for (Ticket ticket : tickets) {
                    if (ticket.getSeatNumber() != ++i) {
                        emptySeat = i;
                        break;
                    }
                }
            }
            frm.getTxtSeatNumber().setText(String.valueOf(emptySeat));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frm, e.getMessage(), useCase.getName(), JOptionPane.ERROR_MESSAGE);
            frm.dispose();
        }
    }

    private void prepareDeleteTicketForm() {
        prepareTableTickets();
    }

    private void prepareTableTickets() {
        Departure currentDeparture = ((Departure) MainCordinator.getInstance().getParam(CURRENT_DEPARTURE)).copy();
        List<Ticket> tickets = currentDeparture.getTickets();
        Collections.sort(tickets);
        TableModelTicket model = new TableModelTicket(tickets);
        frm.getTblTicket().setModel(model);
        JTableUtilities.setCellAlignment(frm.getTblTicket(), SwingConstants.CENTER);

    }

    private Ticket getNewTicket() {
        Ticket ticket = new Ticket();
        try {
            ticket.setLine((Line) MainCordinator.getInstance().getParam(MapParams.CURRENT_LINE));
            ticket.setDeparture(((Departure) MainCordinator.getInstance().getParam(MapParams.CURRENT_DEPARTURE)).copy());
            ticket.setSeatNumber(Integer.parseInt(frm.getTxtSeatNumber().getText()));
            ticket.setFirstName(frm.getTxtFirstName().getText());
            ticket.setLastName(frm.getTxtLastName().getText());
            ticket.setExitStop((Stop) frm.getCbLastStop().getSelectedItem());
            return ticket;
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteTicket() {
        TableModelTicket model = (TableModelTicket) frm.getTblTicket().getModel();
        int selectedRow = frm.getTblTicket().getSelectedRow();
        Ticket toDelete = null;
        try {
            if (selectedRow != -1) {
                if (!confirmDelete()) {
                    return;
                }
                toDelete = model.getTicketAt(selectedRow);
                toDelete.setState(State.REMOVED);
                String message = Communication.getInstance().deleteTicket(toDelete);
                model.removeTicketAt(selectedRow);
                JOptionPane.showMessageDialog(frm,message, useCase.getName(), JOptionPane.INFORMATION_MESSAGE);
                Departure departure = toDelete.getDeparture();
                toDelete.getDeparture().getTickets().remove(toDelete);
                departure.setPassengerNumber(departure.getPassengerNumber() - 1);
                departure.resetState();
                updateCurrentDeparture(toDelete.getDeparture());
                cancelForm();
            } else {
                throw new TableModelException("Couldn't complete operation: No Rows Selected");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (toDelete != null) {
                toDelete.resetState();
            }
            JOptionPane.showMessageDialog(frm, e.getMessage(), useCase.getName(), JOptionPane.ERROR_MESSAGE);
//            frm.dispose(); no dispose,because it is the last step in use case
        }
    }

    public void saveTicket() {
        try {
            if (!confirmSave()) {
                return;
            }
            validateForm();
            resetForm();
            Ticket toSave = getNewTicket();
            toSave.setState(State.NEW);
            String message = Communication.getInstance().saveTicket(toSave);
            JOptionPane.showMessageDialog(frm, message, useCase.getName(), JOptionPane.INFORMATION_MESSAGE);
            Departure departure = toSave.getDeparture();
            departure.getTickets().add(toSave);
            departure.resetState();
            departure.setPassengerNumber(departure.getPassengerNumber() + 1);
            updateCurrentDeparture(departure);
            addMoreTickets();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frm, ex.getMessage(), useCase.getName(), JOptionPane.ERROR_MESSAGE);
//            frm.dispose(); no dispose because this is the last step of use-case
        }
    }

    @Override
    protected void validateForm() throws ValidationException {
        StringBuilder sb = new StringBuilder();
        if (frm.getTxtSeatNumber().getText().isEmpty()) {
            frm.getLblSeatNumberError().setText("Seat Number can not be empty!");
            sb.append("\nSeat Number can not be empty!\n");
        }
        if (frm.getTxtFirstName().getText().isEmpty()) {
            frm.getLblSeatNumberError().setText("First Name can not be empty!");
            sb.append("\nFirst Name can not be empty!\n");
        }
        if (frm.getTxtLastName().getText().isEmpty()) {
            frm.getLblSeatNumberError().setText("Last Name can not be empty!");
            sb.append("\nLast Name can not be empty!\n");
        }
        String errorMessage = sb.toString();
        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }

    private void addMoreTickets() throws Exception {
        int input = JOptionPane.showConfirmDialog(frm, "Add more Tickets?", "Question",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        // 0=yes, 1=no, 2=cancel
        if (input == 0) {
            Departure departure = (Departure) MainCordinator.getInstance().getParam(CURRENT_DEPARTURE);
            if (departure.getMaxPassengerNumber() == departure.getPassengerNumber()) {
                throw new Exception("All seats are already taken");
            }
            prepareAddTicketForm();
        } else {
            frm.dispose();
        }
    }

    private void resetForm() {
        frm.getLblSeatNumberError().setText("");
        frm.getLblFirstNameError().setText("");
        frm.getLblLastNameError().setText("");
    }

    private void updateCurrentDeparture(Departure currentDeparture) {
        Departure oldDeparture = (Departure) MainCordinator.getInstance().getParam(CURRENT_DEPARTURE);
        oldDeparture.paste(currentDeparture.copy());
    }
}
