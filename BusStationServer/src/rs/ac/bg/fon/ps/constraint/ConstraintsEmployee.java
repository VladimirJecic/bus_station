/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.constraint;

import rs.ac.bg.fon.ps.domain.Employee;
import rs.ac.bg.fon.ps.domain.GeneralDObject;
import rs.ac.bg.fon.ps.exception.constraint.SimpleValueConstraintException;
import rs.ac.bg.fon.ps.repository.db.DbRepository;
import rs.ac.bg.fon.ps.util.MyStringBuilder;

/**
 *
 * @author Vladimir Jecić
 */
public class ConstraintsEmployee extends Constraints {

    public ConstraintsEmployee(DbRepository repository) {
        super(repository);
    }



    public void preconditionSimpleValueConstraint_LoginEmployeeSO(GeneralDObject gdo) throws SimpleValueConstraintException {
        Employee employee = (Employee) gdo;
        if (employee.getUserName() == null || employee.getPassword() == null) {
            String message2 = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", employee.message2());
            throw new SimpleValueConstraintException(message2);
        }
    }
}
