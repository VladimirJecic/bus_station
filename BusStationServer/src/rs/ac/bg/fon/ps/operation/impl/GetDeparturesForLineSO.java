/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.impl;

import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.communication.exchangeable.TransferObject;
import rs.ac.bg.fon.ps.constraint.ConstraintsLine;
import rs.ac.bg.fon.ps.domain.Departure;
import rs.ac.bg.fon.ps.domain.Line;
import rs.ac.bg.fon.ps.exception.constraint.ConstraintException;
import rs.ac.bg.fon.ps.operation.AbstractSO;

/**
 *
 * @author Vladimir Jecić
 */
public class GetDeparturesForLineSO extends AbstractSO {

    public GetDeparturesForLineSO(TransferObject tObj) {
        super(tObj);
    }

    @Override
    protected boolean preconditions() throws Exception {
        try {
            ConstraintsLine constraint = new ConstraintsLine(repository);
            constraint.preconditionNullAndInstanceofConstraint(tObj.getGdo(), Line.class);
            constraint.preconditionSimpleValueConstraint_GetDeparturesForLineSO(tObj.getGdo());
            return true;
        } catch (ConstraintException cex) {
            StringBuilder sb = new StringBuilder();
            sb.append(new Line().message24()).append(":\nPreconditions not satisfied:")
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
        Line line = (Line) tObj.getGdo();
        List<Departure> departureList = new ArrayList();
        Departure exampleDeparture = new Departure(line, null);
        try {
            departureList = repository.findRecords(exampleDeparture, departureList, exampleDeparture.getDeparturesForLine_Where());
        } catch (Exception e) {
            signal = false;
            throw e;
        } finally {
            tObj.setSignal(signal);
            if (signal) {
                line.setDepartures(departureList);
                tObj.setGdo(line);
                tObj.setMessage(line.message23());
            } else {
                tObj.setMessage(line.message24());
            }
        }
    }

    @Override
    protected boolean postconditions() throws Exception {
        return true;
    }

}
