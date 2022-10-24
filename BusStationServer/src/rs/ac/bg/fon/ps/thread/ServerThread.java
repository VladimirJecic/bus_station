/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.thread;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import rs.ac.bg.fon.ps.logger.MyLogger;
import rs.ac.bg.fon.ps.util.constant.MapParams;
import rs.ac.bg.fon.ps.util.constant.MyServerConstants;
import rs.ac.bg.fon.ps.util.constant.PathConstants;
import rs.ac.bg.fon.ps.view.controller.MainController;

/**
 *
 * @author Vladimir Jecić
 */
public class ServerThread extends Thread {

    private boolean end;

    public ServerThread() {
        end = false;
    }

    @Override
    public void run() {
        startServer();
        MyLogger.getLogger(this.getClass()).log(Level.INFO, "ServerThread STOPPED!");

    }

    public void startServer() {
        List<HandleClientThread> clientList = new ArrayList();
        MainController.getInstance().putParam(MapParams.CLIENT_LIST, clientList);
        ServerSocket serverSocket = null;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(PathConstants.SERVER_CONFIG_PROPERTIES));
            int maxNumberOfClients = Integer.valueOf(properties.getProperty(MyServerConstants.MAX_CONNECTED_CLIENTS));
            int serverPort = Integer.valueOf(properties.getProperty(MyServerConstants.SERVER_PORT));
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Waiting for client connection...");
            serverSocket.setSoTimeout(4000);
            while (!end) {
                Socket socket = null;
                try {
                    clientList = (List<HandleClientThread>) MainController.getInstance().getParam(MapParams.CLIENT_LIST);
                    if (clientList.size() >= maxNumberOfClients) {
                        MyLogger.getLogger(this.getClass()).log(Level.INFO, "Max Number of Clients Reached!,connected:" + clientList.size());
                        Thread.sleep(5000);
                        continue;
                    }
                    socket = serverSocket.accept();
                } catch (SocketTimeoutException ste) {
                    if (!end) {
                        continue;
                    } else {
                        throw new Exception("ServerThread ended by User: OK");
                    }
                }
                System.out.println("Client Connected!");
                HandleClientThread client = new HandleClientThread(socket);
                System.out.println("Client detached to separate Thread!");
                clientList.add(client);
                client.start();
            }

        } catch (Exception ex) {
            if (!end) {
                ex.printStackTrace();
            }
            stopClientThreads(clientList);
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (Exception ex1) {
                    System.out.println("Couldn't close server socket\n" + ex1.getMessage());
                }
            }
            if (end) {
                MyLogger.getLogger(this.getClass()).log(Level.INFO, ex.getMessage());
            } else {
                MyLogger.getLogger(this.getClass()).log(Level.SEVERE, "ServerThread ended unexpectably:\n" + ex.getMessage());
            }

        }
    }

    private void stopClientThreads(List<HandleClientThread> clientList) {
        for (HandleClientThread client : clientList) {
            if (client.isAlive()) {
                client.stopClient();
//                client.interrupt();
                try {
                    client.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        clientList.clear();
    }

    public void stopServer() {
        end = true;
    }

}
