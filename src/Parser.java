/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;
/**
 *
 * @author Николай
 */
public class Parser {
    public static void main(String [] args) throws JSONException{
        HttpQuery http=new HttpQuery();
        String response="";
        try {
            response = http.send("http://router.project-osrm.org/viaroute?loc=50.4423,30.5298&loc=50.4433,30.5151&z=0&instructions=true&geometry=false");
        } catch (Exception ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONObject obj=new JSONObject(response);
        JSONArray arr=obj.getJSONArray("route_instructions");
        JSONArray arr2;
        for (int i = 0; i < arr.length(); i++){
            arr2=arr.getJSONArray(i);
            String str = arr2.getString(0)+", "+arr2.getString(1)+", "+arr2.getInt(2)+", "+arr2.getInt(3)+", "+arr2.getInt(4);
            System.out.println(str);
            
            System.out.println();
        }
        System.out.println();
        System.out.println(response);
    }
}
