/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 * @author Николай
 */
public class Point {
    private long  id;
    private double lat;
    private double lon;
    private boolean junction;
    private String juctionType;
    private String barrier;
    private String house;
    private String street;
    private String city;
    private String country;

    public Point(long id) {
        this.id = id;
    }

    
    public Point(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.city="Київ місто";
        this.country="Україна";
    }

    public void setJunction(boolean junction) {
        this.junction = junction;
    }

    public void setJuctionType(String juctionType) {
        this.juctionType = juctionType;
    }

    public void setBarrier(String barrier) {
        this.barrier = barrier;
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

    public long getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getHouse() {
        return house;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    
    @Override
    public String toString() {
        return "point id: "+id+ "  lat: "+lat+ "  lon: "+lon+"  "+country+", "+city+", "+street+", "+house;
    }
    
    
    
}
