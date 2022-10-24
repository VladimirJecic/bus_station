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
import rs.ac.bg.fon.ps.domain.Line;
import rs.ac.bg.fon.ps.domain.Stop;
import rs.ac.bg.fon.ps.exception.constraint.ConstraintException;
import rs.ac.bg.fon.ps.operation.AbstractSO;
import rs.ac.bg.fon.ps.util.MyStringBuilder;
import rs.ac.bg.fon.ps.util.constant.ConstraintOperation;
import rs.ac.bg.fon.ps.util.constant.State;

/**
 *
 * @author Vladimir Jecić
 */
public class SaveLineSO extends AbstractSO {

    public SaveLineSO(TransferObject tObj) {
        super(tObj);
    }

    @Override
    protected boolean preconditions() throws Exception {
        try {
            executePreconditions(tObj.getGdo());
            Line line = (Line) tObj.getGdo();
            if (line.getState().equals(State.REMOVED) || line.getState().equals(State.NOT_CHANGED)) {
                throw new ConstraintException(line.message10() + line.getState());
            }
            return true;
        } catch (ConstraintException cex) {
            StringBuilder sb = new StringBuilder();
            sb.append(new Line().message12()).append(":\nPreconditions not satisfied:")
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
        ConstraintException constraintException = null;
        Line line = (Line) tObj.getGdo();
        try {
            String operation = ConstraintOperation.forState(line.getState());
            String className = repository.getClass().getCanonicalName();
            String methodName = MyStringBuilder.concatStrings(operation, "Record");
            Method method = Class.forName(className).getMethod(methodName, GeneralDObject.class);
            if (!(boolean) method.invoke(repository, line)) {
                signal = false;
            }
            List<Stop> stopList = line.getStops();
            if (stopList != null) {
                for (Object objectOne : stopList) {
                    //must be checked now,because there is a structure constraint on insert operation
                    //that checks whether referenced line really exists in database
                    executePreconditions(objectOne);
                    Stop stop = (Stop) objectOne;
                    operation = ConstraintOperation.forState(stop.getState());
                    if (operation != null && !operation.isEmpty()) {
                        methodName = MyStringBuilder.concatStrings(operation, "Record");
                        method = Class.forName(className).getMethod(methodName, GeneralDObject.class);
                        if (!(boolean) method.invoke(repository, stop)) {
                            signal = false;
                        } else {
                            executePostconditions(stop);
                        }
                    }
                }
            }
            List<Departure> departureList = line.getDepartures();
            if (departureList != null) {
                for (Object objectTwo : departureList) {
                    //must be checked now,because there is a structure constraint on insert operation
                    //that checks whether referenced line really exists in database
                    executePreconditions(objectTwo);
                    Departure departure = (Departure) objectTwo;
                    operation = ConstraintOperation.forState(departure.getState());
                    if (operation != null && !operation.isEmpty()) {
                        methodName = MyStringBuilder.concatStrings(operation, "Record");
                        method = Class.forName(className).getMethod(methodName, GeneralDObject.class);
                        if (!(boolean) method.invoke(repository, departure)) {
                            signal = false;
                        } else {
                            executePostconditions(departure);
                        }
                    }
                }
            }
        } catch (ConstraintException cex) {
            constraintException = cex;
            signal = false;
        } catch (Exception e) {
            signal = false;
            throw e;
        } finally {
            tObj.setSignal(signal);
            if (signal) {
                tObj.setMessage(line.message11());
            } else if (constraintException == null) {
                tObj.setMessage(line.message12());
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(line.message12()).append(":\nPreconditions not satisfied:")
                        .append(constraintException.getClass().getSimpleName()).append("\n").append(constraintException.getMessage());
                tObj.setMessage(sb.toString());
            }
        }
    }

    @Override
    protected boolean postconditions() throws Exception {
        try {
            GeneralDObject gdo = tObj.getGdo();
            executePostconditions(gdo);
            return true;
        } catch (ConstraintException cex) {
            StringBuilder sb = new StringBuilder();
            sb.append(new Line().message12()).append(":\nPostconditions not satisfied:")
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
