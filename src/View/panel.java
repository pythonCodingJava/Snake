package View;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import Logic.*;
import GraphicsExtension.*;

public class panel extends JPanel{

    double offsetX = 0;
    double offsetY = 0;
    double shakeIntensity = 0;

    boolean postProcessing = false;

    ArrayList<particleEffect> effects = new ArrayList<particleEffect>();

    // int recordCalled = 0;
	// int called = 0;
    long t;

    Dimension size;
    int resolution;

    public panel(Dimension size, int resolution){
        this.size = size;
        this.resolution = resolution;
    }
    
    public void update(){
        // Point toGet = new Point(x,y);
				
        // if(ai) {
            
        //     try {
        //         toGet = path.get(path.indexOf(new Point(snek.get(0).x, snek.get(0).y))-1);
        //     }catch(Exception e) {
        //         toGet = path.get(path.size()-1);
        //     }
        // }
        // snek.get(0).Pos(toGet.x, toGet.y);

        if(offsetX!=0) {
            if(System.currentTimeMillis()-t >= 100) {
                offsetX = -(Math.abs(offsetX)/offsetX)*shakeIntensity;
                t = System.currentTimeMillis();
                shakeIntensity-=4;
            }
        }else if(offsetY!=0){
            if(System.currentTimeMillis()-t >= 100) {
                offsetY = -(Math.abs(offsetY)/offsetY)*shakeIntensity;
                t = System.currentTimeMillis();
                shakeIntensity-=4;
            }
        }
        if(shakeIntensity<=0) {
            offsetX=0;
            offsetY=0;
            shakeIntensity = 12;
        }
    }

    public void paintComponent(Graphics g){
        update();
        super.paintComponent(g);
        
			Graphics2D g2d = (Graphics2D) g;
			// g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			g2d.setStroke(new BasicStroke(1));

			for(int i = 0; i<Main.me.foods.size(); i++) {
				int foodx = Main.me.foods.get(i).x;
				int foody = Main.me.foods.get(i).y;
				if(Main.me.snek.get(0).x==foodx && Main.me.snek.get(0).y==foody) {
					Main.me.s++;
					Main.me.foods.remove(i);
					Main.me.snek.add(new block(Main.me.snek.get(Main.me.snek.size()-1).prex,Main.me.snek.get(Main.me.snek.size()-1).prey));
					effects.add(new particleEffect(foodx*resolution+offsetX, foody*resolution+offsetY, 50, 150, 30));
					shakeCamera();
					Main.me.getFood();
				}
			}

            for(Point b : Main.me.path){
                double i = b.y;
				double j = b.x;
				Rectangle2D tbf = new Rectangle2D.Double(j*(resolution)+offsetX, i*(resolution)+offsetY, (resolution), (resolution));
				g2d.setColor(Color.red.darker().darker());
                g2d.fill(tbf);
            }
            
            g2d.setColor(Color.cyan.darker().darker());                            
            int[][] off = {{1,0},{-1,0},{0,1},{0,-1}};
            for(block b : Main.me.snek){
                int i = b.y;
				int j = b.x;
				if(Main.me.snek.indexOf(b) != 0){
                    for(int[] o : off){
                        int x = j + o[0];
                        int y = i + o[1];
                        if((x >= 0 && x<Main.me.board[0].length) && (y >= 0 && y<Main.me.board.length)){
                            if(!(x == Main.me.snek.get(0).x && y == Main.me.snek.get(0).y)){
                                Rectangle2D tbf = new Rectangle2D.Double(x*(resolution)+offsetX, y*(resolution)+offsetY, (resolution), (resolution));
                                g2d.fill(tbf);
                            }
                        }
                    }
                }
            }
			
            g2d.setColor(Color.cyan);	
			for(block b : Main.me.snek) {
				int i = b.y;
				int j = b.x;
                Rectangle2D tbf = new Rectangle2D.Double(j*(resolution)+offsetX, i*(resolution)+offsetY, (resolution), (resolution));
				g2d.fill(tbf);
				if(postProcessing) {
					glow.glowRect(tbf.getX(), tbf.getY(), tbf.getWidth(), tbf.getHeight(), g2d);
				}
			}
			
			for(Point pp : Main.me.foods) {
				int foodx = pp.x;
				int foody = pp.y;
				Rectangle2D tbf = new Rectangle2D.Double(foodx*resolution+offsetX, foody*resolution+offsetY, resolution, resolution);
				g2d.setColor(new Color(255,0,0));
				g2d.fill(tbf);
				g2d.setColor(new Color(255,0,0));
				if(postProcessing) {
					glow.glowRect(tbf.getX(), tbf.getY(), tbf.getWidth(), tbf.getHeight(), g2d);
				}
			}
			
			for(particleEffect p : effects) {
				p.update();
			}
			for(particleEffect p : effects) {
				g2d.setColor(new Color(255,0,0,250));
				p.show(g2d);
			}

			g2d.setColor(Color.yellow);
			g2d.setFont(new Font("Pixelated Regular", Font.BOLD, 30));
			g2d.drawString("BEST : "+ java.lang.Integer.toString((int)Main.me.bestScore), size.width/2 - 75 + (int)offsetX, 90+(int)offsetY);
			
			g2d.setColor(Color.white);
			g2d.setFont(new Font("Pixelated Regular", Font.BOLD, 60));
			g2d.drawString(Integer.toString(Main.me.s-1), size.width/2 - 30 + (int)offsetX, 60 + (int)offsetY);
    }

    public void reset(){
        effects = new ArrayList<particleEffect>();
        offsetX = 0;
        offsetY = 0;
        repaint();
    }

    public void shakeCamera() {
        int r = (int)(Math.floor(Math.random()*2));
        t = System.currentTimeMillis();
        if(r==1) {
            offsetY = shakeIntensity;
        }else {
            offsetX = shakeIntensity;
        }
    }
    
    
}
