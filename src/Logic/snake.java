package Logic;

import java.awt.Dimension;
import java.awt.Point;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

public class snake {
    
    public int[][] board;
	public ArrayList<block> snek;   
    public ArrayList<Point> foods = new ArrayList<Point>();
    
    public int s = 1;
    public int bestScore = 0;

	public int foodx = 0;
	public int foody = 0;
  
    public boolean dead = false;
		
    public boolean ai = false;
    public boolean moveByDir = true;

    //1 is right
    //2 is left
    //3 is up
    //4 is down
    public int direction = 1;
    public ArrayList<Point> path;

    public snake(int rows, int cols, Dimension size, int resolution){
        board = new int[rows][cols];
        path = new ArrayList<>();
        snek = new ArrayList<>();
        for(int i = 0; i<s; i++) {
            snek.add(new block(((size.width/2)/resolution) - (int)i/s, ((size.height/2)/resolution)));
        }
        empty();
        for(int i = 0; i<1; i++) {
            getFood();
        }
    }

    public void getFood() {
        int x = new Random().nextInt(board[0].length-1);
        int y = new Random().nextInt(board.length-1);
        
        boolean taken = checkTaken(x, y);
        while(taken) {
            x = new Random().nextInt(board[0].length-1);
            y = new Random().nextInt(board.length-1);
            taken = checkTaken(x, y);
        }
        
        foodx = x;
        foody = y;
        foods.add(new Point(foodx, foody));
        board[y][x] = 2;

        // if(ai){
            AI.warp = false;
            if(s>50) {
                AI.warp = true;
            }
            // updatePath();
        // }
    }

    public boolean checkTaken(int x, int y) {
        boolean ret = false;
        for(block b : snek) {
            if(x==b.x && y==b.y) {
                ret = true;
                break;
            }
        }
        return ret;
    }
  
    public void updatePos() {
        int x = snek.get(0).x;
        int y = snek.get(0).y;
        
        if(direction==1) {
            x++;
        }else if(direction==2) {
            x--;
        }else if(direction==3) {
            y--;
        }else if(direction==4) {
            y++;
        }
        
        if(x>=board[0].length) {
            x = 0;
        }else if(x < 0) {
            x = board[0].length-1;
        }else if(y>=board.length) {
            y = 0;
        }else if(y < 0) {
            y = board.length-1;
        }

        if(moveByDir) snek.get(0).Pos(x,y);

        for(int i = 1; i<snek.size(); i++) {
            block b = snek.get(i);
            if(snek.get(0).x==b.x && snek.get(0).y==b.y) {
                dead = true;
                if(s-1>bestScore) {
                    try {
                        FileWriter fw = new FileWriter("logs/score.txt");
                        fw.write(java.lang.Double.toString(s-1));
                        fw.flush();
                        fw.close();
                    }catch(Exception e) {
                    
                    }
                }
                System.out.println("game over");
            }
        }
        
        for(int i = 1; i<snek.size(); i++) {	
            snek.get(i).Pos(snek.get(i-1).prex, snek.get(i-1).prey);
        }
        
        for(block b : snek) {
            try{
            board[b.y][b.x] = 1;
            }catch(Exception e) {}
        }
        
    }

    public void empty() {
        for(int i = 0; i<board.length; i++) {
            for(int j = 0; j<board[i].length; j++) {
                if(board[i][j]==1) {
                    board[i][j]=0;
                }
            }
        }
    }

    public void update(){
		int x = snek.get(0).x;
		int y = snek.get(0).y;

        Point toGet = new Point(x,y);
				
        if(ai && !moveByDir && !dead) {
            for(int i = 1; i<path.size(); i++){
                if(path.get(i).x == snek.get(0).x && path.get(i).y == snek.get(0).y){
                    toGet = path.get(i-1);
                }
            }
            if(toGet.x == x && toGet.y == y){
                toGet = path.get(path.size()-1);
            }

            snek.get(0).Pos(toGet.x, toGet.y);
            for(int i = 1; i<snek.size(); i++) {
                block b = snek.get(i);
                if(snek.get(0).x==b.x && snek.get(0).y==b.y) {
                    dead = true;
                    System.out.println("game over");
                }
            }
        }

        
        // updateDir(toGet);

        if(!dead){
            empty();
            updatePos();
            if(ai){
                try{
                    // dumbMove();
                    updatePath();
                    moveByDir = false;
                }catch(Exception e){
                    dumbMove();
                    System.out.println("dummy " + direction);
                    moveByDir = true;
                }
            }
        }
    }

    public void dumbMove(){
        if(!willDie(direction)) return;

        for(int dir = 1; dir<5; dir++){
            if(!willDie(dir)){
                direction = dir;
                break;
            }
        }
    }

    private boolean willDie(int direction){
        int x = snek.get(0).x;
        int y = snek.get(0).y;
        
        if(direction==1) {
            x++;
        }else if(direction==2) {
            x--;
        }else if(direction==3) {
            y--;
        }else if(direction==4) {
            y++;
        }
        
        if(x>=board[0].length) {
            x = 0;
        }else if(x < 0) {
            x = board[0].length-1;
        }else if(y>=board.length) {
            y = 0;
        }else if(y < 0) {
            y = board.length-1;
        }

        for(int i = 1; i<snek.size(); i++) {
            block b = snek.get(i);
            if(x==b.x && y==b.y) {
                return true;
            }
        }

        return false;
    }
    
    public void reset(Dimension size, int resolution){
        snek = new ArrayList<block>();
        s = 1;
        for(int i = 0; i<s; i++) {
            snek.add(new block(((size.width-1)/resolution) - i, ((size.height/2)/resolution)));
        }
        // empty();
        board = new int[size.height/resolution][size.width/resolution];
        dead = false;
        direction = 1;
        foods = new ArrayList<Point>();
        for(int i = 0; i<1; i++) {
            getFood();
        }
        updatePos();
    }

    public void updatePath(){
        try{
            path = AI.getPath(board, snek, foodx, foody, true);
        }catch(Exception e){
            path = AI.getPath(board, snek, foodx, foody, false);
        }
    }

    public void updateDir(Point p){
        block head = snek.get(0);
        int x = p.x-head.x;
        int y = p.y - head.x;
        if(x > 0) direction = 1;
        if(x < 0) direction = 2;
        if(y > 0) direction = 4;
        if(y < 0) direction = 3;
    }
}
