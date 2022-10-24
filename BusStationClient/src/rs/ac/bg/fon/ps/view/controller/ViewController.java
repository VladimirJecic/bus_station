/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.exception.ValidationException;
import rs.ac.bg.fon.ps.util.constant.MapParams;
import rs.ac.bg.fon.ps.util.constant.UseCaseConstants;

/**
 *
 * @author Vladimir Jecić
 * @param <T>
 */
public abstract class ViewController<T> {

    protected final T frm;
    protected final UseCaseConstants useCase;

    public ViewController(T frm) {
        this.frm = frm;
        useCase = (UseCaseConstants) MainCordinator.getInstance().getParam(MapParams.CURRENT_USE_CASE);
        addActionListeners();
    }

    public ViewController(T frm, UseCaseConstants useCase) {
        this.frm = frm;
        this.useCase = useCase;
        addActionListeners();
    }

    public void openForm() {
        try {
            if (frm instanceof JDialog) {
                ((JDialog) frm).setTitle(useCase != null ? useCase.getName() : "");
            } else {
                ((JFrame) frm).setTitle(useCase != null ? useCase.getName() : "");
            }
            prepareView();
            ((Window) frm).setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog((Component) frm, "Error in view initialization\n" + e.getMessage(), useCase.getName(), JOptionPane.ERROR_MESSAGE);
            ((Window) frm).dispose();
        }
    }

    protected abstract void addActionListeners();

    protected abstract void prepareView() throws Exception;

    protected abstract void validateForm() throws ValidationException;

    public void cancelForm() {
        int input = JOptionPane.showConfirmDialog((Component) frm, "Cancel " + useCase.getName().toLowerCase() + "?", "Select an Option...",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (input == JOptionPane.YES_OPTION) {
            if (frm instanceof JDialog) {
                ((JDialog) frm).dispose();
            } else {
                ((JFrame) frm).dispose();
            }
        }
       
    }

    protected boolean confirmSave() {
        int input = JOptionPane.showConfirmDialog((Component) frm, "Save " + useCase.getName().toLowerCase() + "?", "Confirm operation",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        // 0=yes, 1=no, 2=cancel
        return input == JOptionPane.YES_OPTION;
    }

    protected boolean confirmDelete() {
        int input = JOptionPane.showConfirmDialog((Component) frm, useCase.getName().substring(0,1) +
                useCase.getName().substring(1).toLowerCase()+"?", "Confirm operation",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        // 0=yes, 1=no, 2=cancel
        return input == JOptionPane.YES_OPTION;
    }
    
}
