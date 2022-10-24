/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.communication;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.logger.MyLogger;

/**
 *
 * @author Vladimir Jecić
 */
public class Sender {

    private Socket socket;
    ObjectOutputStream out = null;

    public Sender(Socket socket) {
        this.socket = socket;
        //ObjOutputStream early initialization to ensure that outputstreams are initialized
        //before inputstream and prevent inputstream from blocking in constructors
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error opening OutputStream!\n" + ex.getMessage());
        }
    }

    public void send(Object object) throws Exception {
        try {
//            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(object);
            out.flush();//bitno je kod bufferovanih objekata,kako bi se oni
            //zaista i poslali cim dodju na output stream(iako su malih dimenzija)
            //ovde nije neophondno, ali  ne moze da skodi
        } catch (Exception ex) {
//            ex.printStackTrace();
            throw new Exception("Error sending Object!\n" + ex.getMessage());
        }
    }
}
