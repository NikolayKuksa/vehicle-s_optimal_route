package src.routing;

import java.util.Comparator;

/**
 * Created by Николай on 07/05/2015.
 */
public class VertexComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        if(o1.d<o2.d)
            return -1;
        if(o1.d>o2.d)
            return 1;
        return 0;
    }
}
