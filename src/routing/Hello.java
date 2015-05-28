package src.routing;

import java.sql.*;
import src.*;
class Hello {
    private static Connection conn=ConnectionToRDBMS.getDBConnection("routing1","routing1");
    public static void main(String args[]) throws SQLException {
        //System.out.println("Hello World!");
/*
        //not directed graph example
        Node s=new Node("s");
        Graph g=new Graph(s);
        Node r=new Node("r");
        g.addNode(r);
        Node v=new Node("v");
        g.addNode(v);
        Node w=new Node("w");
        g.addNode(w);
        Node t=new Node("t");
        g.addNode(t);
        Node x=new Node("x");
        g.addNode(x);
        Node u=new Node("u");
        g.addNode(u);
        Node y=new Node("y");
        g.addNode(y);

        g.addLink(v,r);
        g.addAdjNodes(r,new Node[]{v,s});
        g.addAdjNodes(s,new Node[]{r,w});
        g.addAdjNodes(w,new Node[]{s,t,x});
        g.addAdjNodes(t,new Node[]{w,x,u});
        g.addAdjNodes(u,new Node[]{t,x,y});
        g.addAdjNodes(x,new Node[]{w,t,u,y});
        g.addAdjNodes(y,new Node[]{x,u});

        //g.display();
        ShortestMethod.BFS(g,x,x);
        */
        /*
        //directed graph example
        Node one=new Node("1");
        Graph g=new Graph(one);
        Node two=new Node("2");
        Node three=new Node("3");
        Node four=new Node("4");
        Node five=new Node("5");
        Node six=new Node("6");
        g.addNodes(new Node[]{two,three,four,five,six});

        g.addAdjNodes(one,new Node[]{two, four});
        g.addLink(two,five);
        g.addAdjNodes(three,new Node[]{five,six});
        g.addLink(four, two);
        g.addLink(five, four);
        g.addLink(six,six);

        //g.display();
        ShortestMethod.BFS(g,three,four);*/
        /*
        //belman-ford example
        Node s=new Node("s");
        Graph g=new Graph(s);
        Node t=new Node("t");
        g.addNode(t);
        Node x=new Node("x");
        g.addNode(x);
        Node z=new Node("z");
        g.addNode(z);
        Node y=new Node("y");
        g.addNode(y);

        g.addLink(s,t,6);
        g.addLink(s,y,7);

        g.addLink(t,x,5);
        g.addLink(t,y,8);
        g.addLink(t,z,-4);

        g.addLink(x,t,-2);

        g.addLink(z,x,7);
        g.addLink(z,s,2);

        g.addLink(y,x,-3);
        g.addLink(y,z,9);

        ShortestMethod.BelmanFord(g,s,s);
        g.display();
        */
        //dijkstara exampl from Wikipedia
        /*Node one=new Node(1);
        Graph g=new Graph(one);
        Node two=new Node(2);
        Node three=new Node(3);
        Node four=new Node(4);
        Node five=new Node(5);
        Node six=new Node(6);
        g.addNodes(new Node[]{two,three,four,five,six});

        g.addSymmetricLink(one,six,14);
        g.addSymmetricLink(one,three,9);
        g.addSymmetricLink(one ,two,7);
        g.addSymmetricLink(six,three,2);
        g.addSymmetricLink(three,two,10);
        g.addSymmetricLink(six,five,9);
        g.addSymmetricLink(three,four,11);
        g.addSymmetricLink(two,four,15);
        g.addSymmetricLink(five,four,6);

        ShortestMethod.dijkstra(g,one,five);*/
        
        
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Graph G=new Graph();
        Statement stm=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet rs=stm.executeQuery("select point_id from vertex");
        
        while(rs.next()){
            G.addNode(new Node(rs.getLong(1)));
        }
        stm.close();
        //System.out.print(G.getNodes().size());
        stm=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs=stm.executeQuery("select * from edge");
        Node begin;
        Node end;
        while(rs.next()){
            begin=G.getNodeByID(rs.getLong("s"));
            end=G.getNodeByID(rs.getLong("d"));
            //System.out.println("begin="+begin.id+" end="+end.id);
            if(rs.getInt("type")==2)
                G.addSymmetricLink(begin, end, rs.getDouble("distance"));
            else 
                G.addLink(begin, end, rs.getDouble("distance"));
        }
        stm.close();
        System.out.println(G.getEdges().size()+"  "+G.getNodes().size());
        //G.display();
        ShortestMethod.dijkstra(G,G.getNodeByID(26322885),G.getNodeByID(26228721));
    }
}