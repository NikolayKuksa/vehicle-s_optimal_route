/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gui.*;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_CANCEL_OPTION;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showOptionDialog;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import src.ConnectionToRDBMS;
/**
 *
 * @author Николай
 */
public class LogInController implements ActionListener, FocusListener {
    private String[] args;
    private LogInUI form;
    private Color messERROR=new Color(255,0,51);
    private Color messOK=new Color(0,198,58);
    
    public LogInController(LogInUI form) {
        this.form=form;
        args=new String[3];   
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        if(e.getActionCommand().equals("LogIn"))
            try {
                login();
            } catch (SQLException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(e.getActionCommand().equals("btnSignInAutorization"));
            //form.setLoginStatusAutorization("");
    }

    @Override
    public void focusGained(FocusEvent e) {
        
    }
    
    @Override
    public void focusLost(FocusEvent e) {
        //JTextField src = (JTextField) e.getSource();
        //System.out.println(src.getText());
        //src.setText("lolo");
        JTextComponent txt=(JTextComponent) e.getComponent();
        System.out.println(txt.getText());
        ///txt.setText("loooooool");
        int count=txt.getText().length();
        String name=e.getComponent().getName();
        switch(name){
                case "txtLoginAutorization":
                    if(count<3)
                        form.setLoginStatusAutorization("не менше 3 знаків",messERROR);
                    else
                        form.setLoginStatusAutorization("OK",messOK);
                    break;
                case "txtPasswordAutorization":
                    if(count<3)
                        form.setPassworStatusdAutorization("не менше 3 знаків",messERROR);
                    else
                        form.setPassworStatusdAutorization("OK",messOK);
            }  
    }

    private void login() throws SQLException {
        if(form.checkLogInFields()){
            Connection con=ConnectionToRDBMS.getDBConnection("MYKOLA_KUKSA", "MYKOLA_KUKSA");
            CallableStatement stm=con.prepareCall("{? = call CHECK_LOGIN_PASSWORD(?,?)}");
            stm.registerOutParameter (1, Types.INTEGER);
            stm.setString(2,form.getLoginAtoutization());
            stm.setString(3, form.getPasswordAtoutization());
            stm.execute();
            int result=stm.getInt(1);
            stm.close();
            System.out.println(result);
            switch(result){
                case -1: //if user don't exist
                    
                    break;
                case  0: //if typical user
                    break;
                case  1: //if user is admin
                    break;
            }
        }
        else{
            //showMessageDialog(null, "This language just gets better and better!");
            //showMessageDialog(null, "Thank you for using Java", "Yay, java",PLAIN_MESSAGE);
            /*Object[] options = {"Yes, please",
                    "No, thanks",
                    "No eggs, no ham!"};
            int n=showOptionDialog(null,"Would you like some green eggs to go with that ham?", "A Silly Question",
            YES_NO_CANCEL_OPTION,QUESTION_MESSAGE,null,options,options[2]);
            System.out.println(n);*/
            showMessageDialog(form,"Eggs are not supposed to be green.","Inane error",ERROR_MESSAGE);
        }
        //InputRouteUI.main(args);
    }
    
}
