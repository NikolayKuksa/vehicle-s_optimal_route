/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src;
import controllers.*;
import gui.*;
/**
 *
 * @author Николай
 */
public class Start {
    LogInController logIn;
    public Start() {
        logIn=new LogInController();
    }
    
    public static void main(String [] args){
        LogInUI.main(args);
    }
}
