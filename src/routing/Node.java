package src.routing;

import java.util.ArrayList;

/**
 * Created by Николай on 05/05/2015.
 */
enum NodeColor {WHITE,GRAY,BLACK}
public class Node {
    //public String name;
    public long id;
    public double d;
    public Node pred;
    public NodeColor color;
    ArrayList<Node> adjList;

    public Node(long id) {
        this.id = id;
        this.color = NodeColor.WHITE;
        adjList=new ArrayList<>();

    }



}
