/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.thread;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.communication.Receiver;
import rs.ac.bg.fon.ps.communication.Sender;
import rs.ac.bg.fon.ps.communication.exchangeable.TransferObject;
import rs.ac.bg.fon.ps.controller.Controller;
import rs.ac.bg.fon.ps.util.constant.SystemOperations;
import rs.ac.bg.fon.ps.domain.Employee;
import rs.ac.bg.fon.ps.domain.Station;
import rs.ac.bg.fon.ps.logger.MyLogger;
import rs.ac.bg.fon.ps.util.constant.MessageConstans;
import rs.ac.bg.fon.ps.util.constant.MapParams;
import rs.ac.bg.fon.ps.view.controller.MainController;
import rs.ac.bg.fon.ps.domain.GeneralDObject;
import rs.ac.bg.fon.ps.exception.constraint.SOException;
import rs.ac.bg.fon.ps.util.MyStringBuilder;

/**
 *
 * @author Vladimir Jecić
 */
public class HandleClientThread extends Thread {

    private Socket socket;
    private boolean end;
    private Employee client;
    private Sender sender;
    private Receiver receiver;
    private Socket messageSocket = null;
    private Sender messageSender = null;
    private Receiver messageReceiver = null;
    private TransferObject transferObject;
    private boolean signal = false;
//    private int clientPort;
//    private boolean connected = false;
    private ScheduledExecutorService serviceExecutor;
    private Duration maxClientInactiveTime;

    public HandleClientThread(Socket socket) {
        this.socket = socket;
        this.maxClientInactiveTime = Duration.ofHours(1);
        client = new Employee();
        client.setUserName("Not Logged in yet");
    }

    @Override
    public void run() {
        Duration maxClientInactiveTime;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
        try {
            int clientPort = introduction();
            scheduledCheckAlive(clientPort);
        } catch (Exception ex) {
            MyLogger.getLogger(this.getClass()).log(Level.WARNING, "Failed to setup message socket towards Client:" + ex.getMessage());
            return;
        }
        handleClient();
        MyLogger.getLogger(this.getClass()).log(Level.INFO, "HandleClientThread STOPPED!");

    }

    public void stopClient() {
        end = true;
        receiver.stopReceiver();
    }

    public Socket getSocket() {
        return socket;
    }

    public Employee getClient() {
        return client;
    }

    public void setClient(Employee client) {
        this.client = client;
    }

    private int introduction() throws Exception {
        int clientPort;
        transferObject = (TransferObject) receiver.receive(maxClientInactiveTime);
        MyLogger.getLogger(this.getClass()).log(Level.INFO, "Client introduction received: " + transferObject.getMessage());
        if (transferObject.getMessage().startsWith(MessageConstans.HELLO_SERVER)) {
            clientPort = transferObject.getClientPort();
            transferObject.setSignal(true);
            transferObject.setMessage(MessageConstans.HELLO_CLIENT + socket.getPort());
            transferObject.setClientPort(clientPort);
            sender.send(transferObject);
            MyLogger.getLogger(this.getClass()).log(Level.INFO, "Server response sent:" + transferObject.getMessage());
            return clientPort;
        } else {
            throw new Exception("Client message doesn't follow messaging protocol:" + transferObject.getMessage());
        }
    }

    private void scheduledCheckAlive(int clientPort) throws Exception {
        Duration maxHeartBeatDuration = Duration.ofSeconds(7);

        String clientAdress = socket.getInetAddress().getHostAddress();
        messageSocket = new Socket(clientAdress, clientPort);//clientPort 
        messageSender = new Sender(messageSocket);
        messageReceiver = new Receiver(messageSocket);
        serviceExecutor = Executors.newSingleThreadScheduledExecutor();
        serviceExecutor.scheduleAtFixedRate(() -> {
            try {
                TransferObject checkTransferObject = new TransferObject();
                checkTransferObject.setSignal(true);
                checkTransferObject.setMessage(MessageConstans.CHECK_ALIVE);
                messageSender.send(checkTransferObject);
                checkTransferObject = (TransferObject) messageReceiver.receive(maxHeartBeatDuration);
                if (checkTransferObject != null && !checkTransferObject.getSignal()) {
                    if (checkTransferObject.getMessage().equals(MessageConstans.YES_ALIVE)) {
                    }
                }
            } catch (Exception ex) {
                MyLogger.getLogger(this.getClass()).log(Level.INFO, "Client has stoped responding to heartBeat messages\n");
                messageReceiver.stopReceiver();
                stopClient();
                serviceExecutor.shutdownNow();
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    private void handleClient() {
        try {
            while (!end) {
                handleClientRequest();
            }
        } catch (TimeoutException tex) {
            //client didn't send request for a long time
            end = true;
            MyLogger.getLogger(this.getClass()).log(Level.INFO,
                    MyStringBuilder.concatStrings("HandleClientThread ", currentThread().getName(), "ended because client didn't respond for more than,", String.valueOf(maxClientInactiveTime.getSeconds()), "seconds: OK"));
        } catch (Exception ex) {
            if (end == true) {
                //client stopped responding to heart-beat messages
                MyLogger.getLogger(this.getClass()).log(Level.INFO, MyStringBuilder.concatStrings("HandleClientThread ", currentThread().getName(), " ended by User: OK"));
            } else {
                //unpredicted behavior!!!
//                ex.printStackTrace();
                MyLogger.getLogger(this.getClass()).log(Level.SEVERE, MyStringBuilder.concatStrings("HandleClientThread ", currentThread().getName(), "ended unexpectably: ERROR!\n"),ex);
            }
        } finally {
            if (!serviceExecutor.isShutdown()) {
                notifyClient(messageSender);
            }
            releaseAllResources();
        }
    }

    private void handleClientRequest() throws SOException, Exception {
        transferObject = (TransferObject) receiver.receive(maxClientInactiveTime);//wait for request
        if(transferObject== null || !(transferObject instanceof TransferObject)){
            throw new Exception("Received Object is null or not instance of "+TransferObject.class.getCanonicalName());
        }
        MyLogger.getLogger(this.getClass()).log(Level.INFO, "Client Request received:" + transferObject.getMessage());
        try {
            switch (transferObject.getOperation()) {
                case SystemOperations.LOGIN:
                    Controller.getInstance().loginEmployee(transferObject);
                    if (transferObject.getSignal()) {
                        client = (Employee) transferObject.getGdo();
                    }
                    break;
                case SystemOperations.SAVE_STATION:
                    Controller.getInstance().saveStation(transferObject);
                    break;
                case SystemOperations.GET_STATION_LIST:
                    Controller.getInstance().getStationList(transferObject);
                    break;
                case SystemOperations.FIND_STATIONS:
                    Controller.getInstance().findStations(transferObject);
                    break;
                case SystemOperations.DELETE_STATION:
                    Controller.getInstance().deleteStation(transferObject);
                    break;
                case SystemOperations.SAVE_LINE:
                    Controller.getInstance().saveLine(transferObject);
                    break;
                case SystemOperations.GET_LINE_LIST:
                    Controller.getInstance().getLineList(transferObject);
                    break;
                case SystemOperations.FIND_LINES:
                    Controller.getInstance().findLines(transferObject);
                    break;
                case SystemOperations.GET_DEPARTURES_FOR_LINE:
                    Controller.getInstance().getDeparturesForLine(transferObject);
                    break;
                case SystemOperations.DELETE_LINE:
                    Controller.getInstance().deleteLine(transferObject);
                    break;
                case SystemOperations.SAVE_DEPARTURES:
                    Controller.getInstance().saveDepartures(transferObject);
                    break;
                case SystemOperations.DELETE_DEPARTURE:
                    Controller.getInstance().deleteDeparture(transferObject);
                    break;
                case SystemOperations.GET_TICKETS_FOR_DEPARTURE:
                    Controller.getInstance().getTicketsForDeparture(transferObject);
                    break;
                case SystemOperations.SAVE_TICKET:
                    Controller.getInstance().saveTicket(transferObject);
                    break;
                case SystemOperations.DELETE_TICKET:
                    Controller.getInstance().deleteTicket(transferObject);
                    break;
                default:
                    throw new SOException("Illegal SystemOperation:" + transferObject.getOperation());
            }
        } catch (SOException soE) {
            //unpredicted behavior in one of SOperation classes!!!
            transferObject.setMessage("System couldn't execute operation: Server Error!\n" + transferObject.getMessage());
            throw soE;
        } finally {
            sender.send(transferObject);
            MyLogger.getLogger(this.getClass()).log(Level.INFO, "Server Response sent:" + transferObject.getMessage());
//            transferObject =null;
//            System.gc();
        }

    }

    private void notifyClient(Sender messageSender) {
        TransferObject tranferObjectStop = new TransferObject();
        tranferObjectStop.setMessage(MessageConstans.SERVER_STOPPED);
        MyLogger.getLogger(this.getClass()).log(Level.INFO, "Server Response sent:" + tranferObjectStop.getMessage());
        try {
            messageSender.send(tranferObjectStop);
        } catch (Exception ex) {
            MyLogger.getLogger(this.getClass()).log(Level.WARNING, "Couldn't notify client of this program's termination\n" + ex.getMessage());
        }

    }

    private void releaseAllResources() {
        if (serviceExecutor != null && !serviceExecutor.isShutdown()) {
            serviceExecutor.shutdown();
        }
        closeSockets();
    }

    private void closeSockets() {
        try {
            if (messageSocket != null && !messageSocket.isClosed()) {
                messageSocket.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

}
