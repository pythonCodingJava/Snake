package Logic;

import java.awt.Point;
import java.util.ArrayList;

public class AI {
    static boolean warp = false;
    public static ArrayList<Point> getPath(int[][] b, ArrayList<block> s, int foodx, int foody, boolean thick){
        ArrayList<Point> p = new ArrayList<Point>();
        
        Point start = new Point(s.get(0).x, s.get(0).y);
        Point end = new Point(foodx, foody);
        ArrayList<node> walls = new ArrayList<node>();
        node[][] bClone = new node[b.length][b[0].length];
        int[][] off = {{1,0},{-1,0},{0,-1},{0,1}};
        for(int i = 0; i<b.length; i++) {
            for(int j = 0; j<b[0].length; j++) {
                bClone[i][j] = new node(j,i);
                if(b[i][j]==1 && !(j == start.x && i == start.y)) {
                    walls.add(bClone[i][j]);
                    if(thick){
                        for(int[] o : off){
                            int x = j + o[0];
                            int y = i + o[1];
                            if((x >= 0 && x<bClone[0].length) && (y >= 0 && y<bClone.length)){
                                if(!(x == start.x && y == start.y) && !walls.contains(bClone[y][x])){
                                    walls.add(bClone[y][x]);
                                }
                            }
                        }
                    }
                }
            }
        }
        bClone[start.y][start.x].g = 0;
        bClone[end.y][end.x].h = 0;
        
        ArrayList<node> openSet = new ArrayList<node>();
        ArrayList<node> closedSet = new ArrayList<node>();
        node current = bClone[start.y][start.x];
        openSet.add(current);
        boolean running = true;
        while(running) {
            if(current == bClone[end.y][end.x]) {
                running = false;
                break;
            }else {
                ArrayList<node> nb = new ArrayList<node>();
                if(current.y<=bClone.length-2) {
                    nb.add(bClone[current.y+1][current.x]);
                }else if(warp){
                    nb.add(bClone[0][current.x]);
                }
                if(current.y>0) {
                    nb.add(bClone[current.y-1][current.x]);
                }else if(warp){
                    nb.add(bClone[bClone.length-1][current.x]);
                }
                if(current.x<=bClone[0].length-2) {
                    nb.add(bClone[current.y][current.x+1]);
                }else if(warp){
                    nb.add(bClone[current.y][0]);
                }
                if(current.x>0) {
                    nb.add(bClone[current.y][current.x-1]);
                }else if(warp){
                    nb.add(bClone[current.y][bClone[0].length-1]);
                }
                
                for(int i = nb.size()-1; i>=0; i--) {
                    if(closedSet.contains(nb.get(i)) || walls.contains(nb.get(i))) {
                        nb.remove(i);
                    }else {
                        nb.get(i).g = current.g+1;
                        double toAdd =  Point.distance(nb.get(i).x, nb.get(i).y, end.x, end.y);
                        if(warp) {
                            double[] toCompare = {
                                    Point.distance(nb.get(i).x, nb.get(i).y, end.x-b[0].length, end.y),
                                    Point.distance(nb.get(i).x, nb.get(i).y, end.x, end.y-b[0].length),
                            };
                            for(double tCm : toCompare) {
                                if(tCm<toAdd) {
                                    toAdd=tCm;
                                }
                            }
                        }
                        nb.get(i).h = toAdd;//Point.distance(nb.get(i).x, nb.get(i).y, end.x, end.y);
                        nb.get(i).score = nb.get(i).g + nb.get(i).h;
                        nb.get(i).from = new Point(current.x, current.y);
                        if(openSet.contains(nb.get(i))) {
                            if(openSet.get(openSet.indexOf(bClone[nb.get(i).y][nb.get(i).x])).score>nb.get(i).score) {
                                openSet.remove(bClone[nb.get(i).y][nb.get(i).x]);
                                openSet.add(nb.get(i));
                            }
                        }else {
                            openSet.add(nb.get(i));
                        }
                    }
                }
                
                ArrayList<node> mins = getSameNodes(openSet, getMinNode(openSet));
                node min = mins.get(0);
                for(node n : mins) {
                    if(n.h<min.h) {
                        min = n;
                    }
                }
                
                openSet.remove(min);
                closedSet.add(min);
                current = min;
            }
        }
        
        boolean r2 = true;
        p.add(end);
        node tT = bClone[end.y][end.x];
        while(r2) {
            if(tT == bClone[start.y][start.x]) {
                r2 = false;
                break;
            }else {
                p.add(tT.from);
                tT = bClone[tT.from.y][tT.from.x];
            }
        }
        
        return p;
    }
    
    public static ArrayList<Point> reverseAstar(int[][] b, ArrayList<block> s, int foodx, int foody, boolean thick){
        ArrayList<Point> p = new ArrayList<Point>();
        
        Point start = new Point(s.get(0).x, s.get(0).y);
        Point end = new Point(foodx, foody);
        ArrayList<node> walls = new ArrayList<node>();
        node[][] bClone = new node[b.length][b[0].length];
        int[][] off = {{1,0},{-1,0},{0,1},{0,-1}};
        for(int i = 0; i<b.length; i++) {
            for(int j = 0; j<b[0].length; j++) {
                bClone[i][j] = new node(j,i);
                if(b[i][j]==1 && new Point(j,i)!=start) {
                    walls.add(bClone[i][j]);
                    if(thick){
                        for(int[] o : off){
                            int x = j + o[0];
                            int y = i + o[1];
                            if((x >= 0 && x<bClone[0].length) && (y >= 0 && y<bClone.length)){
                                if(!(x == start.x && y == start.y) && !walls.contains(bClone[y][x])){
                                    walls.add(bClone[y][x]);
                                }
                            }
                        }
                    }
                }
            }
        }
        bClone[start.y][start.x].g = 0;
        bClone[end.y][end.x].h = 0;
        
        ArrayList<node> openSet = new ArrayList<node>();
        ArrayList<node> closedSet = new ArrayList<node>();
        node current = bClone[start.y][start.x];
        openSet.add(current);
        boolean running = true;
        while(running) {
            if(current == bClone[end.y][end.x]) {
                running = false;
                break;
            }else {
                ArrayList<node> nb = new ArrayList<node>();
                if(current.y<=bClone.length-2) {
                    nb.add(bClone[current.y+1][current.x]);
                }else if(warp){
                    nb.add(bClone[0][current.x]);
                }
                if(current.y>0) {
                    nb.add(bClone[current.y-1][current.x]);
                }else if(warp){
                    nb.add(bClone[bClone.length-1][current.x]);
                }
                if(current.x<=bClone[0].length-2) {
                    nb.add(bClone[current.y][current.x+1]);
                }else if(warp){
                    nb.add(bClone[current.y][0]);
                }
                if(current.x>0) {
                    nb.add(bClone[current.y][current.x-1]);
                }else if(warp){
                    nb.add(bClone[current.y][bClone[0].length-1]);
                }
                
                for(int i = nb.size()-1; i>=0; i--) {
                    if(closedSet.contains(nb.get(i)) || walls.contains(nb.get(i))) {
                        nb.remove(i);
                    }else {
                        nb.get(i).g = current.g+1;
                        double toAdd =  Point.distance(nb.get(i).x, nb.get(i).y, end.x, end.y);
                        if(warp) {
                        double[] toCompare = {
                                Point.distance(nb.get(i).x, nb.get(i).y, end.x-b[0].length, end.y),
                                Point.distance(nb.get(i).x, nb.get(i).y, end.x, end.y-b[0].length),
                        };
                        for(double tCm : toCompare) {
                            if(tCm>toAdd) {
                                toAdd=tCm;
                            }
                        }
                        }
                        nb.get(i).h = toAdd;//Point.distance(nb.get(i).x, nb.get(i).y, end.x, end.y);
                        nb.get(i).score = nb.get(i).g + nb.get(i).h;
                        nb.get(i).from = new Point(current.x, current.y);
                        if(openSet.contains(nb.get(i))) {
                            if(openSet.get(openSet.indexOf(bClone[nb.get(i).y][nb.get(i).x])).score<nb.get(i).score) {
                                openSet.remove(bClone[nb.get(i).y][nb.get(i).x]);
                                openSet.add(nb.get(i));
                            }
                        }else {
                            openSet.add(nb.get(i));
                        }
                    }
                }
                
                ArrayList<node> mins = getSameNodes(openSet, getMaxNode(openSet));
                node min = mins.get(0);
                for(node n : mins) {
                    if(n.h>min.h) {
                        min = n;
                    }
                }
                
                openSet.remove(min);
                closedSet.add(min);
                current = min;
            }
        }
        
        boolean r2 = true;
        p.add(end);
        node tT = bClone[end.y][end.x];
        while(r2) {
            if(tT == bClone[start.y][start.x]) {
                r2 = false;
                break;
            }else {
                p.add(tT.from);
                tT = bClone[tT.from.y][tT.from.x];
            }
        }
        
        return p;
    }

    private static node getMinNode(ArrayList<node> n) {
        node  minNode = n.get(0);
        for(node ns : n) {
            if(ns.score<minNode.score) {
                minNode=ns;
            }
        }
        return minNode;
    }

    private static node getMaxNode(ArrayList<node> n) {
        node  minNode = n.get(0);
        for(node ns : n) {
            if(ns.score>minNode.score) {
                minNode=ns;
            }
        }
        return minNode;
    }
    
    private static ArrayList<node> getSameNodes(ArrayList<node> i1, node tC){
        ArrayList<node> ret = new ArrayList<node>();
        for(node n : i1) {
            if(n==tC) {
                ret.add(n);
            }
        }
        return ret;
    }
    
}    
		

