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
public class Way {
    private ArrayList<Point> way;
    private long id;
    private String type;
    private String name;
    private String house;
    private String street;
    private String city;
    private String country;
    private int goType=2;
    
    public Way(long id) {
        this.id = id;
        way=new ArrayList<>();
    }
    
    public void completePointInfo(){
        for(Point p: way){
            if(street!=null && p.getStreet()==null)
                p.setStreet(street);
            if(house!=null && p.getHouse()==null)
                p.setHouse(house);
        }
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGoType() {
        return goType;
    }

    public void setGoType(int goType) {
        this.goType = goType;
    }

    
    
    public Point get(int index){
        return way.get(index);
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getType() {
        return type;
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
    
    public void addPoint(Point p){
        way.add(p);
    }
    
    public Long getId(){
        return id;
    }
    
    public int getCountPoint(){
        return way.size();
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }
    
    
    @Override
    public String toString() {
        return "way id: "+id+ "  "+country+", "+city+", "+street+", "+house;
    }
}
