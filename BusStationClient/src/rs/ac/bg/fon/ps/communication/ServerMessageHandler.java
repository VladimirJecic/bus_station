/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.communication;

import com.sun.media.jfxmedia.logging.Logger;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import rs.ac.bg.fon.ps.communication.exchangeable.TransferObject;
import rs.ac.bg.fon.ps.logger.MyLogger;
import rs.ac.bg.fon.ps.util.MyStringBuilder;
import rs.ac.bg.fon.ps.util.constant.MessageConstans;

/**
 *
 * @author Vladimir Jecić
 */
public class ServerMessageHandler extends Thread {

    private int hostingPort;
    private boolean end;
    private Socket socket;
    private ServerSocket serverSocket;
    private Sender sender;
    private Duration maxWaitDuration;
    private boolean exitOnClose;

    ServerMessageHandler() {
        this.end = false;
        exitOnClose = true;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(0);
            hostingPort = serverSocket.getLocalPort();
            System.out.println("Waiting for server connection...");
            socket = serverSocket.accept();
            System.out.println("Server Connected to this Client!");
            sender = new Sender(socket);
            maxWaitDuration = Duration.ofSeconds(15);
            handleServer();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        close();
    }

    private void handleServer() throws Exception {
        Receiver receiver = new Receiver(socket);
        TransferObject tObj = null;
        while (!end) {
            try {
                tObj = (TransferObject) receiver.receive(maxWaitDuration);
            } catch (TimeoutException exception) {
                MyLogger.getLogger(this.getClass()).log(Level.INFO, exception.getMessage());
                end = true;
                continue;
            } catch (Exception exception) {
                MyLogger.getLogger(this.getClass()).log(Level.INFO, "Server stopped unexpectedly... ending program" + exception.getMessage());
                end = true;
                continue;
            }

            if (tObj.getMessage().equals(MessageConstans.SERVER_STOPPED)) {
                MyLogger.getLogger(this.getClass()).log(Level.INFO, "Server response received:" + tObj.getMessage(), ", ending client program:OK");
                end = true;
            }
            if (tObj.getMessage().equals(MessageConstans.CHECK_ALIVE)) {
                tObj.setSignal(true);
                tObj.setMessage(MessageConstans.YES_ALIVE);
                System.out.println(MyStringBuilder.concatStrings(LocalTime.now().toString(),":Client response sent:",tObj.getMessage()));
                sender.send(tObj);
            }
        }

    }

    private void close() {
        if (exitOnClose) {
            System.exit(0);
        }
    }

    public int getHostingPort() {
        return hostingPort;
    }

    public void stopHandler() {
        end = true;
    }

    public void setExitOnClose(boolean exitOnClose) {
        this.exitOnClose = exitOnClose;
    }

}
