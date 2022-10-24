/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.domain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author Vladimir Jecić
 */
public class Ticket extends GeneralDObject<Ticket> implements Comparable<Ticket> {

    @Override
    public void setColumnNames() {
        columnNames = new String[]{"ticket.lineID", "ticket.departureID", "ticket.seatNumber", "ticket.firstName", "ticket.lastName", "ticket.exitStationID"};
    }

    private Line line;
    private Departure departure;
    private int seatNumber;
    private String firstName;
    private String lastName;
    private Stop exitStop;//it's Stop and not Station because when user tries to delete Stop on the Line I want to
    //throw StructureConstraintException,
    //because I don't want any Tickets referring to Station that doesn't exist as Stop on that Line!!!

    public Ticket() {
        exitStop = new Stop();
    }

    public Ticket(Line line, Departure departure) {
        this.line = line;
        this.departure = departure;
    }

    public Ticket(Line line, Departure departure, int seatNumber) {
        this(line, departure);
        this.seatNumber = seatNumber;
    }

    private Ticket(Ticket toCopy) {
        this.line = toCopy.getLine();
        this.departure = toCopy.getDeparture();
        this.seatNumber = toCopy.getSeatNumber();
        this.firstName = toCopy.getFirstName();
        this.lastName = toCopy.getLastName();
        this.exitStop = toCopy.getExitStop().copy();
        this.state = toCopy.getState();
    }

    @Override
    public String toString() {
        return seatNumber + ". seat | " + firstName + " " + lastName + " | " + exitStop;
    }

    @Override
    public int compareTo(Ticket val) {
        // compares 2 Tickets by seat number

        int xSeat = this.getSeatNumber();
        int ySeat = val.getSeatNumber();
        if (xSeat != ySeat) {
            return (xSeat < ySeat) ? -1 : 1;
        } else {
            return 0;
        }
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Stop getExitStop() {
        return exitStop;
    }

    public void setExitStop(Stop exitStop) {
        this.exitStop = exitStop;
    }

    @Override
    public Ticket copy() {
        return new Ticket(this);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ticket other = (Ticket) obj;
        if (this.seatNumber != other.seatNumber) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.exitStop, other.exitStop)) {
            return false;
        }
        return true;
    }

    @Override
    public void paste(Ticket other) {
        this.line = other.line;
        this.departure = other.departure;
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.exitStop = other.exitStop;
        this.seatNumber = other.seatNumber;
    }

    @Override
    public String getGeneral_Select() {
        return "ticket.seatNumber, ticket.firstName,ticket.lastName,station.stationID,station.stationName,stop.stopNumber";
    }

    @Override
    public String getGeneral_From() {
        return "Ticket INNER JOIN Stop ON (ticket.lineID = stop.lineID AND ticket.exitStationID = stop.stationID) INNER JOIN Station ON(stop.stationID = station.stationID)";
    }

    @Override
    public String getGeneral_Where() {
        return "ticket.lineID = ? AND ticket.departureID = ? AND ticket.seatNumber = ?";
    }

    public String getTicketsForDeparture_Where() {
        return "ticket.lineID = ? AND ticket.departureID = ?";
    }

    public String getTicketsForStop_Where() {
        return "ticket.lineID = ? AND ticket.exitStationID=?";
    }

    @Override
    public String getNewKey_ColumnName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNewKey_Where() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void prepareStatement(PreparedStatement statement, String sql) throws SQLException, UnsupportedOperationException, Exception {
        int numWCards = countWildCards(sql);
        switch (numWCards) {
            case 0://[findAllRecords]
                break;
            case 2://[getTicketsForDeparture]
                if(sql.endsWith(getTicketsForDeparture_Where())){
                statement.setLong(1, line.getLineID());
                statement.setLong(2, departure.getDepartureID());
                }
                if(sql.endsWith(getTicketsForStop_Where())){
                statement.setLong(1, line.getLineID());
                statement.setLong(2, exitStop.getStation().getStationID());
                }
                break;
            case 3://[delete][findRecordWhere]
                statement.setLong(1, line.getLineID());
                statement.setLong(2, departure.getDepartureID());
                statement.setInt(3, seatNumber);
                break;
            case 6://[insert]
                statement.setLong(1, line.getLineID());
                statement.setLong(2, departure.getDepartureID());
                statement.setInt(3, seatNumber);
                statement.setString(4, firstName);
                statement.setString(5, lastName);
                statement.setLong(6, exitStop.getStation().getStationID());
                break;
            case 8://[update]
                statement.setLong(1, line.getLineID());
                statement.setLong(2, departure.getDepartureID());
                statement.setInt(3, seatNumber);
                statement.setString(4, firstName);
                statement.setString(5, lastName);
                statement.setLong(6, exitStop.getStation().getStationID());
                statement.setLong(7, line.getLineID());
                statement.setLong(8, departure.getDepartureID());
                break;
            default:
                throw new UnsupportedOperationException("Unsupported number of wildCards:" + numWCards);
        }
    }

    @Override
    public void fillWithRecordValues(ResultSet rs) throws SQLException, Exception {
//        line = new Line(rs.getLong("ticket.lineID"));
//        departure = new Departure(line,ticket.departureID);
        seatNumber = rs.getInt("ticket.seatNumber");
        firstName = rs.getString("ticket.firstName");
        lastName = rs.getString("ticket.lastName");
        Station exitStation = new Station(rs.getLong("station.stationID"), rs.getString("station.stationName"));
        exitStop = new Stop(this.line, exitStation, rs.getInt("stop.stopNumber"));

    }

    @Override
    public String message2() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String message3() {
        return "line and lineID can't be null";
    }

    @Override
    public String message4() {
        return "departure and departureID can't be null";
    }

    @Override
    public String message5() {
        return "seatNumber must be a positive number";
    }

    @Override
    public String message6() {
        return "firstName can't be null";
    }

    @Override
    public String message7() {
        return "lastName can't be null";
    }

    @Override
    public String message8() {
        return "exitStop or exitStop's station or it's stationID can't be null";
    }

    @Override
    public String message9() {
        return "Refferenced line doesn't exist";
    }

    @Override
    public String message10() {
        return "Refferenced departure doesn't exist";
    }

    public String message11() {
        return "Refferenced stop doesn't exist";
    }

    public String message12() {
        return "System has saved the ticket.";
    }

    public String message13() {
        return "System couldn't save ticket";
    }

    public String message14() {
        return "System has deleted the ticket.";
    }

    public String message15() {
        return "System couldn't delete ticket";
    }

    public String message16() {
        return "Ilegal state of ticket for requested system operation";

    }

    @Override
    public String message17() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String message18() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String message19() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String message20() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String message21() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String message22() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String message23() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String message24() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String message25() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
