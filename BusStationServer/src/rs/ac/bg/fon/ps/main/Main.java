/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.main;

import rs.ac.bg.fon.ps.thread.ServerThread;
import rs.ac.bg.fon.ps.view.controller.MainController;

/**
 *
 * @author Vladimir Jecić
 */
public class Main {
    public static void main(String[] args) {
        MainController.getInstance().startApp();
    }
}
