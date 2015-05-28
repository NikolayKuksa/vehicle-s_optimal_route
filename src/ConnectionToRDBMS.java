/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.*;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Николай
 */
public class ConnectionToRDBMS {
    private static Connection conn;
    public static Connection getDBuserConnection(){
        //return getDBConnection("MYKOLA__KUKSA","MYKOLA__KUKSA");
        return getDBConnection("UNIT_T","UNIT_T");
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
    public static void main(String[] args) throws SQLException, MalformedURLException, ProtocolException, IOException, ParserConfigurationException, SAXException {
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
        
        /*getDBuserConnection();
        Statement stm; 
        ResultSet rs;
        stm=conn.createStatement();
        rs=stm.executeQuery("select *from has");
        while(rs.next()){
            System.out.println(rs.getDate(1)+"||"+rs.getString(2));
        }
        */
    /*    
    String uri =
    "http://api.flurry.com/eventMetrics/Event?apiAccessCode=YHJBA13CSKTMS6XHTM6M&apiKey=6XQY729FDU1CR9FKXVZP&startDate=2011-2-28&endDate=2011-3-1&eventName=Tip%20Calculated";

    URL url = new URL(uri);
    HttpURLConnection connection =
        (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.setRequestProperty("Accept", "application/xml");

    InputStream xml = connection.getInputStream();
    System.out.println(xml);
    System.out.println("\n\n!!!!!!!!!!!!!!!!!!!!!!!!");
    /*DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = (Document) db.parse(xml);
            System.out.println(doc);*/
        
        URL xmlUrl = new URL("http://www.rekindle.co.za/rss.xml");
InputStream in = xmlUrl.openStream();
System.out.println();
    }
 
}
