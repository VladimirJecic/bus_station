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
public class Station extends GeneralDObject<Station> {

    @Override
    public void setColumnNames() {
        columnNames = new String[]{"station.stationID", "station.stationName"};
    }

    private Long stationID;
    private String stationName;

    public Station(Long stationID) {
        this.stationID = stationID;
    }

    public Station() {
    }

    public Station(Long stationID, String stationName) {
        this.stationID = stationID;
        this.stationName = stationName;
    }

    private Station(Station toCopy) {
        this.stationID = toCopy.getStationID();
        this.stationName = toCopy.getStationName();
        this.state = toCopy.state;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Long getStationID() {
        return stationID;
    }

    public void setStationID(Long stationID) {
        this.stationID = stationID;
    }

    @Override
    public String toString() {
        return stationName;
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
        final Station other = (Station) obj;
        if (!Objects.equals(this.stationName, other.stationName)) {
            return false;
        }
        if (!Objects.equals(this.stationID, other.stationID)) {
            return false;
        }
        return true;
    }

    @Override
    public Station copy() {
        return new Station(this);
    }

    @Override
    public void paste(Station other) {
        this.stationID = other.stationID;
        this.stationName = other.stationName;
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
        return "station.stationID = ?";
    }

    @Override
    public String getNewKey_ColumnName() {
        return "station.stationID";
    }

    @Override
    public String getNewKey_Where() {
        return "TRUE";
    }

    @Override
    public void prepareStatement(PreparedStatement statement, String sql) throws SQLException, UnsupportedOperationException, Exception {

        int numWCards = countWildCards(sql);
        switch (numWCards) {
            case 0://[getNewKey],[findAllRecords]
                break;
            case 1://[delete],[findRecord]
                statement.setLong(1, stationID);
                break;
            case 2://[insert]
                statement.setLong(1, stationID);
                statement.setString(2, stationName);
                break;
            case 3://[update]
                statement.setLong(1, stationID);
                statement.setString(2, stationName);
                statement.setLong(3, stationID);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported number of wildCards:" + numWCards);
        }
    }

    @Override
    public void fillWithRecordValues(ResultSet rs) throws SQLException, Exception {
        stationID = rs.getLong(columnNames[0]);
        stationName = rs.getString(columnNames[1]);
    }

    @Override
    public String message2() {
        return "System couldn't set new primary key for station.";
    }

    @Override
    public String message3() {
        return "stationID can't be null";

    }

    @Override
    public String message4() {
        return "stationName can't be null or empty";

    }

    @Override
    public String message5() {
        return "System has saved the station.";

    }

    @Override
    public String message6() {
        return "System couldn't save station";
    }

    @Override
    public String message7() {
        return "Stations are loaded.";
    }

    @Override
    public String message8() {
        return "System couldn't load stations.";

    }

    @Override
    public String message9() {
        return "System couldn't delete station, because it is refferenced in line ";

    }

    @Override
    public String message10() {
        return "System has deleted the station.";

    }

    public String message11() {
        return "System couldn't delete station";
    }

    public String message12() {
        return "System found stations for requested search values";
    }

    public String message13() {
        return "System coudn't find stations for requested search values";
    }

    public String message14() {
        return "Station not found";
    }

    public String message15() {
        return "After deleting station corresponding stops were not deleted!";
    }

    public String message16() {
        return "System couldn't save station.\nA station with this name already exists.\nUse a different name instead.";
    }

    public String message17() {
        return "Ilegal state of station for requested system operation";
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
