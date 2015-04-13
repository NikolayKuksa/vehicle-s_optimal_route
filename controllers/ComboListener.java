/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.InputRouteUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Николай
 */
public class ComboListener implements ActionListener{
    private final InputRouteUI form;

    public ComboListener(InputRouteUI form) {
        this.form = form;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            
            switch(e.getActionCommand()){
            case "comb1":
                form.updateCombo2();
                form.updateCombo3();
                form.updateCombo4();
                break;
            case "comb2":
                form.updateCombo3();
                form.updateCombo4();
                break;
            case "comb3":
                form.updateCombo4();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ComboListener.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
