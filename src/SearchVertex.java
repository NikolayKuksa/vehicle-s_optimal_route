/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Николай
 */
public class SearchVertex {
    private Connection conn=ConnectionToRDBMS.getDBConnection("routing1", "routing1");
          
    public static void main(String [] argv) throws SQLException{
        SearchVertex v=new SearchVertex();
        //v.fillVertex();
        v.fillEdges();
    }
    
    private void fillVertex() throws SQLException{
        Statement stm=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet rs=stm.executeQuery("select * from way_point");
        
        long point_id;
        while (rs.next()) {
            point_id=rs.getLong("point_id");
            Statement stm2=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs2=stm2.executeQuery("select count(way_id) from way_point where point_id="+point_id);
            if(rs2.next())
                if(rs2.getInt(1)>1){
                    Statement stm3=conn.createStatement();
                    try{
                        stm3.executeQuery("insert into vertex values("+point_id+")");
                    }
                    catch(SQLIntegrityConstraintViolationException e){}
                    stm3.close();
                }
            stm2.close();
        }
        stm.close();
        stm=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs=stm.executeQuery("select point_id from way_point where count_point=order_position or order_position=1");
        while(rs.next()){
            Statement stm2=conn.createStatement();
            try{
                stm2.executeQuery("insert into vertex values("+rs.getLong("point_id")+")");
            }
            catch(SQLIntegrityConstraintViolationException e){}
            stm2.close();
        }
        stm.close();
    }
    /*
    private void fill() throws SQLException{
        Statement stm=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet rs=stm.executeQuery("select way_id from way_point group by way_id");
        
        long way_id;
        while (rs.next()) {
            way_id=rs.getLong("way_id");
            Statement stm2=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs2=stm2.executeQuery("select order_position,point_id,lat,lon,go_type from way_point where way_id="+way_id+" order by 1");

            ArrayList<Point> points=new ArrayList<>();
            int go_type=0;
            while(rs2.next()){
                if(go_type==0)
                    go_type=rs2.getInt("go_type");
                points.add(new Point(rs2.getLong("point_id"),rs2.getDouble("lat"),rs2.getDouble("lon"))); 
            } 
            Statement stm3=conn.createStatement();
            long begin=points.get(0).getId();
            long end=points.get(points.size()-1).getId();
            try{
                stm3.executeQuery("insert into edge values("+begin+","+end+","+go_type+","+distance(points)+")");
            }
            catch(SQLIntegrityConstraintViolationException e){}
            stm3.close();
            stm3=conn.createStatement();
            try{
                stm3.executeQuery("insert into vertex values("+begin+")");
            }
            catch(SQLIntegrityConstraintViolationException e){}
            stm3.close();
            stm3=conn.createStatement();
            try{
                stm3.executeQuery("insert into vertex values("+end+")");   
            }
            catch(SQLIntegrityConstraintViolationException e){}
            stm3.close();
            stm2.close();
        }
        stm.close();
    }*/
    
    private void fillEdges() throws SQLException{
        Statement stm=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet rs=stm.executeQuery("select * from vertex");
        
        long v_id;         
        while(rs.next()){
            v_id=rs.getLong(1);
            Statement stm2=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet waysWithVertex=stm2.executeQuery("select way_id,order_position,go_type from way_point where point_id="+v_id);
            while(waysWithVertex.next()){
                long way_id=waysWithVertex.getLong("way_id");
                int go_type=waysWithVertex.getInt("go_type");
                
                //selecting all vertex which belong to way with this way_id
                Statement stm3=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet allVertexInWay=stm3.executeQuery("select point_id,lat,lon,order_position from way_point where point_id in " +
                                                        "(select point_id from vertex where point_id in \n" +
                                                         "  (select point_id from way_structure where way_id="+way_id+"))" +
                                                            " and way_id="+way_id);
                ArrayList<Point> vertexOnWay=new ArrayList(); //for vertex
                ArrayList<Integer> vertexPosition=new ArrayList();//for order position
                while(allVertexInWay.next()){
                    vertexOnWay.add(new Point(allVertexInWay.getLong(1),allVertexInWay.getDouble(2),allVertexInWay.getDouble(3)));
                    vertexPosition.add(allVertexInWay.getInt("order_position"));
                }
                stm3.close();
                //search cur vertex in vertexOnWay
                int index=0;
                for(int i=0;i<vertexOnWay.size();i++)
                    if(vertexOnWay.get(i).getId()==v_id){
                        index=i;
                        break;
                    }
                /*if(index==vertexOnWay.size()-1){//if vertex is last in way
                    stm3.close();
                    stm2.close();
                }*/
                if(index!=vertexOnWay.size()-1){
                    //search adjecent vertex
                    long nextVertexId=vertexOnWay.get(index+1).getId();
                    int curVertexPosition=vertexPosition.get(index);
                    ArrayList<Point> points=new ArrayList();
                    Statement stmVertexToNext=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    ResultSet curVertexToEndWay=stmVertexToNext.executeQuery("select point_id,lat,lon from way_point where way_id="+way_id+" and order_position>="+curVertexPosition+" order by order_position");
                    while(curVertexToEndWay.next()){
                        long id=curVertexToEndWay.getLong(1);
                        points.add(new Point(id,curVertexToEndWay.getDouble(2),curVertexToEndWay.getDouble(3)));
                        if(id==nextVertexId)
                            break;
                    }
                    stmVertexToNext.close();
                    Statement insertEdge=conn.createStatement();
                    try{
                        insertEdge.executeQuery("insert into edge values("+points.get(0).getId()+","+points.get(points.size()-1).getId()+","+go_type+","+distance(points)+")");
                    }
                    catch(SQLIntegrityConstraintViolationException e){}
                    insertEdge.close();
                }   
            }    
            stm2.close(); 
        }
        stm.close();
        
    }
    
    private double distance(Point p1,Point p2){
        final double LAT=40000/360;
        final double LON=Math.cos(Math.PI*p2.getLat()/180)*40000/360;
        double delta1=Math.abs(p1.getLat()-p2.getLat())*LAT;
        double delta2=Math.abs(p1.getLon()-p2.getLon());
        return Math.sqrt(delta1*delta1+delta2*delta2);
    }
    
    private double distance(ArrayList<Point> p){
        double result=0;
        for(int i=0;i<p.size()-1;i++)
            result+=distance(p.get(i),p.get(i+1));
        return result;
    }
}
