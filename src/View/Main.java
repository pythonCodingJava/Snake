package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.Timer;

import Logic.*;
import GraphicsExtension.*;

public class Main {
    
	static Dimension size = /*Toolkit.getDefaultToolkit().getScreenSize();//*/new Dimension(500,500);

	static snake me;
	static panel p;
    private static JFrame f;
    private static Timer t;
    static int resolution = 20;

    private static long time;

    public static void main(String[] args) {

		f = new JFrame();
        me = new snake((int)Math.ceil((double)size.height/resolution),(int)Math.ceil((double)size.width/resolution), size, resolution);
        p = new panel(size, resolution);

        t = new Timer(1, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if((System.currentTimeMillis()-time) >= 100) {
                    // System.out.println(me.snek.get(0).x+", "+me.snek.get(0).y);
                    me.update();
                    time = System.currentTimeMillis();
                }
                f.getContentPane().repaint();
            }

        });

		f.setSize(size.width, size.height);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setContentPane(p);
		f.setUndecorated(true);
		f.getContentPane().setBackground(Color.black);
		f.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				int key = arg0.getKeyCode();
				if(key==32) {
					if(t.isRunning()) {
						t.stop();
					}else {
						t.start();
					}
				}
				if(key==38) {
					if(me.direction!=4) {
						me.direction = 3;
					}
				}else if(key==40) {
					if(me.direction!=3) {
						me.direction = 4;
					}
				}else if(key==39) {
					if(me.direction!=2) {
						me.direction = 1;
					}
				}else if(key==37) {
					if(me.direction!=1) {
						me.direction = 2;
					}
				}else if(key==80) {
					p.postProcessing = !p.postProcessing;
				}else if(key==82 && me.dead) {
					// try {
					// 	File toRead = new File("C:\\Users\\admin\\Desktop\\gameData\\score.txt");
					// 	Scanner s = new Scanner(toRead);
					// 	me.bestScore = s.nextDouble();
					// 	s.close();
					// }catch(Exception e) {
					// 	System.out.println("error");
					// }
                    me.reset(size, resolution);
				    t.stop();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		f.setVisible(true);

        // t.start();
	}

}
