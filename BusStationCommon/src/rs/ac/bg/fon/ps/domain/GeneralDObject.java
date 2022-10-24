/* GeneralDObject.java
 * @author Vladimir Jecić
 */
package rs.ac.bg.fon.ps.domain;

// Operacije navedenog interfejsa je potrebno da implementira svaka od domenskih klasa,
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import rs.ac.bg.fon.ps.util.constant.State;

// koja zeli da joj bude omogucena komunikacija sa Database broker klasom.
public abstract class GeneralDObject<T> implements Serializable {

    protected String[] columnNames;
    protected State state;

    public GeneralDObject() {
        setColumnNames();
        state = State.NOT_CHANGED;
    }
    public State getState(){return this.state;}
    public void setState(State state){this.state = state;}
    public void resetState(){this.state = State.NOT_CHANGED;}
    public String getColumnName(int position) {return columnNames[position];}
    public String getTableName() {return this.getClass().getSimpleName();}
    public String getColumnNames() {
        StringBuilder sb = new StringBuilder();
        int length = columnNames.length;
        for (int i = 0; i < length; i++) {
            sb.append(columnNames[i]);
            if (i != length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    public int getNumberOfColumns() {return columnNames.length;}
    public int countWildCards(String query) {
        int count = 0;
        for (int i = 0; i < query.length(); i++) {
            if (query.charAt(i) == '?') {
                count++;
            }
        }
        return count;
    }
    abstract public String getGeneral_Select();
    abstract public String getGeneral_From();
    /**
     * 
     * @return searches for General Object by its primary key in database
     */
    abstract public String getGeneral_Where();
    abstract public String getNewKey_ColumnName();
    abstract public String getNewKey_Where();
    /**
     * Sets names of all columns,which will be used in queries for corresponding database table.
     * This method is called by GeneralDObject's constructor
     */
    abstract public void setColumnNames();
    /**
       * Creates and returns a genuine(deep) copy of this instance
       *
       * @return a genuine(deep) copy of this instance.
       */
    abstract public T copy();
    /**
       * Copies other object's attributes to this object's corresponding attributes by 
       * reference only(shallow copy)
       *
       * @param other an object to be pasted from.
       */
    abstract public void paste(T other);
    /**
       * Sets all parameters for prepared statement
       *
       * @param statement
       * @param sql
       * @throws SQLException
       */
    abstract public void prepareStatement(PreparedStatement statement, String sql) throws SQLException,UnsupportedOperationException, Exception;
    /**
       * Sets this objects attributes using values from Result Set
       * @param rs
       * @return
       * @throws SQLException
       * @throws Exception
       */
    abstract public void fillWithRecordValues(ResultSet rs) throws SQLException, Exception;
    public String message1A(){
        return "Passed argument 'gdo' can't be null or not instance of "+this.getClass().getCanonicalName();
    }
    public String message1B(){
        return "Passed argument 'gdoList' can't be null or empty";
    }
    public String message1C(){
        return "state can't be null!";
    }
    abstract public String message2();
    abstract public String message3();  
    abstract public String message4();
    abstract public String message5();
    abstract public String message6();
    abstract public String message7();
    abstract public String message8();
    abstract public String message9();
    abstract public String message10();
    abstract public String message11();
    abstract public String message12();  
    abstract public String message13();
    abstract public String message14();
    abstract public String message15();
    abstract public String message16();
    abstract public String message17();
    abstract public String message18();
    abstract public String message19();
    abstract public String message20();
    abstract public String message21();
    abstract public String message22();
    abstract public String message23();
    abstract public String message24();
    abstract public String message25();
}
