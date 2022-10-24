/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository;

import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author Vladimir Jecić
 * @param <T>
 */
public interface Repository<T> {

//    public void connect() throws Exception;
//    public void disconnect() throws Exception;
    public boolean insertRecord (T gdo)throws SQLException, Exception;
    public boolean updateRecord(T gdo)throws SQLException, Exception;
    public boolean deleteRecord(T gdo) throws SQLException, Exception;
    public List<T> findAllRecords(T gdo, List<T> gdoList) throws SQLException, Exception ;
    public List<T> findRecords(T gdo, List<T> gdoList) throws SQLException, Exception ;
    public List<T> findRecords(T gdo,List<T> gdoList, String where)throws SQLException, Exception;
    public T findRecord(T gdo)throws SQLException, Exception;//by primaryKey
    public T findRecord(T gdo,String where)throws SQLException, Exception;
    public Long getNewKey(T gdo) throws SQLException, Exception;
}
