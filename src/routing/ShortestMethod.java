package src.routing;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Николай on 05/05/2015.
 */

public class ShortestMethod {
    final static double maxValue=Double.MAX_VALUE;
    public static void BFS(Graph G,Node s, Node d){
        for (int i = 0; i < G.size(); i++) {
            Node temp=G.get(i);
            if(!temp.equals(s)){
                temp.color=NodeColor.WHITE;
                temp.d=maxValue;
                temp.pred=null;
            }
        }
        s.color=NodeColor.GRAY;
        s.d=0;
        s.pred=null;
        Queue<Node> queue=new LinkedList<>();
        queue.add(s);
        Node u;
        while (!queue.isEmpty()){
            u=queue.remove();
            for (Node v: u.adjList) {
                if(v.color==NodeColor.WHITE){
                    v.color=NodeColor.GRAY;
                    v.d=u.d+1;
                    v.pred=u;
                    queue.add(v);
                }
            }
            u.color=NodeColor.BLACK;
        }
        System.out.print("Path: " + printOptimalPath(d)+"  Length:"+d.d);


    }

    private static String printOptimalPath(Node d){
        return d.pred==null?(new Long(d.id)).toString():printOptimalPath(d.pred)+"->"+d.id;
    }
    private static void initializeSingleSource(Graph G, Node s){
        for(Node v:G.getNodes()){
            v.d=maxValue;
            v.pred=null;
        }
        s.d=0;
    }
    private static void relax(Graph G,Node u, Node v){
        double tmp=u.d+G.w(u,v);
        if(v.d>tmp){
            v.d=tmp;
            v.pred=u;
        }
    }

    private static void relax(Graph G,Node u, Node v, PriorityQueue<Node> queue){
        double tmp=u.d+G.w(u,v);
        if(v.d>tmp){
            queue.remove(v);
            v.d=tmp;
            v.pred=u;
            queue.add(v);
        }
    }

    public static void BelmanFord(Graph G,Node s,Node d){
        initializeSingleSource(G,s);
        for (int i = 0; i < G.size()-1; i++) {
            for(Edge e:G.getEdges())
                relax(G,e.begin,e.end);
        }
        System.out.print("Path: " + printOptimalPath(d)+"  Length:"+d.d);
    }

    public static void dijkstra(Graph G, Node s,Node d) {
        initializeSingleSource(G,s);
        PriorityQueue<Node> queue=new PriorityQueue<>(new VertexComparator());
        for(Node v : G.getNodes())
            queue.add(v);
        Node u;
        while (!queue.isEmpty()){
            u=queue.remove();
            //if u.equals(d) return
            for(Node v: u.adjList)
                relax(G,u,v,queue);
        }
        System.out.print("Path: " + printOptimalPath(d)+"  Weight:"+d.d);
    }
}
