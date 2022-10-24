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
public class Employee extends GeneralDObject<Employee> {

    @Override
    public void setColumnNames() {
        columnNames = new String[]{"employee.employeeID", "employee.firstName", "employee.lastName", "employee.userName", "employee.password"};
    }
    private Long employeeID;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    public Employee() {
    }

    public Employee(Long employeeID) {
        this.employeeID = employeeID;
    }

    public Employee(Long employeeID, String firstName, String lastName, String userName, String password) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }
    

    private Employee(Employee toCopy) {
        this.employeeID = toCopy.employeeID;
        this.firstName = toCopy.firstName;
        this.lastName = toCopy.lastName;
        this.userName = toCopy.userName;
        this.password = toCopy.password;
        this.state = toCopy.state;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    @Override
    public Employee copy() {
        return new Employee(this);
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
        final Employee other = (Employee) obj;
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }

    @Override
    public void paste(Employee other) {
        this.employeeID = other.employeeID;
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.userName = other.userName;
        this.password = other.password;
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
        return "employee.employeeID = ?";
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
            case 1://[delete],[findRecord]
                statement.setLong(1, employeeID);
                break;
            case 5://[insert]
                statement.setLong(1, employeeID);
                statement.setString(2, firstName);
                statement.setString(3, lastName);
                statement.setString(4, userName);
                statement.setString(5, password);
                break;
            case 6://[update]
                statement.setLong(1, employeeID);
                statement.setString(2, firstName);
                statement.setString(3, lastName);
                statement.setString(4, userName);
                statement.setString(5, password);
                statement.setLong(6, employeeID);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported number of wildCards:" + numWCards);
        }
    }

    @Override
    public void fillWithRecordValues(ResultSet rs) throws SQLException, Exception {
        employeeID = rs.getLong(columnNames[0]);
        firstName = rs.getString(columnNames[1]);
        lastName = rs.getString(columnNames[2]);
        userName = rs.getString(columnNames[3]);
        password = rs.getString(columnNames[4]);

    }


    @Override
    public String message2() {
        return "userName and/or password can't be null";

    }

    @Override
    public String message3() {
        return "Login Unsuccessful!";

    }

    @Override
    public String message4() {
        return "Login Successful!";

    }

    @Override
    public String message5() {
        //it is okay to inform caller that username is correct
        //because system user is employee
        return "Login Unsuccessful: Incorrect password!";
    }

    @Override
    public String message6() {
        return "Login Unsuccessful: Unknown User!";
    }

    @Override
    public String message7() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String message8() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String message9() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String message10() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String message11() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String message12() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
