/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gui.*;
/**
 *
 * @author Николай
 */
public class LogInController implements ActionListener {
    private String[] args;

    public LogInController() {
        args=new String[3];   
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("login"))
            InputRouteUI.main(args);
    }
    
}
