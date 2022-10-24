/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import rs.ac.bg.fon.ps.domain.GeneralDObject;
import rs.ac.bg.fon.ps.repository.db.DbConnectionFactory;
import rs.ac.bg.fon.ps.repository.db.DbRepository;

/**
 *
 * @author Vladimir Jecić
 * @param <GeneralDObject>
 */
public class RepositoryDbGeneric<GeneralDOObject> implements DbRepository<GeneralDObject> {

    private Connection connection;

    @Override
    public void connect() throws Exception {
        connection = DbConnectionFactory.getInstance().getConnectionFromPool();
    }

    @Override
    public void disconnect() throws Exception {
//        if (connection != null && !connection.isClosed()) {
//            connection.close();
//        }
        DbConnectionFactory.getInstance().returnConnectionToPool(connection);
        connection = null;
    }

    @Override
    public void commit() throws Exception {
        connection.commit();
    }

    @Override
    public void rollback() throws Exception {
        connection.rollback();
    }

    @Override
    public boolean insertRecord(GeneralDObject gdo) throws SQLException, Exception {
        StringBuilder sb = new StringBuilder();
        int numColumns = gdo.getNumberOfColumns();
        sb.append("INSERT INTO ")
                .append(gdo.getTableName())
                .append(" VALUES (");
        for (int i = 0; i < numColumns; i++) {
            sb.append("?");
            if (i != numColumns - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return executeUpdate(gdo, sb.toString());
    }

    @Override
    public boolean updateRecord(GeneralDObject gdo) throws SQLException, Exception {
        StringBuilder sb = new StringBuilder();
        int numWCards = gdo.getNumberOfColumns();
        sb.append("UPDATE ")
                .append(gdo.getTableName())
                .append(" SET ");
        for (int i = 0; i < numWCards; i++) {
            sb.append(gdo.getColumnName(i)).append("=?");
            if (i != numWCards - 1) {
                sb.append(",");
            }
        }
        sb.append(" WHERE ").append(gdo.getGeneral_Where());
        return executeUpdate(gdo, sb.toString());
    }

    @Override
    public boolean deleteRecord(GeneralDObject gdo) throws SQLException, Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ")
                .append(gdo.getTableName())
                .append(" WHERE ").append(gdo.getGeneral_Where());
        return executeUpdate(gdo, sb.toString());
    }

    @Override
    public List<GeneralDObject> findAllRecords(GeneralDObject gdo, List<GeneralDObject> gdoList) throws SQLException, Exception {
        return findRecords(gdo, gdoList, "TRUE");
    }

    @Override
    public List<GeneralDObject> findRecords(GeneralDObject gdo, List<GeneralDObject> gdoList) throws SQLException, Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ").append(gdo.getGeneral_Select())
                .append("\nFROM ").append(gdo.getGeneral_From())
                .append("\nWHERE ").append(gdo.getGeneral_Where());
        return executeQuery(gdo, gdoList, sb.toString());
    }

    @Override
    public List<GeneralDObject> findRecords(GeneralDObject gdo, List<GeneralDObject> gdoList, String where) throws SQLException, Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ").append(gdo.getGeneral_Select())
                .append("\nFROM ").append(gdo.getGeneral_From())
                .append("\nWHERE ").append(where);
        return executeQuery(gdo, gdoList, sb.toString());
    }

    @Override
    public GeneralDObject findRecord(GeneralDObject gdo) throws SQLException, Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ").append(gdo.getGeneral_Select())
                .append("\nFROM ").append(gdo.getGeneral_From())
                .append("\nWHERE ").append(gdo.getGeneral_Where());
        return executeQuery(gdo, sb.toString());
    }

    @Override
    public GeneralDObject findRecord(GeneralDObject gdo, String where) throws SQLException, Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ").append(gdo.getGeneral_Select())
                .append("\nFROM ").append(gdo.getGeneral_From())
                .append("\nWHERE ").append(where);
        return executeQuery(gdo, sb.toString());
    }

    @Override
    public Long getNewKey(GeneralDObject gdo) throws SQLException, Exception {
        StringBuilder sb = new StringBuilder();
        PreparedStatement statement = null;
        ResultSet rs = null;
        boolean signal = false;
        sb.append("SELECT MAX(").append(gdo.getNewKey_ColumnName()).append(") AS newKey")
                .append("\nFROM ").append(gdo.getTableName())
                .append("\nWHERE ").append(gdo.getNewKey_Where());
        String sql = sb.toString();
        System.out.println("\n" + sql + "\n");
        try {
            statement = connection.prepareStatement(sql);
            gdo.prepareStatement(statement, sql);
            rs = statement.executeQuery();
            signal = rs.next();
            if (signal) {
                return rs.getLong("newKey") + 1L;
            } else {
                return 1L;
            }
        } catch (SQLException sqle) {
            signal = false;
            throw sqle;
        } catch (Exception e) {
            signal = false;
            throw e;
        } finally {
            closeResources(null, statement, rs);
        }
    }

    private List<GeneralDObject> executeQuery(GeneralDObject gdo, List<GeneralDObject> gdoList, String sql) throws SQLException, Exception {
        PreparedStatement statement = null;
        ResultSet rs = null;
        boolean signal = false;
        System.out.println("\n" + sql + "\n");
        try {
            statement = connection.prepareStatement(sql);
            gdo.prepareStatement(statement, sql);
            rs = statement.executeQuery();
            while (rs.next()) {
                GeneralDObject gdoNew = gdo.getClass().newInstance();
                gdoNew.fillWithRecordValues(rs);
                gdoList.add(gdoNew);
            }
        } catch (SQLException sqle) {
            signal = false;
            throw sqle;
        } catch (Exception e) {
            signal = false;
            throw e;
        } finally {
            closeResources(null, statement, rs);
        }
        return gdoList;

    }

    private GeneralDObject executeQuery(GeneralDObject gdo, String sql) throws SQLException, Exception {
        PreparedStatement statement = null;
        ResultSet rs = null;
        boolean signal = false;
        System.out.println("\n" + sql + "\n");
        try {
            statement = connection.prepareStatement(sql);
            gdo.prepareStatement(statement, sql);
            rs = statement.executeQuery();
            signal = rs.next();
            if (signal) {
                gdo.fillWithRecordValues(rs);
            }
        } catch (SQLException sqle) {
            signal = false;
            throw sqle;
        } catch (Exception e) {
            signal = false;
            throw e;
        } finally {
            closeResources(null, statement, rs);
        }
        return gdo;
    }

    private boolean executeUpdate(GeneralDObject gdo, String sql) throws SQLException, Exception {
        PreparedStatement statement = null;
        boolean signal = false;
        System.out.println("\n" + sql + "\n");
        try {
            statement = connection.prepareStatement(sql);
            gdo.prepareStatement(statement, sql);
            int rowCount = statement.executeUpdate();
            if (rowCount > 0) {
                signal = true;
            }
            return signal;
        } catch (SQLException sqle) {
            signal = false;
            throw sqle;
        } catch (Exception e) {
            signal = false;
            throw e;
        } finally {
            closeResources(null, statement, null);
        }

    }

    private void closeResources(Connection connection, Statement statement, ResultSet rs) throws SQLException, Exception {
        if (rs != null && !rs.isClosed()) {
            rs.close();
        }
        if (statement != null && !statement.isClosed()) {
            statement.close();
        }
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

}
