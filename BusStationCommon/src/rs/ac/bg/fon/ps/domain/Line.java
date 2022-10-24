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
import java.util.List;
import java.util.Objects;
import rs.ac.bg.fon.ps.util.constant.State;

/**
 *
 * @author Vladimir Jecić
 */
public class Line extends GeneralDObject<Line> {

    @Override
    public void setColumnNames() {
        columnNames = new String[]{"line.lineID", "line.lineName", "line.distance", "line.travelTime", "line.employeeID", "line.firstStationID", "line.lastStationID"};
    }
    private Long lineID;
    private String lineName;
    private BigDecimal distance;
    private String travelTime;
    private Employee employee;
    private Station firstStation;
    private Station lastStation;
    private List<Departure> departures;
    private List<Stop> stops;

    public Line(Long lineID) {
        this();
        this.lineID = lineID;
    }

    public Line() {
        distance = new BigDecimal(BigInteger.ZERO);
        employee = new Employee();
        firstStation = new Station();
        lastStation = new Station();
        departures = new ArrayList<>();
        stops = new ArrayList<>();
        state = State.NOT_CHANGED;
    }

    //copy constructor  
    public Line(Line toCopy) {
        //String,Long,BigDecimal and Enums are immutable
        this.lineID = toCopy.getLineID();
        this.lineName = toCopy.getLineName();
        this.distance = toCopy.getDistance();
        this.travelTime = toCopy.getTravelTime();//String
        this.employee = (Employee) toCopy.getEmployee().copy();
        this.firstStation = (Station) toCopy.getFirstStation().copy();
        this.lastStation = (Station) toCopy.getLastStation().copy();
        this.state = toCopy.getState();
        this.departures = new ArrayList<>();
        this.stops = new ArrayList<>();

    }

    //makes shallow copy of @lines variables to this Line
    @Override
    public void paste(Line line) {
        this.lineName = line.getLineName();
        this.distance = line.getDistance();
        this.travelTime = line.getTravelTime();
        this.employee = line.getEmployee();
        this.firstStation = line.getFirstStation();
        this.lastStation = line.getLastStation();
        this.state = line.getState();
        this.departures = line.getDepartures();
        this.stops = line.getStops();

    }

    @Override
    public Line copy() {
        Line lineCopy = new Line(this);//kopira samo immutable tipove i objekte koji se sastoje od immutable tipova
        //sada postavljam objekte u listama koje imaju povratnu referencu na linecopy
        for (Departure departure : this.departures) {
            Departure departureCopy = (Departure) departure.copy();
            departureCopy.setLine(lineCopy);
            lineCopy.getDepartures().add(departureCopy);
        }
        for (Stop stop : this.stops) {
            Stop stopCopy = stop.copy();
            stopCopy.setLine(lineCopy);
            lineCopy.getStops().add(stopCopy);
        }
        return lineCopy;
    }

    public Station getLastStation() {
        return lastStation;
    }

    public void setLastStation(Station lastStation) {
        this.lastStation = lastStation;
    }

    public Long getLineID() {
        return lineID;
    }

    public void setLineID(Long lineID) {
        this.lineID = lineID;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Station getFirstStation() {
        return firstStation;
    }

    public void setFirstStation(Station firstStation) {
        this.firstStation = firstStation;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public List<Departure> getDepartures() {
        return departures;
    }

    public void setDepartures(List<Departure> departures) {
        this.departures = departures;
    }

    @Override
    public String toString() {
        return lineName;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
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
        final Line other = (Line) obj;
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.lineName, other.lineName)) {
            return false;
        }
        if (!Objects.equals(this.travelTime, other.travelTime)) {
            return false;
        }
        if (!Objects.equals(this.lineID, other.lineID)) {
            return false;
        }
        if (!Objects.equals(this.distance, other.distance)) {
            return false;
        }
//        if (!Objects.equals(this.employee, other.employee)) {
//            return false; //because different current user doesn't always mean different data
//        }
        if (!Objects.equals(this.firstStation, other.firstStation)) {
            return false;
        }
        if (!Objects.equals(this.lastStation, other.lastStation)) {
            return false;
        }
        int a = this.departures.size();
        int b = other.departures.size();
        if (a != b) {
            return false;
        }
        for (int i = 0; i < a; i++) {
            if (!Objects.equals(this.departures.get(i), other.departures.get(i))) {
                return false;
            }
        }
        a = this.stops.size();
        b = other.stops.size();
        if (a != b) {
            return false;
        }
        for (int i = 0; i < a; i++) {
            if (!Objects.equals(this.stops.get(i), other.stops.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getGeneral_Select() {
        return "line.lineID,line.lineName,line.distance,line.travelTime,employee.employeeID,employee.firstName,employee.lastName,employee.userName,employee.password,firstStation.stationID,firstStation.stationName,lastStation.stationID,lastStation.stationName";
    }

    @Override
    public String getGeneral_From() {
        return "Line INNER JOIN Employee ON (line.employeeID = employee.employeeID) INNER JOIN Station firstStation ON (line.firstStationID = firstStation.stationID) INNER JOIN Station lastStation ON (line.lastStationID = lastStation.stationID)";
    }

    
    @Override
    public String getGeneral_Where() {
        return "line.lineID=?";
    }
    @Override
    public String getNewKey_ColumnName() {
        return "line.lineID";
    }

    @Override
    public String getNewKey_Where() {
        return "TRUE";
    }

    @Override
    public void prepareStatement(PreparedStatement statement, String sql) throws SQLException, UnsupportedOperationException, Exception {

        int numWCards = countWildCards(sql);
        switch (numWCards) {
            case 0://[getNewKey][findAllRecords]
                break;
            case 1://[delete],[findRecord]
                statement.setLong(1, lineID);
                break;
            case 7://[insert]
                statement.setLong(1, lineID);
                statement.setString(2, lineName);
                statement.setBigDecimal(3, distance);
                statement.setString(4, travelTime);
                statement.setLong(5, employee.getEmployeeID());
                statement.setLong(6, firstStation.getStationID());
                statement.setLong(7, lastStation.getStationID());
                break;
            case 8://[update]
                statement.setLong(1, lineID);
                statement.setString(2, lineName);
                statement.setBigDecimal(3, distance);
                statement.setString(4, travelTime);
                statement.setLong(5, employee.getEmployeeID());
                statement.setLong(6, firstStation.getStationID());
                statement.setLong(7, lastStation.getStationID());
                statement.setLong(8, lineID);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported number of wildCards:" + numWCards);
        }

    }

    @Override
    public void fillWithRecordValues(ResultSet rs) throws SQLException, Exception {
        lineID = rs.getLong(columnNames[0]);
        lineName = rs.getString(columnNames[1]);
        distance = rs.getBigDecimal(columnNames[2]);
        travelTime = rs.getString(columnNames[3]);
        employee = new Employee(rs.getLong("employee.employeeID"),rs.getString("employee.firstName"),rs.getString("employee.lastName"),rs.getString("employee.userName"),rs.getString("employee.password"));
        firstStation = new Station(rs.getLong("firstStation.stationID"),rs.getString("firstStation.stationName"));
        lastStation = new Station(rs.getLong("lastStation.stationID"),rs.getString("lastStation.stationName"));
    }
    

    @Override
    public void resetState() {
        super.resetState(); //To change body of generated methods, choose Tools | Templates.
        if (stops != null && !stops.isEmpty()) {
            for (Stop stop : stops) {
                stop.resetState();
            }
        }
        if (departures != null && !departures.isEmpty()) {
            for (Departure departure : departures) {
                departure.resetState();
            }
        }
    }
    
    @Override
    public String message2() {
        return "System couldn't set new primary key for line!";
    }

    @Override
    public String message3() {
        return "lineID can't be null";
    }

    @Override
    public String message4() {
        return "lineName can't be null";
    }

    @Override
    public String message5() {
        return "distance can't be null or not a positive number";
    }

    @Override
    public String message6() {
        return "travelTime can't be null";
    }

    @Override
    public String message7() {
        return "employeeID can't be null";
    }

    @Override
    public String message8() {
        return " firstStation and firstStationID can't be null";
    }

    @Override
    public String message9() {
        return "lastStation and lastStationID can't be null";
    }

    @Override
    public String message10() {
        return "Ilegal state of line for requested system operation";
    }

    public String message11() {
        return "System has saved the line!";
    }

    public String message12() {
        return "System couldn't save line!";
    }

    public String message13() {
        return "System has deleted the line.";
    }

    public String message14() {
        return "System couldn't delete line.";
    }

    public String message15() {
        return "Referenced employee doesn't exist.";
    }

    public String message16() {
        return "Referenced station doesn't exist.";
    }

    public String message17() {
        return "After deleting line corresponding departures were not deleted!";
    }

    public String message18() {
        return "After deleting line corresponding stops were not deleted!";
    }

    public String message19() {
        return "Lines are loaded.";
    }

    public String message20() {
        return "System couldn't load lines.";
    }

    public String message21() {
        return "System found lines for requested search values";
    }

    public String message22() {
        return "System coudn't find lines for requested search values";
    }
    public String message23() {
        return "System has loaded departures for the selected line";
    }
    public String message24() {
        return "System coudn't load departures for the selected line";
    }

    @Override
    public String message25() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
