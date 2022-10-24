/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import rs.ac.bg.fon.ps.logger.MyLogger;
import rs.ac.bg.fon.ps.util.constant.MyServerConstants;
import rs.ac.bg.fon.ps.util.constant.PathConstants;

/**
 *
 * @author Vladimir Jecić
 */
public class DbConnectionFactory {

    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections;
    private Connection connectionOld;
    private static DbConnectionFactory instance;

    private DbConnectionFactory() {
        connectionPool = new ArrayList();
        usedConnections = new ArrayList();
    }

    public synchronized static DbConnectionFactory getInstance() {
        if (instance == null) {
            instance = new DbConnectionFactory();
        }
        return instance;
    }

    public Connection getConnectionFromPool() throws Exception {
        Connection connection = null;
        try {
            synchronized (connectionPool) {//seize connectionPool monitor
                if (connectionPool.isEmpty() && usedConnections.isEmpty()) {
                    fillConnectionPool();
                }
                if (connectionPool.isEmpty() && !usedConnections.isEmpty()) {
                    waitForConnection();
                }
                connection = connectionPool.remove(0);
                usedConnections.add(connection);
            }

        } catch (Exception ex) {
            MyLogger.getLogger(DbConnectionFactory.class).log(Level.WARNING, ex.getMessage());
            connectionPool.clear();
            throw ex;
        }
        return connection;
    }

    @Deprecated
    public synchronized Connection getConnection() throws Exception {
        if (connectionOld == null || connectionOld.isClosed()) {
            Properties properties = new Properties();
            properties.load(new FileInputStream("util/config/dbconfig.properties"));
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            connectionOld = DriverManager.getConnection(url, username, password);
            connectionOld.setAutoCommit(false);
        }
        return connectionOld;
    }

    public void returnConnectionToPool(Connection connection) throws Exception {
        try {
            synchronized (connectionPool) {
                usedConnections.remove(connection);
                if (connection.isClosed()) {
                    throw new Exception("Closed connection in connection pool");
                }
                connectionPool.add(connection);//releases monitor once it gets out of synchronized block
                connectionPool.notify();
            }

        } catch (Exception ex) {
            throw ex;
        }
    }

//    private void openConnection(Connection connection) throws FileNotFoundException, SQLException, IOException {
//        Properties properties = new Properties();
//        properties.load(new FileInputStream(PathConstants.DB_CONFIG_PROPERTIES));
//        String url = properties.getProperty(MyServerConstants.URL);
//        String username = properties.getProperty(MyServerConstants.USERNAME);
//        String password = properties.getProperty(MyServerConstants.PASSWORD);
//        connection = DriverManager.getConnection(url, username, password);
//        connection.setAutoCommit(false);
//    }
    private void fillConnectionPool() throws FileNotFoundException, IOException, SQLException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(PathConstants.DB_CONFIG_PROPERTIES));
        String url = properties.getProperty(MyServerConstants.URL);
        String username = properties.getProperty(MyServerConstants.USERNAME);
        String password = properties.getProperty(MyServerConstants.PASSWORD);
        int connectionPoolSize = Integer.valueOf(properties.getProperty(MyServerConstants.CONNECTION_POOL_SIZE));
        for (int i = 0; i < connectionPoolSize; i++) {
            Connection connection;
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            connectionPool.add(connection);
        }
    }

    private void waitForConnection() {
        try {
            connectionPool.wait();//releases monitor on connectionPool and waits for notify
        } catch (InterruptedException interruptedException) {
        }
    }

}
