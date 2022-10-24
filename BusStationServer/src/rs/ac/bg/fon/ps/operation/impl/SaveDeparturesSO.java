/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.impl;

import java.lang.reflect.Method;
import java.util.List;
import rs.ac.bg.fon.ps.communication.exchangeable.TransferObject;
import rs.ac.bg.fon.ps.domain.Departure;
import rs.ac.bg.fon.ps.domain.GeneralDObject;
import rs.ac.bg.fon.ps.exception.constraint.ConstraintException;
import rs.ac.bg.fon.ps.operation.AbstractSO;
import rs.ac.bg.fon.ps.util.MyStringBuilder;
import rs.ac.bg.fon.ps.util.constant.ConstraintOperation;
import rs.ac.bg.fon.ps.util.constant.State;

/**
 *
 * @author Vladimir Jecić
 */
public class SaveDeparturesSO extends AbstractSO {

    public SaveDeparturesSO(TransferObject tObj) {
        super(tObj);
    }

    @Override
    protected boolean preconditions() throws Exception {
        try {
            List<?> departureList = tObj.getListGdo();
            if (departureList == null || departureList.isEmpty()) {
                throw new ConstraintException(new Departure().message1B());
            } else {
                for (Object object : departureList) {
                    executePreconditions(object);
                    if (((GeneralDObject) object).getState().equals(State.REMOVED)) {
                        throw new ConstraintException(new Departure().message11() + State.REMOVED);
                    }
                }
            }
            return true;
        } catch (ConstraintException cex) {
            StringBuilder sb = new StringBuilder();
            sb.append(new Departure().message13()).append(":\nPreconditions not satisfied:")
                    .append(cex.getClass().getSimpleName()).append("\n").append(cex.getMessage());
            tObj.setMessage(sb.toString());
            return false;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    protected void executeSO() throws Exception {
        boolean signal = true;
        List<GeneralDObject> departureList = tObj.getListGdo();
        String operation = null;
        try {
            for (GeneralDObject departure : departureList) {
                operation = ConstraintOperation.forState(departure.getState());
                if (operation != null && !operation.isEmpty()) {
                    String className = repository.getClass().getCanonicalName();
                    String methodName = MyStringBuilder.concatStrings(operation, "Record");
                    Method method = Class.forName(className).getMethod(methodName, GeneralDObject.class);
                    if (!(boolean) method.invoke(repository, departure)) {
                        signal = false;
                    }
                }
            }
            //**There is no need to repeat the same for tickets because they are added/deleted in separate classes
//            List<Ticket> ticketList=  ((Departure)departure).getTickets();
//            if(ticketList!=null){
//                for (Object object : ticketList) {
//                    //must be checked now,because there is a structure constraint on insert operation
//                    //that checks whether referenced line really exists in database
//                    executePreconditions(object);
//                    Ticket ticket = (Ticket) object;
//                    operation = ConstraintOperation.forState(ticket.getState());
//                    if(operation!=null && !operation.isEmpty()){
//                        methodName = MyStringBuilder.concatStrings(operation,"Record");
//                        method = Class.forName(className).getMethod(methodName, GeneralDObject.class);
//                        if(!(boolean) method.invoke(repository, ticket)){signal= false;}
//                    }
//                }
//            }

        } catch (Exception e) {
            signal = false;
            throw e;
        } finally {
            tObj.setSignal(signal);
            if (signal) {
                tObj.setMessage(new Departure().message12());
            } else {
                if (tObj.getMessage().isEmpty()) {
                    tObj.setMessage(new Departure().message13());
                }
            }
        }
    }

    @Override
    protected boolean postconditions() throws Exception {
        try {
            List<Departure> departureList = (List<Departure>) (List<?>) tObj.getListGdo();
            for (Departure departure : departureList) {
                executePostconditions(departure);
                //**There is no need to repeat the same for tickets because they are added/deleted in separate classes
//                List<Ticket> ticketList = departure.getTickets();
//                for (Ticket ticket : ticketList) {
//                    executePostconditions(ticket);
//                }
            }
            return true;
        } catch (ConstraintException cex) {
            StringBuilder sb = new StringBuilder();
            sb.append("System couldn't execute operation:\nPostconditions not satisfied :")
                    .append(cex.getClass().getSimpleName()).append("\n").append(cex.getMessage());
            tObj.setMessage(sb.toString());
            tObj.setSignal(false);
            return false;
        } catch (Exception ex) {
            tObj.setSignal(false);
            throw ex;
        }
    }

}
