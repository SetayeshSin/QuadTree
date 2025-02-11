package org.example.quadtree;

public class Node {
    public int[][] image;
    public int length;
    public boolean leaf;
    public int color=-1;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;
    public int level;
    public int treeDepth=0;
    public Node(int[][] image,int level){
        this.image=image;
        length=image.length;
        this.level=level;
    }
}
