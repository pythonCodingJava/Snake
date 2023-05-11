package Logic;

import java.awt.Point;

class node{
		
    int x;
    int y;
    double g=9999;
    double h=9999;
    double score=g+h;
    Point from = new Point(0,0);
    
    public node(int x,int y) {
        this.x = x;
        this.y = y;
        
        score = g+h;
    }
}
