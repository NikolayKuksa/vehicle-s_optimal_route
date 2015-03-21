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
        if(e.getActionCommand().equals("SignIn"));
        try {
            signIn();
        } catch (SQLException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        String less3="не менше 3 знаків";
        String ok="OK";
        int count=txt.getText().length();
        String name=e.getComponent().getName();
        switch(name){
                case "txtLoginAutorization":
                    if(count<3)
                        form.setLoginStatusAutorization(less3,messERROR);
                    else
                        form.setLoginStatusAutorization(ok,messOK);
                    break;
                case "txtPasswordAutorization":
                    if(count<3)
                        form.setPassworStatusdAutorization(less3,messERROR);
                    else
                        form.setPassworStatusdAutorization(ok,messOK);
                    break;
                case "txtLoginSignIn":
                    if(count<3)
                        form.setLoginStatusSignIn(less3, messERROR);
                    else 
                        form.setLoginStatusSignIn(ok, messOK);
                    break;
                case "txtPasswordSignIn":
                    if(count<3)
                        form.setPasswordStatusSignIn(less3, messERROR);
                    else 
                        form.setPasswordStatusSignIn(ok, messOK);
                        if(!form.getConfirmPassowrd().equals(""))
                            if(!form.getPasswordSignIn().equals(form.getConfirmPassowrd()))    
                                form.setConfirmStatusSignIn("паролі не співпадають", messERROR);
                            else
                                form.setConfirmStatusSignIn("",messOK);
                        else
                            form.setConfirmStatusSignIn(ok,messOK);
                    break;
                case "txtConfirmPasswordSignIn":
                    if(count<3)
                        form.setConfirmStatusSignIn(less3, messERROR);
                    else 
                        if(form.getPasswordSignIn().equals(form.getConfirmPassowrd()))
                            form.setConfirmStatusSignIn(ok, messOK);
                        else 
                            form.setConfirmStatusSignIn("паролі не співпадають", messERROR);
                    break;
                case "ftxDriverLicense":
                    if(form.checkDriverLicense())
                        form.setDriverLicenseStatus(ok, messOK);
                    else 
                        form.setDriverLicenseStatus("некоректне значення", messERROR);           
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
                    showMessageDialog(form,"Користувач з заданим логіном та паролем не зареєстрований в системі!"
                            + "\n Перевірте введені дані або зареєструйтесь в системі.","Пошук оптимального маршрту. "
                                    + "Авторизація користувача - Помилкові дані",ERROR_MESSAGE);
                    break;
                case  0: //if typical user
                    showMessageDialog(form, "Авторизація завершена успішно!\nДякуємо, що обрали нашу систему!", "Вітання",PLAIN_MESSAGE);
                    form.hide();
                    InputRouteUI.main(args);
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
            showMessageDialog(form,"Всі поля для вводу є обов'яковими для заповнення","Помилка",ERROR_MESSAGE);
        }
        //InputRouteUI.main(args);
    }

    private void signIn() throws SQLException {
        if(form.checkSignInFields()){
            Connection con=ConnectionToRDBMS.getDBConnection("MYKOLA_KUKSA", "MYKOLA_KUKSA");
            CallableStatement stm=con.prepareCall("{? = call ADD_USER(?,?,?)}");
            stm.registerOutParameter (1, Types.INTEGER);
            stm.setString(2,form.getLoginSignIn());
            stm.setString(3, form.getPasswordSignIn());
            stm.setString(4,form.getDriverLicense());
            stm.execute();
            int result=stm.getInt(1);
            stm.close();
            System.out.println(result);
            switch(result){
                case -1: //if user login and password already exist
                    showMessageDialog(null,"Нажаль, введені логін та пароль вже зайняті!"
                            + "\n Введіть інші значення","Пошук оптимального маршрту. Реєстрація в системі - Перевірка логіну та паролю",ERROR_MESSAGE);
                    break;
                case  0: //if user does'n exist
                    showMessageDialog(null, "Реєстрація успішно завершена!\nДякуємо, що обрали нашу систему!", "Вітання",PLAIN_MESSAGE);
                    form.gotoLogInWindow();
                    break;
                case  1: //if driver license already exist
                     showMessageDialog(null,"Водійське посвідчення з даною серією та номером вже зареєстроване в системі!"
                            + "\nПеревітре правильність серії та номеру посвідчення",
                             "Пошук оптимального маршрту. Реєстрація в системі - Перевірка водійського посвідчення",ERROR_MESSAGE);
            }
        }
        else{
            showMessageDialog(form,"Всі поля для вводу є обов'яковими для заповнення","Помилка",ERROR_MESSAGE);
        }
    }
    
}
