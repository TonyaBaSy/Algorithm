package graph;

import java.util.List;

/**
 * Created by Tony on 2016/5/4.
 */
public class DeepFirst {
    public final int WHITE = 0;
    public final int GREY = 1;
    public final int BLACK = 2;
    private int time;
    private Node[] nodes;

    private class Node {
        private int id;
        private int color;  //0表示white，1表示grey，2表示black
        private Node father;
        private int start;  //0->1的时间戳
        private int end;    //1->2的时间戳

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public Node getFather() {
            return father;
        }

        public void setFather(Node father) {
            this.father = father;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
    public void deep(List<Node[]> graph) {
        time = 0;
        for (Node n : nodes)
            if (n.getColor() == WHITE)
                deepVisit(graph, n);
    }
    public void deepVisit(List<Node[]> graph, Node n) {
        time++;
        n.setStart(time);
        Node[] adj = graph.get(n.getId());
        for (Node v : adj) {
            if (v.getColor() == WHITE){
                v.father = n;
                deepVisit(graph, v);
            }
        }
        n.setColor(BLACK);
        time++;
        n.setEnd(time);
    }
}
