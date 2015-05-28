/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 *
 * @author Николай
 */
public class DOMParserDemo {
    private ArrayList<Point> points=new ArrayList<>();
    private ArrayList<Way> ways=new ArrayList<>();
    private ArrayList<Relation> rel=new ArrayList<>();
    static private Connection conn;
    
    public static void main (String[] args) throws ParserConfigurationException, SQLException{
        conn=ConnectionToRDBMS.getDBConnection("routing1","routing1");
        DOMParserDemo parser = new DOMParserDemo();
        parser.readXml() ;
        //System.out.println(Long.MAX_VALUE);
   } 
 public void readXml () throws ParserConfigurationException, SQLException{
    /*File fXmlFile = new File("D:/formdata.xml");
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = null;
    try {
        doc = dBuilder.parse(fXmlFile);
    } catch (SAXException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    doc.getDocumentElement().normalize();

    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

    NodeList nList = doc.getElementsByTagName("record");

    System.out.println("----------------------------");

    for (int temp = 0; temp < nList.getLength(); temp++) {

        Node nNode = nList.item(temp);

        System.out.println("\nCurrent Element :" + nNode.getNodeName());

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

            Element eElement = (Element) nNode;//eElement.

            System.out.println("nd : " + eElement.getElementsByTagName("nd").item(0).getAttributes().getNamedItem("be"));
            System.out.println("tag bla: "+eElement.getAttribute("bla")+" "+"tag ref: "+eElement.getAttribute("ref"));
            System.out.println("ID : " + eElement.getElementsByTagName("ID").item(0).getTextContent());
            System.out.println("Item No : " + eElement.getElementsByTagName("item_no").item(0).getTextContent());
            System.out.println("Description : " + eElement.getElementsByTagName("description").item(0).getTextContent());
            System.out.println("price : " + eElement.getElementsByTagName("price").item(0).getTextContent());
            System.out.println("base qty : " + eElement.getElementsByTagName("base_qty").item(0).getTextContent());
            System.out.println("Var qty : " + eElement.getElementsByTagName("var_qty").item(0).getTextContent());
            System.out.println("Base price : " + eElement.getElementsByTagName("base_price_").item(0).getTextContent()); 
            System.out.println("nd : " + eElement.getElementsByTagName("nd").item(0).getAttributes().getNamedItem("be2"));

        }
    }
            */
    File fXmlFile = new File("D:/podol.xml");
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = null;
    try {
        doc = dBuilder.parse(fXmlFile);
    } catch (SAXException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    
     doc.getDocumentElement().normalize();
     System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
    
     NodeList nList;
    /*nList = doc.getElementsByTagName("node");
    for (int i = 0; i < nList.getLength(); i++) {

        Node nNode = nList.item(i);
        Element eElement = (Element) nNode;
        long id=Long.parseLong(eElement.getAttribute("id"));
        double lat=Double.parseDouble(eElement.getAttribute("lat"));
        double lon=Double.parseDouble(eElement.getAttribute("lon"));
        
        //points.add(new Point(id,lat,lon));
        Statement stm=conn.createStatement();
        String sql="insert into point values("+id+","+lat+","+lon+")";
        //System.out.println(sql);
        stm.execute(sql);
        stm.close();
        //System.out.println("\nCurrent Element :" + nNode.getNodeName());


    }
    System.out.println(points.size());
    for(Point p:points)
        System.out.println(p);
    */
    Way curWay;
    nList = doc.getElementsByTagName("way");
    for (int i = 0; i < nList.getLength(); i++) {

        Node nNode = nList.item(i);
        Element wayElement = (Element) nNode;
        long id=Long.parseLong(wayElement.getAttribute("id"));
        curWay=new Way(id);
        NodeList ndRef=wayElement.getElementsByTagName("nd");
        for(int j=0;j<ndRef.getLength();j++){
            Element ndElement =(Element) ndRef.item(j);
            String refValue=ndElement.getAttributes().getNamedItem("ref").toString().split("\"")[1];
            curWay.addPoint(new Point(Long.parseLong(refValue)));
        }
        
        NodeList tag=wayElement.getElementsByTagName("tag");
        boolean flagCarWay=true;
        for(int j=0;j<tag.getLength();j++){
            Element ndElement =(Element) tag.item(j);
            String k=ndElement.getAttributes().getNamedItem("k").toString().split("\"")[1];
            String v=ndElement.getAttributes().getNamedItem("v").toString().split("\"")[1];
            if(k.equals("addr:housenumber"))
                curWay.setHouse(v);
            if(k.equals("addr:street"))
                curWay.setStreet(v);
            if(k.equals("addr:city"))
                curWay.setCity(v);
            if(k.equals("addr:city"))
                curWay.setCity(v);
            if(k.equals("highway")){
                if(v.equals("footway") || v.equals("cycleway")|| v.equals("bridleway")|| v.equals("steps")|| v.equals("path")){
                    flagCarWay=false;
                    break;
                }
                else
                    curWay.setType("road");
            }
            if(k.equals("oneway") && v.equals("yes"))   
                curWay.setGoType(1);
            if(k.equals("building"))
                curWay.setType("house");
    
        }
        //curWay.setType(null);
        if(flagCarWay){
            //ways.add(curWay);
            Statement stm=conn.createStatement();
            String sql="insert into way values("+curWay.getId()+","+curWay.getCountPoint()+",'"+curWay.getType()+"')";
            //System.out.println(sql);
            
                stm.execute(sql);
           
            //conn.commit();
            for(int j=0;j<curWay.getCountPoint();j++){
                sql="insert into way_structure values("+curWay.get(j).getId()+","+curWay.getId()+","+(j+1)+","+curWay.getGoType()+")";
                try{
                    stm.execute(sql);
                }
            catch(SQLIntegrityConstraintViolationException e){
                //System.out.println(sql);
                //return;
            }
            }
            stm.close();
        }
    }
    /*
    Relation curRel;
    nList = doc.getElementsByTagName("relation");
    for (int i = 0; i < nList.getLength(); i++) {

        Node nNode = nList.item(i);
        Element wayElement = (Element) nNode;
        long id=Long.parseLong(wayElement.getAttribute("id"));
        curRel=new Relation(id);
        NodeList members=wayElement.getElementsByTagName("member");
        for(int j=0;j<members.getLength();j++){
            Element ndElement =(Element) members.item(j);
            String type=ndElement.getAttributes().getNamedItem("type").toString().split("\"")[1];
            if(type.equals("way")){
                String wayId=ndElement.getAttributes().getNamedItem("ref").toString().split("\"")[1];
                Way tmp=getWayById(Long.parseLong(wayId));
                if(tmp!=null)
                    curRel.addWay(getWayById(Long.parseLong(wayId)));
            }
        }
        
        NodeList tag=wayElement.getElementsByTagName("tag");
            String name=null;
            String type=null;
        for(int j=0;j<tag.getLength();j++){
            Element ndElement =(Element) tag.item(j);
            String k=ndElement.getAttributes().getNamedItem("k").toString().split("\"")[1];
            String v=ndElement.getAttributes().getNamedItem("v").toString().split("\"")[1];
            if(k.equals("name"))
                name=v;
            if(k.equals("type"))
                type=v;
        }
        if(type.equals("associatedStreet"))
            curRel.setStreet(name);

        rel.add(curRel);
    }
    for(Relation r:rel)
        if(r.getCountMember()>0)
            r.completeWaysInfo();
        
        System.out.println(ways.size());
        for(Way w:ways)
            System.out.println(w);
        for(Point p:points)
            System.out.println(p);*/
 }
 
private Point getPointById(long id){
    for(Point p:points)
        if(p.getId()==id)
            return p;
    return null;
}

private Way getWayById(long id){
    for(Way w:ways)
        if(w.getId()==id)
            return w;
    //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    return null;
}

}

