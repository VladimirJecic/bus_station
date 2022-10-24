/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.constraint;

import rs.ac.bg.fon.ps.domain.GeneralDObject;
import rs.ac.bg.fon.ps.exception.constraint.ComplexValueConstraintException;
import rs.ac.bg.fon.ps.exception.constraint.ConstraintException;
import rs.ac.bg.fon.ps.exception.constraint.SimpleValueConstraintException;
import rs.ac.bg.fon.ps.exception.constraint.StructureConstraintException;
import rs.ac.bg.fon.ps.repository.db.DbRepository;
import rs.ac.bg.fon.ps.util.MyStringBuilder;

/**
 * All Constraints except postConditionStructureConstraint_update(UPDATE CASCADES)
 * will be implemented
 * @author Vladimir Jecić
 */
public class Constraints {
    
    protected final DbRepository repository;

    public Constraints(DbRepository repository) {
        this.repository = repository;
    }

    public void preconditionNullAndInstanceofConstraint(GeneralDObject gdo, Class gdoClass) throws ConstraintException, InstantiationException, IllegalAccessException {
        if (gdo == null || !(gdoClass.isInstance(gdo))) {
            String message1A =MyStringBuilder.concatStrings(this.getClass().getSimpleName(),":\n",((GeneralDObject) gdoClass.newInstance()).message1A());
            throw new ConstraintException(message1A);
        }
    }
    
    public void preconditionSimpleValueConstraint_insert(GeneralDObject gdo) throws SimpleValueConstraintException,Exception {
    }
    public void preconditionSimpleValueConstraint_update(GeneralDObject gdo) throws SimpleValueConstraintException,Exception {
    }
    public void preconditionSimpleValueConstraint_delete(GeneralDObject gdo) throws SimpleValueConstraintException, Exception {
    }
    
    public void preconditionComplexValueConstraint_insert(GeneralDObject gdo) throws ComplexValueConstraintException,Exception {
    }
    public void preconditionComplexValueConstraint_update(GeneralDObject gdo) throws ComplexValueConstraintException,Exception {
    }
    public void preconditionComplexValueConstraint_delete(GeneralDObject gdo) throws ComplexValueConstraintException,Exception {
    }
    
    public void preconditionStructureConstraint_insert(GeneralDObject gdo) throws StructureConstraintException,Exception {
    }  
    public void preconditionStructureConstraint_update(GeneralDObject gdo) throws StructureConstraintException,Exception {
    }
    public void preconditionStructureConstraint_delete(GeneralDObject gdo) throws StructureConstraintException,Exception {
    }
    
    
    
    public void postconditionSimpleValueConstraint_insert(GeneralDObject gdo) throws SimpleValueConstraintException,Exception {
    }
    public void postconditionSimpleValueConstraint_update(GeneralDObject gdo) throws SimpleValueConstraintException,Exception {
    }
    public void postconditionSimpleValueConstraint_delete(GeneralDObject gdo) throws SimpleValueConstraintException,Exception {
    }
 

    public void postconditionComplexValueConstraint_update(GeneralDObject gdo) throws ComplexValueConstraintException,Exception {
    }
    public void postconditionComplexValueConstraint_insert(GeneralDObject gdo) throws ComplexValueConstraintException,Exception {
    }
    public void postconditionComplexValueConstraint_delete(GeneralDObject gdo) throws ComplexValueConstraintException,Exception {
    }
    
    
    public void postconditionStructureConstraint_insert(GeneralDObject gdo) throws StructureConstraintException,Exception {
    }
    public void postconditionStructureConstraint_update(GeneralDObject gdo) throws StructureConstraintException,Exception {
    }
    public void postconditionStructureConstraint_delete(GeneralDObject gdo) throws StructureConstraintException,Exception {
    }
    
    protected void setPrimaryKey(GeneralDObject gdo) throws Exception{
    }
    protected String getClassName(){
        return this.getClass().getSimpleName()+":";
    }
}
