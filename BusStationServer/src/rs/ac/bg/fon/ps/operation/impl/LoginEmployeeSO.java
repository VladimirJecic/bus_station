/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.impl;

import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.communication.exchangeable.TransferObject;
import rs.ac.bg.fon.ps.constraint.ConstraintsEmployee;
import rs.ac.bg.fon.ps.domain.Employee;
import rs.ac.bg.fon.ps.domain.GeneralDObject;
import rs.ac.bg.fon.ps.exception.constraint.ConstraintException;
import rs.ac.bg.fon.ps.operation.AbstractSO;

/**
 *
 * @author Vladimir Jecić
 */
public class LoginEmployeeSO extends AbstractSO {

    public LoginEmployeeSO(TransferObject tObj) {
        super(tObj);
    }

    @Override
    protected boolean preconditions() throws Exception {
        try {
            ConstraintsEmployee constraint = new ConstraintsEmployee(repository);
            GeneralDObject gdo = tObj.getGdo();
            constraint.preconditionNullAndInstanceofConstraint(gdo, Employee.class);
            constraint.preconditionSimpleValueConstraint_LoginEmployeeSO(gdo);
            return true;
        } catch (ConstraintException cex) {
            StringBuilder sb = new StringBuilder();
            sb.append(new Employee().message3()).append(":\nPreconditions not satisfied:")
                    .append(cex.getClass().getSimpleName()).append("\n").append(cex.getMessage());
            tObj.setMessage(sb.toString());
            return false;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    protected void executeSO() throws Exception {
        List<GeneralDObject> users = new ArrayList<>();
        Employee exampleEmployee = new Employee();
        try {
            users = repository.findAllRecords(exampleEmployee, users);
        } catch (Exception e) {
            tObj.setMessage(exampleEmployee.message3());
            throw e;
        } finally {
            if (!users.isEmpty()) {
                Employee employee = (Employee) tObj.getGdo();
                for (GeneralDObject user : users) {
                    if (((Employee) user).getUserName().equalsIgnoreCase(employee.getUserName())) {
                        if (((Employee) user).getPassword().equals(employee.getPassword())) {
                            tObj.setGdo(user);
                            tObj.setMessage(employee.message4());
                            tObj.setSignal(true);
                            return;
                        } else {
                            tObj.setMessage(employee.message5());
                            return;
                        }
                    }
                }
                tObj.setMessage(employee.message6());
            }
        }
    }

    @Override
    protected boolean postconditions() throws Exception {
        return true;
    }

}
