package src.routing;

import java.util.ArrayList;

/**
 * Created by Николай on 04/05/2015.
 */
//adjacency-list representation
//adjacency-matrix representation
public class Graph {
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    public Graph(){
        nodes=new ArrayList<>();
        edges=new ArrayList<>();
    }
    public Graph(Node node){
        nodes=new ArrayList<>();
        edges=new ArrayList<>();
        nodes.add(node);
    }
    public void display(){
        for (int i = 0; i < nodes.size(); i++) {
            Node temp=nodes.get(i);
            System.out.print(temp.id + ": ");
            for (int j = 0; j <temp.adjList.size(); j++) {
                if(j==temp.adjList.size()-1) { //if we print last node of list
                    System.out.print(temp.adjList.get(temp.adjList.size() - 1).id);
                    break;
                }
                System.out.print(temp.adjList.get(j).id + ", ");
            }
            System.out.println();
        }
    }

    public void addNode(Node node){
        nodes.add(node);
    }
    public void addNodes(Node[] nodeList){
        for (int i = 0; i <nodeList.length ; i++) {
            nodes.add(nodeList[i]);
        }
    }
    public void addLink(Node a,Node b){
        a.adjList.add(b);
        edges.add(new Edge(a,b,1));
    }
    public void addLink(Node a,Node b,double w){
        a.adjList.add(b);
        edges.add(new Edge(a,b,w));
    }
    public void addSymmetricLink(Node a,Node b, double w){
        addLink(a,b,w);
        addLink(b,a,w);
    }
    public void addAdjNodes(Node source, Node [] list){
        for (int i = 0; i <list.length ; i++) {
            //source.adjList.add(list[i]);
            addLink(source,list[i]);
        }
    }
    public void addAdjNodes(Node source, Node [] list,double [] weights){
        for (int i = 0; i <list.length ; i++) {
            addLink(source,list[i],weights[i]);
        }
    }
    public Node get(int i){
        return nodes.get(i);
    }
    public int size(){
        return nodes.size();
    }
    public double w(Node a,Node b){
        for(Edge e: edges)
            if(a.equals(e.begin) && b.equals(e.end))
                return e.weight;
        return 0;
    }
    
    public Node getNodeByID(long id){
        for(Node n:nodes)
            if(n.id==id)
                return n;
        System.out.println("fail  "+id+"  ");
        return null;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

}
class Edge {
    public Node begin;
    public Node end;
    public double weight;

    public Edge(Node a, Node b, double weight) {
        begin=a;
        end=b;
        this.weight=weight;
    }

}
