/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import rs.ac.bg.fon.ps.communication.exchangeable.TransferObject;
import rs.ac.bg.fon.ps.constraint.Constraints;
import rs.ac.bg.fon.ps.domain.GeneralDObject;
import rs.ac.bg.fon.ps.exception.constraint.ConstraintException;
import rs.ac.bg.fon.ps.repository.Repository;
import rs.ac.bg.fon.ps.repository.db.DbRepository;
import rs.ac.bg.fon.ps.repository.db.impl.RepositoryDbGeneric;
import rs.ac.bg.fon.ps.util.MyStringBuilder;
import rs.ac.bg.fon.ps.util.constant.ConstraintOperation;

/**
 *
 * @author Vladimir Jecić
 */
public abstract class AbstractSO {

    protected final TransferObject tObj;
    protected final DbRepository repository;

    public AbstractSO(TransferObject tObj) {
        this.tObj = tObj;
        this.repository = new RepositoryDbGeneric();
    }

    /**
     * Template method and pattern is used here, generalExecuteSo is skeleton
     * with functions that are part part of the "template" that Subclasses of
     * the base class "fill in" with specific algorithms that vary from one
     * subclass to another. It is important that subclasses do not override the
     * template method itself. That's why this method is final. Open-closed
     * pattern - generalExecuteSo is "closed" for modification,but in the same
     * time "executeSO" is open for extension to its subclasses.
     *
     * @throws Exception
     */
    public final synchronized void generalExecuteSO() throws Exception {
        Exception catchedException = null;
        startTransaction();
        try {
            if (preconditions()) {
                executeSO();
                if (tObj.getSignal() == true) {
                    if (postconditions()) {
                        commitTransaction();
                    }
                }
            }
        } catch (Exception e) {
            catchedException = e;
            rollbackTransaction();
            throw e;
        } finally {
            if(!tObj.getSignal() && catchedException==null){
                rollbackTransaction();//otherwise lock on table's row won't be released,and deadlock will occur on the next operation
            }
            endTransaction();
        }

    }

    private void startTransaction() throws Exception {
        repository.connect();
    }

    private void commitTransaction() throws Exception {
        boolean signal = tObj.getSignal();
        if (signal == true) {
            repository.commit();
        }
    }

    private void rollbackTransaction() throws Exception {
        repository.rollback();
    }
 
    private void endTransaction() throws Exception {
        repository.disconnect();
    }

    //Subclasses must implement these 3 methods
    abstract protected boolean preconditions() throws Exception;
    abstract protected void executeSO() throws Exception;
    abstract protected boolean postconditions() throws Exception;
    ///////////////////////////////////////////

    protected void executePreconditions(Object object) throws IllegalStateException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ConstraintException, Exception {
        //check if object is null or not instance of GeneralDomainObject
        if (object == null || !(object.getClass().getSuperclass().equals(GeneralDObject.class))) {
            String message1A = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", ((GeneralDObject) GeneralDObject.class.newInstance()).message1A());
            throw new ConstraintException(message1A);
        }
        GeneralDObject gdo = (GeneralDObject) object;
        //check if state is null
        if (gdo.getState() == null) {
            String message1C = MyStringBuilder.concatStrings(this.getClass().getSimpleName(), ":\n", GeneralDObject.class.newInstance().message1C());
            throw new ConstraintException(message1C);
        }
        //get operation and check if operation is null or GeneralDomainObject has state NOT_CHANGED,if yes,return
        String operation = ConstraintOperation.forState(gdo.getState());
        if (operation == null || operation.isEmpty()) {
            return;
        }
        String methodName;
        Method method;
        String className = "rs.ac.bg.fon.ps.constraint.Constraints" + gdo.getClass().getSimpleName();
        String constraintType[] = {"SimpleValueConstraint_", "ComplexValueConstraint_", "StructureConstraint_"};
        try {
            Constructor constructor = Class.forName(className).getConstructor(DbRepository.class);
            Constraints constraintsGdo = (Constraints) constructor.newInstance(repository);
            for (int i = 0; i < 3; i++) {
                methodName = MyStringBuilder.concatStrings("precondition", constraintType[i], operation);
                method = Class.forName(className).getMethod(methodName, GeneralDObject.class);
                method.invoke(constraintsGdo, gdo);
            }
        } catch (IllegalStateException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException ex) {
            throw new Exception("Wrong parameters/undefined operation in executePreconditions()", ex);
        } catch (InvocationTargetException iex) {
            if (iex.getCause() instanceof ConstraintException) {
                throw (ConstraintException) (iex.getCause());
            } else {
                throw new Exception("Unpredicted type of exception", iex.getCause());
            }
        }

    }

    protected void executePostconditions(GeneralDObject gdo) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ConstraintException, Exception {
        String methodName;
        Method method;
        String operation = ConstraintOperation.forState(gdo.getState());
        String className = "rs.ac.bg.fon.ps.constraint.Constraints" + gdo.getClass().getSimpleName();
        String constraintType[] = {"SimpleValueConstraint_", "ComplexValueConstraint_", "StructureConstraint_"};
        try {
            Constructor constructor = Class.forName(className).getConstructor(DbRepository.class);
            Constraints constraintsGdo = (Constraints) constructor.newInstance(repository);
            for (int i = 0; i < 3; i++) {
                //example:postconditionSimpleValueConstraint_insert(gdo);
                if (!operation.isEmpty()) {
                    methodName = MyStringBuilder.concatStrings("postcondition", constraintType[i], operation);
                    method = Class.forName(className).getMethod(methodName, GeneralDObject.class);
                    method.invoke(constraintsGdo, gdo);
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException ex) {
            throw new Exception("Wrong parameters/undefined operation in executePostconditions()", ex);
        } catch (InvocationTargetException iex) {
            if (iex.getCause() instanceof ConstraintException) {
                throw (ConstraintException) (iex.getCause());
            } else {
                throw new Exception("Unpredicted type of exception", iex.getCause());
            }
        }
    }

}
