/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src;

import java.sql.*;

/**
 *
 * @author Николай
 */
public class ConnectionToRDBMS {
    /*
    public static Connection getDBadminConnection(String users,String password) throws SQLException{
        return getDBConnection("admin","pass");
    }
     public static Connection getDBuserConnection(String users,String password) throws SQLException{
        return getDBConnection("MYKOLA_KUKSA","MYKOLA_KUKSA");
    }
    */
     public static Connection getDBConnection(String user,String password) throws SQLException {
        String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
        try {
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection conn = DriverManager.getConnection(url, user, password);
                 return conn;
           } catch (SQLException se) {
               System.out.println("getDBConnection(): SQL Exception: " + se);
               return null;
           }          
       }
     //jdbc:oracle:thin:@localhost:1521:ORCL
    /**
     * @param args
     * @throws SQLException 
     */
    public static void main(String[] args) throws SQLException {
        // TODO Auto-generated method stub
        Connection con = getDBConnection("MYKOLA_KUKSA","MYKOLA_KUKSA");
        
        /*
        CallableStatement stm2=con.prepareCall("{? = call TSTPRC2(?,?)}");
        stm2.registerOutParameter (1, Types.INTEGER);
        stm2.setInt (2, 20000);
        stm2.setInt(3,15);
        stm2.execute();
        int result=stm2.getInt(1);
        stm2.close();
        
        System.out.println(con.getSchema());
        System.out.println(result);
                */
        /*
        Statement stm=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet rs=stm.executeQuery("select login,password from account");
        rs.last();rs.absolute(5);
         //while (rs.previous()) {
             //System.out.println("");
             System.out.println(rs.getString("login")+"  "+rs.getString("password"));
         //}
         stm.close();
        */
         
         Statement stm3;
         stm3=con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
         ResultSet src=stm3.executeQuery("select driver.driver_license,car_name,count_hourse,car_number, driver.login,driver.password\n" +
            "from driver,car\n" +
            "where driver.driver_license=car.driver_license");
         //ResultSet src=stm3.executeQuery("select driver.driver_license from driver");
         src.next();
         src.updateString("car_name", "car1");
         src.updateRow();
        //System.out.println(conn.toString());
        
    }
 
}
