/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.ArrayList;

/**
 *
 * @author Николай
 */
public class Relation {
    private ArrayList<Way> ways;
    private long id;
    private String type;
    private String name;
    private String house;
    private String street;
    private String city;
    private String country;

    public Relation(long id) {
        this.id = id;
        ways=new ArrayList<>();
        
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public void completeWaysInfo(){
        for(Way w:ways){
            //w.setHouse(house);
            if(street!=null && w.getStreet()==null)
                w.setStreet(street);
            w.completePointInfo();
        }
    }
    
    public void addWay(Way w){
        ways.add(w);
    }
    
    public int getCountMember(){
        return ways.size();
    }
    
    
}
