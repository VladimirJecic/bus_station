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
import rs.ac.bg.fon.ps.util.constant.State;

/**
 *
 * @author Vladimir Jecić
 */
public class Stop extends GeneralDObject<Stop> implements Comparable<Stop> {

    @Override
    public void setColumnNames() {
        columnNames = new String[]{"stop.lineID", "stop.stationID", "stop.stopNumber"};
    }

    private Line line;
    private Station station;
    private int stopNumber;

    public Stop() {
    }
    public Stop(Line line, Station station) {
        this.line = line;
        this.station = station;
    }

    public Stop(Line line, Station station, int stopNumber) {
        this(line, station);
        this.stopNumber = stopNumber;
    }
    public Stop(Line line, Station station, int stopNumber, State state) {
        this(line, station, stopNumber);
        this.state = state;
    }

    private Stop(Stop toCopy) {
        this.station = toCopy.getStation();
        this.stopNumber = toCopy.getStopNumber();
        this.state = toCopy.getState();

    }


    public int getStopNumber() {
        return stopNumber;
    }

    public void setStopNumber(int stopNumber) {
        this.stopNumber = stopNumber;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return station + "";
    }

    @Override
    public Stop copy() {
        return new Stop(this);
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Stop other = (Stop) obj;
        if (this.stopNumber != other.stopNumber) {
            return false;
        }
        if (!Objects.equals(this.station, other.station)) {
            return false;
        }
        
        return true;
    }


    

    @Override
    public int compareTo(Stop val) {
        // compares 2 Stops by stopNumber
        int xStop = this.getStopNumber();
        int yStop = val.getStopNumber();
        if (xStop != yStop) {
            return (xStop < yStop) ? -1 : 1;
        } else {
            return 0;
        }
    }

    @Override
    public void paste(Stop other) {
        this.line = other.line;
        this.station = other.station;
        this.stopNumber = other.stopNumber;
        this.state = other.state;
    }

    @Override
    public String getGeneral_Select() {
        return "station.stationID,station.stationName,stop.stopNumber";
    }

    @Override
    public String getGeneral_From() {
        return "STOP INNER JOIN Station ON (stop.stationID = station.stationID)";
    }

    @Override
    public String getGeneral_Where() {
        return "stop.lineID = ? AND stop.stationID=?";
    }

    @Override
    public String getNewKey_ColumnName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNewKey_Where() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getStopsForLine_Where() {
        return "stop.lineID = ?";
    }

    public String getStopsForStation_Where() {
        return "stop.stationID = ?";
    }

    @Override
    public void prepareStatement(PreparedStatement statement, String sql) throws SQLException, UnsupportedOperationException, Exception {
        int numWCards = countWildCards(sql);
        switch (numWCards) {
            case 0://[findAllRecords]
                break;
            case 1://[getStopsForLine_Where][getStopsForStation_Where] ie. findRecordsWhere
                if (sql.endsWith(getStopsForLine_Where())) {
                    statement.setLong(1, line.getLineID());
                }
                if (sql.endsWith(getStopsForStation_Where())) {
                    statement.setLong(1, station.getStationID());
                }
                break;
            case 2://[delete],[findRecord]
                statement.setLong(1, line.getLineID());
                statement.setLong(2, station.getStationID());
                break;
            case 3://[insert]
                statement.setLong(1, line.getLineID());
                statement.setLong(2, station.getStationID());
                statement.setInt(3, stopNumber);
                break;
            case 5://[update]
                statement.setLong(1, line.getLineID());
                statement.setLong(2, station.getStationID());
                statement.setInt(3, stopNumber);
                statement.setLong(4, line.getLineID());
                statement.setLong(5, station.getStationID());
                break;
            default:
                throw new UnsupportedOperationException("Unsupported number of wildCards:" + numWCards);
        }
    }

    @Override
    public void fillWithRecordValues(ResultSet rs) throws SQLException, Exception {
//        line = new Line(rs.getLong(columnNames[0]));
        station = new Station(rs.getLong("station.stationID"), rs.getString("station.stationName"));
        stopNumber = rs.getInt("stop.stopNumber");
    }
    @Override
    public void resetState() {
        super.resetState();
        if (station != null) {
            station.resetState();
        }
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
        return "station and stationID can't be null";
    }

    @Override
    public String message5() {
        return "stopNumber must be a positive number";
    }

    @Override
    public String message6() {
        return "Ilegal state of stop for requested system operation";

    }

    @Override
    public String message7() {
        return "System couldn't save stop.";
    }

    @Override
    public String message8() {
        return "System couldn't delete stop.";
    }

    @Override
    public String message9() {
        return "Referenced line doesn't exist.";
    }

    @Override
    public String message10() {
        return "Referenced station doesn't exist.";
    }
    public String message11() {
        return "Found two or more stops refferencing same line and station.\nLine and station must be unique for each stop.";
    }
    public String message12() {
        return "Stops are still refferenced by some of the tickets.\nRemove all of the tickets that refference this stop first, and then try again.";
    }

    @Override
    public String message13() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String message14() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String message15() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String message16() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
