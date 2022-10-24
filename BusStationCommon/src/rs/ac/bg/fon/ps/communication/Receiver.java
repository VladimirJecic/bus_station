/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.communication.exchangeable.TransferObject;
import rs.ac.bg.fon.ps.domain.Employee;
import rs.ac.bg.fon.ps.domain.GeneralDObject;
import rs.ac.bg.fon.ps.domain.Line;
import rs.ac.bg.fon.ps.logger.MyLogger;

/**
 *
 * @author Vladimir Jecić
 */
public class Receiver {

    private Socket socket;
    InputStream in = null;
    ObjectInputStream objIn = null;
    private boolean end;

    public Receiver(Socket socket) {
        this.socket = socket;
        try {
            in = socket.getInputStream();
            objIn = new ObjectInputStream(in);
        } catch (Exception ex) {
//            ex.printStackTrace();
            System.out.println("Error opening InputStream!\n" + ex.getMessage());
        }
    }

    public Object receive(Duration duration) throws Exception {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        try {
            Runnable task = () -> {
                System.out.println(LocalTime.now() + "ReceiverTimerTask finished");
            };
            ScheduledFuture futureOfTask = executor.schedule(task, duration.toMillis(), TimeUnit.MILLISECONDS);
            while (!end) {
                if (in.available() != 0) {
//                    Object object = objIn.readObject();
//                    GeneralDObject gdo = ((TransferObject) object).getGdo();
//                    if (gdo != null) {
//                        if (gdo instanceof Line) {
//                            String firstName = ((Line) gdo).getEmployee().getFirstName();
//                            if (firstName == null){
//                                int i =0;
//                            }
//                            MyLogger.getLogger(this.getClass()).log(Level.WARNING, firstName);
//                        }
//                    }
                    return objIn.readObject();
                }
                if (futureOfTask.isDone()) {
                    executor.shutdownNow();
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                    throw new TimeoutException("Client/Server didn't respond for more than " + duration.getSeconds() + "seconds, Socket closed");
                }
            }
        } catch (TimeoutException tex) {
            throw tex;
        } catch (Exception ex) {
            MyLogger.getLogger(this.getClass()).log(Level.WARNING, ex.getMessage());
            throw new Exception("Error receiving Object!\n" + ex.getMessage());
        } finally {
            if (executor != null && !executor.isShutdown()) {
                executor.shutdownNow();
            }
        }
        throw new Exception("receive finished with flag end=" + end);
    }

    public void stopReceiver() {
        end = true;
    }

//    public Object receive(int seconds) throws Exception {
//        try {
//            Timer timer = new Timer(false);//not 
//            ReceiverTask task = new ReceiverTask(timer);
//            int miliSeconds = seconds * 1000;
//            timer.schedule(task, miliSeconds);
//            while (!end) {
//                if (in.available() != 0) {
//                    return objIn.readObject();
//                }
//                if (task.isFinished()) {
//                    if(socket!=null && !socket.isClosed()){
//                        socket.close();
//                    }
//                    throw new TimeoutException("Socket closed, Client/Server didn't respond for more than " + seconds + "seconds");
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            MyLogger.getLogger(this.getClass()).log(Level.WARNING, ex.getMessage());
//            throw new Exception("Error receiving Object!\n" + ex.getMessage());
//        }
//        throw new Exception("receive finished with flag end="+end);
//    }
}
