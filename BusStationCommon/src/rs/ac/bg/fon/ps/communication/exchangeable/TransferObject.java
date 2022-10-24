/* Racun.java 
 * @autor prof. dr Sinisa Vlajic,  
 * Univerzitet u Beogradu'
 * Fakultet organizacionih nauka 
 * Katedra za softversko inzenjerstvo 
 * Laboratorija za softversko inzenjerstvo 
 * Datum:2020-06-06 
 */
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.communication.exchangeable;

//import DomenskeKlase.GeneralDObject;
//import Ogranicenja.Ogranicenja;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import rs.ac.bg.fon.ps.domain.GeneralDObject;
import rs.ac.bg.fon.ps.logger.MyLogger;
import rs.ac.bg.fon.ps.util.constant.SystemOperations;
public class TransferObject implements Serializable {

    private GeneralDObject gdo;
    private List<GeneralDObject> listGdo;
    private boolean signal; // signal indicating operation success.
    private int operation;
    private int clientHostPort;
    private String message ;
    private String where;

    public TransferObject(GeneralDObject gdo, int operation) {
        this(operation);
        this.gdo= (GeneralDObject) gdo.copy();
    }
    public TransferObject(List<GeneralDObject> listGdo, int operation) {
        this(operation);
        if(listGdo!=null){
            for (GeneralDObject generalDObject : listGdo) {
                this.listGdo.add((GeneralDObject) generalDObject.copy());
            }
        }
    }
    public TransferObject(int operation) {
        this();
        this.operation = operation;
        try {
            this.message = SystemOperations.getName(operation);
        } catch (Exception ex) {
            MyLogger.getLogger(this.getClass()).log(Level.WARNING, ex.getMessage());
        }

    }
    public TransferObject(String where,int operation) {
    this(operation);
    this.where = where;
    }
    public TransferObject() {
        message = "";
        where= "";
        listGdo = new ArrayList<GeneralDObject>();
        
    }
    
    public boolean getSignal() {return signal;}
    public void setSignal(boolean signal) {this.signal = signal;}
    
    public GeneralDObject getGdo() {return (GeneralDObject) gdo;}
    public void setGdo(GeneralDObject gdo) {this.gdo = gdo;}

    public List<GeneralDObject> getListGdo() {return listGdo;}
    public void setListGdo(List<GeneralDObject> listGdo) {this.listGdo = listGdo;}

    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}
    
    public int getOperation(){return operation;}
    public void setOperation(int operation){this.operation = operation;}
    
    public void setClientPort(int port){this.clientHostPort = port;}
    public int getClientPort(){return clientHostPort;}
    
    public String getWhere() { return where;}
    public void setWhere(String where){this.where = where;}
//   public String vratiNazivSO(){return nazivSO;}
//   public void postaviNazivSO(String nazivSO){this.nazivSO = nazivSO;}
//   public void postaviWhere(String where) {this.where = where;}
//   public String vratiWhere() {return where;}
//   public void postaviJSONArray(JSONArray jsonArray) {this.jsonArray = jsonArray.toString();}
//   public void postaviOgranicenje(Ogranicenja og){this.og = og;}
//   public Ogranicenja vratiOgranicenje(){return og;}
//   public void postaviOgranicenjeL(Ogranicenja ogL){this.ogL = ogL;}
//   public Ogranicenja vratiOgranicenjeL(){return ogL;}



}
