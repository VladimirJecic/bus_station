/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.thread;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import rs.ac.bg.fon.ps.util.constant.MapParams;
import rs.ac.bg.fon.ps.view.controller.MainController;
import rs.ac.bg.fon.ps.view.form.component.table.TableModelClient;

/**
 *
 * @author Vladimir Jecić
 */
public class RefreshTableThread extends Thread {

    JTable tblClient;
    boolean end;

    public RefreshTableThread(JTable tblServer) {
        this.tblClient = tblServer;
        this.end = false;
    }

    @Override
    public void run() {
        TableModelClient model = null;
        List<HandleClientThread> clientList = null;
        while (!end) {
            model = (TableModelClient) tblClient.getModel();
            if (model != null) {
                try {
                    Thread.sleep(2000);//moze i da se ucitava iz property fajla
                } catch (Exception e) {
                    System.out.println("Thread awoken:" + LocalTime.now());
                }
                clientList = (List<HandleClientThread>) MainController.getInstance().getParam(MapParams.CLIENT_LIST);
                if (clientList == null) {
                    clientList = new ArrayList();
                }
                removeDeadClients(clientList);
                model.setClientList(clientList);

            }

        }
    }

    public void stopRTThread() {
        end = true;
    }

    private void removeDeadClients(List<HandleClientThread> clientList) {
        List<Integer> deadArray = new ArrayList();
        try {   
            //find inactive
            int size1 = clientList.size();
            for (int i = 0; i < clientList.size(); i++) {
                if (!clientList.get(i).isAlive()) {
                    deadArray.add(i);
                }
            }
            //stop && remove inactive Client Threads
            int size2 = deadArray.size();
            for (int j = 0; j < size2; j++) {
                clientList.remove((int)deadArray.get(j));
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        deadArray = null;
    }

}
