/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Vladimir Jecić
 */
public class Departure extends GeneralDObject<Departure> {

    @Override
    public void setColumnNames() {
        columnNames = new String[]{"departure.lineID", "departure.departureID", "departure.departureTime", "departure.arrivalTime", "departure.maxPassengerNumber",
            "departure.passengerNumber", "departure.price", "departure.platform"};
    }
    private Line line;
    private Long departureID;
    private Date departureTime;
    private Date arrivalTime;
    private int maxPassengerNumber;
    private int passengerNumber;
    private BigDecimal price;
    private int platform;
    private List<Ticket> tickets;

    public Departure(Line line, Long departureID) {
        this();
        this.line = line;
        this.departureID = departureID;
    }

    public Departure() {
        price = new BigDecimal(BigInteger.ZERO);
        tickets = new ArrayList<>();
    }

    public Departure(Departure toCopy) {
        this.line = toCopy.getLine();
        this.departureID = toCopy.getDepartureID();
        this.departureTime = new Date(toCopy.getDepartureTime().getTime());
        this.arrivalTime = new Date(toCopy.getArrivalTime().getTime());
        this.maxPassengerNumber = toCopy.getMaxPassengerNumber();
        this.passengerNumber = toCopy.getPassengerNumber();
        this.price = toCopy.getPrice();
        this.platform = toCopy.getPlatform();
        this.state = toCopy.getState();
        this.tickets = new ArrayList<>();
    }

    @Override
    public void paste(Departure other) {
        this.line = other.line;
        this.departureID = other.departureID;
        this.arrivalTime = other.arrivalTime;
        this.passengerNumber = other.passengerNumber;
        this.price = other.price;
        this.platform = other.platform;
        this.tickets = other.tickets;
        this.state = other.state;
    }

    @Override
    public Departure copy() {
        Departure departureCopy = new Departure(this);
        for (Ticket ticket : this.tickets) {
            Ticket copyTicket = ticket.copy();
            copyTicket.setLine(departureCopy.getLine());
            copyTicket.setDeparture(departureCopy);
            departureCopy.getTickets().add(copyTicket);
        }
        return departureCopy;
    }

    @Override
    public String toString() {
        return "with id " + departureID + ". on line " + line;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Long getDepartureID() {
        return departureID;
    }

    public void setDepartureID(Long departureID) {
        this.departureID = departureID;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getMaxPassengerNumber() {
        return maxPassengerNumber;
    }

    public void setMaxPassengerNumber(int maxPassengerNumber) {
        this.maxPassengerNumber = maxPassengerNumber;
    }

    public int getPassengerNumber() {
//        this.passengerNumber = this.tickets.size();
        return passengerNumber;
    }

    public void setPassengerNumber(int passengerNumber) {
        this.passengerNumber = passengerNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
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
        final Departure other = (Departure) obj;
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (this.maxPassengerNumber != other.maxPassengerNumber) {
            return false;
        }
        if (this.passengerNumber != other.passengerNumber) {
            return false;
        }
        if (this.platform != other.platform) {
            return false;
        }
        if (!Objects.equals(this.departureID, other.departureID)) {
            return false;
        }
        if (!Objects.equals(this.departureTime, other.departureTime)) {
            return false;
        }
        if (!Objects.equals(this.arrivalTime, other.arrivalTime)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        int a = this.tickets.size();
        int b = other.tickets.size();
        if (a != b) {
            return false;
        }
        for (int i = 0; i < a; i++) {
            if (!Objects.equals(this.tickets.get(i), other.tickets.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getNewKey_ColumnName() {
        return "departure.departureID";
    }

    @Override
    public String getGeneral_Select() {
        return getColumnNames();
    }

    @Override
    public String getGeneral_From() {
        return getTableName();
    }
    

    @Override
    public String getGeneral_Where() {
        return "departure.lineID=? AND departure.departureID=?";
    }

    @Override
    public String getNewKey_Where() {
        return "departure.lineID=?";
    }

    public String getDeparturesForLine_Where() {
        return "departure.lineID = ?";
    }

    @Override
    public void prepareStatement(PreparedStatement statement, String sql) throws SQLException, UnsupportedOperationException, Exception {
        int numWCards = countWildCards(sql);
        switch (numWCards) {
            case 0://[findAllRecords]
                break;
            case 1://[getNewKey],[getDeparturesForLine_Where]
                statement.setLong(1, line.getLineID());
                break;
            case 2://[delete][findRecord]
                statement.setLong(1, line.getLineID());
                statement.setLong(2, departureID);
                break;
            case 8://[insert]
                statement.setLong(1, line.getLineID());
                statement.setLong(2, departureID);
                statement.setTimestamp(3, new java.sql.Timestamp(departureTime.getTime()));
                statement.setTimestamp(4, new java.sql.Timestamp(arrivalTime.getTime()));
                statement.setInt(5, maxPassengerNumber);
                statement.setInt(6, passengerNumber);
                statement.setBigDecimal(7, price);
                statement.setInt(8, platform);
                break;
            case 10://[update]
                statement.setLong(1, line.getLineID());
                statement.setLong(2, departureID);
                statement.setTimestamp(3, new java.sql.Timestamp(departureTime.getTime()));
                statement.setTimestamp(4, new java.sql.Timestamp(arrivalTime.getTime()));
                statement.setInt(5, maxPassengerNumber);
                statement.setInt(6, passengerNumber);
                statement.setBigDecimal(7, price);
                statement.setInt(8, platform);
                statement.setLong(9, line.getLineID());
                statement.setLong(10, departureID);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported number of wildCards:" + numWCards);
        }
    }

    @Override
    public void fillWithRecordValues(ResultSet rs) throws SQLException, Exception {
        departureID = rs.getLong(columnNames[1]);
        departureTime = new java.util.Date(rs.getTimestamp(columnNames[2]).getTime());
        arrivalTime = new java.util.Date(rs.getTimestamp(columnNames[3]).getTime());
        maxPassengerNumber = rs.getInt(columnNames[4]);
        passengerNumber = rs.getInt(columnNames[5]);
        price = rs.getBigDecimal(columnNames[6]);
        platform = rs.getInt(columnNames[7]);
    }
    @Override
    public void resetState() {
        super.resetState(); //To change body of generated methods, choose Tools | Templates.
        if (tickets != null && !tickets.isEmpty()) {
            for (Ticket ticket : tickets) {
                ticket.resetState();
            }
        }
    }
    @Override
    public String message2() {
        return "System couldn't set new primary key for departure.";
    }

    @Override
    public String message3() {
        return "line and lineID can't be null";
    }

    @Override
    public String message4() {
        return "departureID can't be null";
    }

    @Override
    public String message5() {
        return "departureTime can't be null";
    }

    @Override
    public String message6() {
        return "arrivalTime can't be null";
    }

    @Override
    public String message7() {
        return "maxPassengerNumber must be a positive number";
    }

    @Override
    public String message8() {
        return "passengerNumber can't be a negative number";
    }

    @Override
    public String message9() {
        return "price can't be null or not a positive number";
    }

    @Override
    public String message10() {
        return "price can't be null or not a positive number";
    }

    public String message11() {
        return "Ilegal state of departure for requested system operation";
    }

    public String message12() {
        return "System has saved departure(s)";
    }

    public String message13() {
        return "System couldn't save departure(s)";
    }

    public String message14() {
        return "System has deleted the departure.";
    }

    public String message15() {
        return "System couldn't delete departure.";
    }

    public String message16() {
        return "Referenced line doesn't exist.";
    }

    public String message17() {
        return "departure time must be sooner than arrival time";
    }

    public String message18() {
        return "current passenger number must be less than max number of passengers";
    }

    public String message19() {
        return "passenger number in departure must be equal to number of tickets for that departure in database!";
    }

    public String message20() {
        return "After deleting departure corresponding tickets were not deleted!";
    }

    public String message21() {
        return "System has loaded tickets for the selected departure";
    }

    public String message22() {
        return "System coudn't load tickets for the selected departure";
    }

    public String message23() {
        return "System couldn't update departure";
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
