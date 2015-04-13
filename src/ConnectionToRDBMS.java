/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src;

import java.sql.*;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author Николай
 */
public class ConnectionToRDBMS {
    private static Connection conn;
    public static Connection getDBuserConnection(){
            return getDBConnection("MYKOLA__KUKSA","MYKOLA__KUKSA");
    }
    
     public static Connection getDBConnection(String user,String password) {
        String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
        try {
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                if(conn==null)
                    conn=DriverManager.getConnection(url,user, password);
                return conn;
           } catch (SQLException se) {
               System.out.println("getDBConnection(): SQL Exception: " + se);
               showMessageDialog(null,"Неможливо встановити з'єднання з сервером. Сервер не відповідає","Пошук оптимального маршрту- Підключення до серверу."
                                    ,ERROR_MESSAGE);
               return null;
           }          
       }
     //jdbc:oracle:thin:@localhost:1521:ORCL
    /**
     * @param args
     * @throws SQLException 
     */
    public static void main(String[] args) throws SQLException {
        /*Statement stm3;
         stm3=con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
         ResultSet src=stm3.executeQuery("select driver.driver_license,car_name,count_hourse,car_number, driver.login,driver.password\n" +
            "from driver,car\n" +
            "where driver.driver_license=car.driver_license");
         //ResultSet src=stm3.executeQuery("select driver.driver_license from driver");
         src.next();
         src.updateString("car_name", "car1");
         src.updateRow();
        //System.out.println(conn.toString());*/
        getDBuserConnection();
        PreparedStatement stm; 
        ResultSet rs;
        stm=conn.prepareStatement("select geo_x from \"GEOGRAPHICAL POINT\" where geo_x>=?");//GROUP BY (street_name)
        stm.setInt(1, 2);
        /*stm.setString(1,"country1"); //
        stm.setString(2, "city1" );*/
        rs=stm.executeQuery();
        while(rs.next()){
            System.out.println(rs.getFloat(1));
        }
    }
 
}
