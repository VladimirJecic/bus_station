/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.communication;

import java.io.FileInputStream;
import java.net.Socket;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import rs.ac.bg.fon.ps.util.constant.SystemOperations;
import rs.ac.bg.fon.ps.communication.exchangeable.TransferObject;
import rs.ac.bg.fon.ps.domain.Departure;
import rs.ac.bg.fon.ps.domain.Employee;
import rs.ac.bg.fon.ps.domain.Line;
import rs.ac.bg.fon.ps.domain.Station;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.logger.MyLogger;
import rs.ac.bg.fon.ps.util.constant.MessageConstans;
import rs.ac.bg.fon.ps.domain.GeneralDObject;
import rs.ac.bg.fon.ps.util.constant.MyServerConstants;
import rs.ac.bg.fon.ps.util.constant.PathConstants;

/**
 *
 * @author Vladimir Jecić
 */
public class Communication {

    private Socket socket;
    private final Sender sender;
    private final Receiver receiver;
    private static Communication instance;
    private ServerMessageHandler messageHandler;
    private Duration maxResponseWaitDuration;

    private Communication() throws Exception {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(PathConstants.SERVER_CONFIG_PROPERTIES));
            String serverAddress = properties.getProperty(MyServerConstants.SERVER_ADDRESS);
            int serverPort = Integer.valueOf(properties.getProperty(MyServerConstants.SERVER_PORT));
            socket = new Socket(serverAddress, serverPort);
        } catch (Exception ex) {
            this.instance = null;
            throw ex;
        }
        sender = new Sender(socket);
        receiver = new Receiver(socket);
        maxResponseWaitDuration = Duration.ofSeconds(25);

        messageHandler = new ServerMessageHandler();
        messageHandler.start();
        Thread.sleep(100);
        introduction(messageHandler.getHostingPort());
    }

    public synchronized static Communication getInstance() throws Exception {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }

    private synchronized String communicate(TransferObject request, GeneralDObject returnParam, List<GeneralDObject> returnParamList) throws Exception {
        sender.send(request);
        MyLogger.getLogger(this.getClass()).log(Level.INFO, "Client Request sent:" + request.getMessage());
        TransferObject response = (TransferObject) receiver.receive(maxResponseWaitDuration);//wait for answer 10s
        MyLogger.getLogger(this.getClass()).log(Level.INFO, "Server Response received:" + response.getMessage());

        if (response.getSignal()) {
            //@param
            if (returnParam != null && response.getGdo() != null) {
                returnParam.paste(response.getGdo());
            }
            //@paramList
            if (returnParamList != null && response.getListGdo() != null) {
                returnParamList.clear();
                List<GeneralDObject> listGdo = (List<GeneralDObject>) response.getListGdo();
                for (GeneralDObject gdo : listGdo) {
                    returnParamList.add(gdo);
                }
            }
            return response.getMessage();
        } else {
            throw new Exception(response.getMessage());
        }

    }

    public String loginEmployee(Employee employee) throws Exception {
        TransferObject request = new TransferObject(employee, SystemOperations.LOGIN);
        return communicate(request, employee, null);
    }//1

    public String saveStation(Station station) throws Exception {
        TransferObject request = new TransferObject(station, SystemOperations.SAVE_STATION);
        return communicate(request, null, null);

    }//2

    public String getStationList(List<?> stationList) throws Exception {
        TransferObject request = new TransferObject(SystemOperations.GET_STATION_LIST);
        return communicate(request, null, (List<GeneralDObject>) stationList);
    }//3

    public String findStations(String where,List<?> stationList) throws Exception {
        TransferObject request = new TransferObject(where, SystemOperations.FIND_STATIONS);
        return communicate(request, null, (List<GeneralDObject>) stationList);
    }//4

    public String deleteStation(Station station) throws Exception {
        TransferObject request = new TransferObject(station, SystemOperations.DELETE_STATION);
        return communicate(request, null, null);
    }//5

    public String saveLine(Line line) throws Exception {
        TransferObject request = new TransferObject(line, SystemOperations.SAVE_LINE);
        return communicate(request, null, null);
    }//6

    public String getLineList(List<?> lineList) throws Exception {
        TransferObject request = new TransferObject(SystemOperations.GET_LINE_LIST);
        return communicate(request, null, (List<GeneralDObject>) lineList);
    }//7

    public String findLines(String where, List<?> lineList) throws Exception {
        TransferObject request = new TransferObject(where, SystemOperations.FIND_LINES);
        return communicate(request, null, (List<GeneralDObject>) lineList);
    }//8

    public String getDeparturesForLine(Line line) throws Exception {
        TransferObject request = new TransferObject(line,SystemOperations.GET_DEPARTURES_FOR_LINE);
        return communicate(request, line, null);
    }//9

    public String deleteLine(Line line) throws Exception {
        TransferObject request = new TransferObject(line, SystemOperations.DELETE_LINE);
        return communicate(request, null, null);
    }//10

    public String saveDepartures(List<Departure> departureList) throws Exception {
        TransferObject request = new TransferObject((List<GeneralDObject>)(List<?>) departureList,SystemOperations.SAVE_DEPARTURES);
        return communicate(request, null, null);
    }//11

    public String deleteDeparture(Departure departure) throws Exception {
        TransferObject request = new TransferObject(departure, SystemOperations.DELETE_DEPARTURE);
        return communicate(request, null, null);
    }//12

    public String getTicketsForDeparture(Departure departure) throws Exception {
        TransferObject request = new TransferObject(departure,SystemOperations.GET_TICKETS_FOR_DEPARTURE);
        return communicate(request, departure, null);
    }//13

    public String saveTicket(Ticket ticket) throws Exception {
        TransferObject request = new TransferObject(ticket,SystemOperations.SAVE_TICKET);
        return communicate(request, null, null);
    }//14

    public String deleteTicket(Ticket ticket) throws Exception {
        TransferObject request = new TransferObject(ticket, SystemOperations.DELETE_TICKET);
        return communicate(request, null, null);
    }//15

    private String introduction(int hostingPort) throws Exception {
        TransferObject request = new TransferObject();
        request.setMessage(MessageConstans.HELLO_SERVER + hostingPort);
        request.setClientPort(hostingPort);
        return communicate(request, null, null);
    }

    public String endCommunication() throws Exception {
        messageHandler.setExitOnClose(false);//don't terminate this program with messageHandler
        messageHandler.stopHandler();
        messageHandler.join();
        messageHandler = null;
        instance = null;
        return "Communication with Server ended by User:OK";
    }

}
