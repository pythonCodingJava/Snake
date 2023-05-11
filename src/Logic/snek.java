package Logic;

// import GraphicsExtension.*;
// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.awt.event.KeyEvent;
// import java.awt.event.KeyListener;
// import java.awt.event.MouseEvent;
// import java.awt.event.MouseListener;
// import java.awt.geom.Line2D;
// import java.awt.geom.Rectangle2D;
// import java.awt.geom.Rectangle2D.Double;
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileWriter;
// import java.util.ArrayList;
// import java.util.Random;
// import java.util.Scanner;

public class snek {
	
// 	@SuppressWarnings("serial")
// 	class panel extends JPanel{
		
// 		boolean warp = false;
		
// 		boolean ai = true;
		
// 		int s = 1;
		
// 		double offsetX = 0;
// 		double offsetY = 0;
// 		double shakeIntensity = 0;
		
// 		boolean dead = false;
		
// 		//1 is right
// 		//2 is left
// 		//3 is up
// 		//4 is down
// 		int direction = 1;
		
// 		boolean postProcessing = true;
		
// 		int foodx = 0;
// 		int foody = 0;
		
// 		FileWriter fw;
		
// 		ArrayList<Point> foods = new ArrayList<Point>();
		
// 		ArrayList<particleEffect> effects = new ArrayList<particleEffect>();
		
// 		int resolution = 20;//50;
// 		int[][] board = new int[(int)Math.ceil((double)size.height/resolution)][(int)Math.ceil((double)size.width/resolution)];
// 		ArrayList<block> snek = new ArrayList<block>();
// 		ArrayList<Point> path = new ArrayList<Point>();
		
// 		double bestScore = 0;
		
// 		int recordCalled = 0;
// 		int called = 0;
// 		Timer t = new Timer(10, new ActionListener() {

// 			@Override
// 			public void actionPerformed(ActionEvent arg0) {
// 				// TODO Auto-generated method stub
// 				int factor = 5;
// 				if(ai) {
// 					factor = 1;
// 				}

// 				int x = snek.get(0).x;
// 				int y = snek.get(0).y;
// 				Point toGet = new Point(x,y);
				
// 				if(ai) {
					
// 					try {
// 						toGet = path.get(path.indexOf(new Point(snek.get(0).x, snek.get(0).y))-1);
// 					}catch(Exception e) {
// 						toGet = path.get(path.size()-1);
// 					}
// 				}
// 				snek.get(0).Pos(toGet.x, toGet.y);

// 				if(called%factor==0 && !dead) {
// 					empty();
// 					updatePos();
// 				}
// 				if(offsetX!=0) {
// 					if((called-recordCalled)%6==0) {
// 						offsetX = -(Math.abs(offsetX)/offsetX)*shakeIntensity;
// 					}
// 					shakeIntensity--;
// 				}else if(offsetY!=0){
// 					if((called-recordCalled)%6==0) {
// 						offsetY = -(Math.abs(offsetY)/offsetY)*shakeIntensity;
// 					}
// 					shakeIntensity--;
// 				}
// 				if(shakeIntensity<=0) {
// 					offsetX=0;
// 					offsetY=0;
// 					shakeIntensity = 20;
// 				}
// 				repaint();
// 				called++;
// 			}
			
// 		});
		
// 		public panel() {
// 			try {
// 				File toRead = new File("C:\\Users\\admin\\Desktop\\gameData\\score.txt");
// 				Scanner s = new Scanner(toRead);
// 				bestScore = s.nextDouble();
// 				s.close();
// 			}catch(Exception e) {
// 				System.out.println("error");
// 			}
// 			for(int i = 0; i<s; i++) {
// 				snek.add(new block(((size.width/2)/resolution) - (int)i/s, ((size.height/2)/resolution)));
// 			}
// 			empty();
// 			for(int i = 0; i<1; i++) {
// 				getFood();
// 			}
// 			//updatePos();
// 		}
		
// 		public void getFood() {
// 			int x = new Random().nextInt(board[0].length-1);
// 			int y = new Random().nextInt(board.length-1);
			
// 			boolean taken = checkTaken(x, y);
// 			while(taken) {
// 				x = new Random().nextInt(board[0].length-1);
// 				y = new Random().nextInt(board.length-1);
// 				taken = checkTaken(x, y);
// 			}
			
// 			foodx = x;
// 			foody = y;
// 			foods.add(new Point(foodx, foody));
// 			board[y][x] = 2;

// 			if(ai) {
// 				if(s>50) {
// 					warp = true;
// 					path = getPath(board, snek);
// 				}else {
// 					warp = false;
// 					path = getPath(board, snek);
// 				}
// 			}
// 		}
		
// 		public boolean checkTaken(int x, int y) {
// 			boolean ret = false;
// 			for(block b : snek) {
// 				if(x==b.x && y==b.y) {
// 					ret = true;
// 					break;
// 				}
// 			}
// 			return ret;
// 		}
		
// 		public void empty() {
// 			for(int i = 0; i<board.length; i++) {
// 				for(int j = 0; j<board[i].length; j++) {
// 					if(board[i][j]==1) {
// 						board[i][j]=0;
// 					}
// 				}
// 			}
// 		}
		
// 		public void updatePos() {
// 			int x = snek.get(0).x;
// 			int y = snek.get(0).y;
			
// 			if(direction==1) {
// 				x++;
// 			}else if(direction==2) {
// 				x--;
// 			}else if(direction==3) {
// 				y--;
// 			}else if(direction==4) {
// 				y++;
// 			}
			
// 			if(x>=board[0].length) {
// 				x = 0;
// 			}else if(x < 0) {
// 				x = board[0].length-1;
// 			}else if(y>=board.length) {
// 				y = 0;
// 			}else if(y < 0) {
// 				y = board.length-1;
// 			}
			
// 			for(int i = 1; i<snek.size(); i++) {
// 				block b = snek.get(i);
// 				if(snek.get(0).x==b.x && snek.get(0).y==b.y) {
// 					dead = true;
// 					if(!ai){
// 						if(s-1>bestScore) {
// 							try {
// 								fw = new FileWriter("C:\\Users\\admin\\Desktop\\gameData\\score.txt");
// 								fw.write(java.lang.Double.toString(s-1));
// 								fw.flush();
// 								fw.close();
// 							}catch(Exception e) {
							
// 							}
// 						}
// 					}
// 					System.out.println("game over");
// 				}
// 			}
			
// 			for(int i = 1; i<snek.size(); i++) {	
// 				snek.get(i).Pos(snek.get(i-1).prex, snek.get(i-1).prey);
// 			}
			
// 			for(block b : snek) {
// 				try{
// 				board[b.y][b.x] = 1;
// 				}catch(Exception e) {}
// 			}
			
// 		}
		
// 		public void paintComponent(Graphics g) {
// 			super.paintComponent(g);
			
// 			Graphics2D g2d = (Graphics2D) g;
// 			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
// 			g2d.setStroke(new BasicStroke(1));
			
// 			for(int i = 0; i<foods.size(); i++) {
// 				int foodx = foods.get(i).x;
// 				int foody = foods.get(i).y;
// 				if(snek.get(0).x==foodx && snek.get(0).y==foody) {
// 					s++;
// 					foods.remove(i);
// 					snek.add(new block(snek.get(snek.size()-1).prex,snek.get(snek.size()-1).prey));
// 					effects.add(new particleEffect(foodx*resolution+offsetX, foody*resolution+offsetY, 120, 150, 30));
// 					shakeCamera();
// 					getFood();
// 				}
// 			}
			
// 			for(block b : snek) {
// 				double i = b.y;
// 				double j = b.x;
// 				Rectangle2D tbf = new Rectangle2D.Double(j*(resolution)+offsetX, i*(resolution)+offsetY, (resolution), (resolution));
// 				g2d.setColor(Color.cyan);
// 				g2d.fill(tbf);
// 				if(postProcessing) {
// 					glow.glowRect(tbf.getX(), tbf.getY(), tbf.getWidth(), tbf.getHeight(), g2d);
// 				}
// 			}
			
// 			for(Point pp : foods) {
// 				int foodx = pp.x;
// 				int foody = pp.y;
// 				Rectangle2D tbf = new Rectangle2D.Double(foodx*resolution+offsetX, foody*resolution+offsetY, resolution, resolution);
// 				g2d.setColor(new Color(255,0,0));
// 				g2d.fill(tbf);
// 				g2d.setColor(new Color(255,0,0));
// 				if(postProcessing) {
// 					glow.glowRect(tbf.getX(), tbf.getY(), tbf.getWidth(), tbf.getHeight(), g2d);
// 				}
// 			}
			
// 			for(particleEffect p : effects) {
// 				p.update();
// 			}
// 			for(particleEffect p : effects) {
// 				g2d.setColor(new Color(255,0,0,250));
// 				p.show(g2d);
// 			}

// 			g2d.setColor(Color.yellow);
// 			g2d.setFont(new Font("Pixelated Regular", Font.BOLD, 30));
// 			g2d.drawString("BEST : "+ java.lang.Integer.toString((int)bestScore), size.width/2 - 75 + (int)offsetX, 90+(int)offsetY);
			
// 			g2d.setColor(Color.white);
// 			g2d.setFont(new Font("Pixelated Regular", Font.BOLD, 60));
// 			g2d.drawString(Integer.toString(s-1), size.width/2 - 30 + (int)offsetX, 60 + (int)offsetY);

// 		}
		
// 		public void shakeCamera() {
// 			int r = (int)(Math.floor(Math.random()*2));
// 			recordCalled = called;
// 			if(r==1) {
// 				offsetY = shakeIntensity;
// 			}else {
// 				offsetX = shakeIntensity;
// 			}
// 		}
		
// 		public ArrayList<Point> getPath(int[][] b, ArrayList<block> s){
// 			ArrayList<Point> p = new ArrayList<Point>();
			
// 			Point start = new Point(s.get(0).x, s.get(0).y);
// 			Point end = new Point(foodx, foody);
// 			ArrayList<node> walls = new ArrayList<node>();
// 			node[][] bClone = new node[b.length][b[0].length];
// 			for(int i = 0; i<b.length; i++) {
// 				for(int j = 0; j<b[0].length; j++) {
// 					bClone[i][j] = new node(j,i);
// 					if(b[i][j]==1 && new Point(j,i)!=start) {
// 						walls.add(bClone[i][j]);
// 					}
// 				}
// 			}
// 			bClone[start.y][start.x].g = 0;
// 			bClone[end.y][end.x].h = 0;
			
// 			ArrayList<node> openSet = new ArrayList<node>();
// 			ArrayList<node> closedSet = new ArrayList<node>();
// 			node current = bClone[start.y][start.x];
// 			openSet.add(current);
// 			boolean running = true;
// 			while(running) {
// 				if(current == bClone[end.y][end.x]) {
// 					running = false;
// 					break;
// 				}else {
// 					ArrayList<node> nb = new ArrayList<node>();
// 					if(current.y<=bClone.length-2) {
// 						nb.add(bClone[current.y+1][current.x]);
// 					}else if(warp){
// 						nb.add(bClone[0][current.x]);
// 					}
// 					if(current.y>0) {
// 						nb.add(bClone[current.y-1][current.x]);
// 					}else if(warp){
// 						nb.add(bClone[bClone.length-1][current.x]);
// 					}
// 					if(current.x<=bClone[0].length-2) {
// 						nb.add(bClone[current.y][current.x+1]);
// 					}else if(warp){
// 						nb.add(bClone[current.y][0]);
// 					}
// 					if(current.x>0) {
// 						nb.add(bClone[current.y][current.x-1]);
// 					}else if(warp){
// 						nb.add(bClone[current.y][bClone[0].length-1]);
// 					}
					
// 					for(int i = nb.size()-1; i>=0; i--) {
// 						if(closedSet.contains(nb.get(i)) || walls.contains(nb.get(i))) {
// 							nb.remove(i);
// 						}else {
// 							nb.get(i).g = current.g+1;
// 							double toAdd =  Point.distance(nb.get(i).x, nb.get(i).y, end.x, end.y);
// 							if(warp) {
// 								double[] toCompare = {
// 										Point.distance(nb.get(i).x, nb.get(i).y, end.x-b[0].length, end.y),
// 										Point.distance(nb.get(i).x, nb.get(i).y, end.x, end.y-b[0].length),
// 								};
// 								for(double tCm : toCompare) {
// 									if(tCm<toAdd) {
// 										toAdd=tCm;
// 									}
// 								}
// 							}
// 							nb.get(i).h = toAdd;//Point.distance(nb.get(i).x, nb.get(i).y, end.x, end.y);
// 							nb.get(i).score = nb.get(i).g + nb.get(i).h;
// 							nb.get(i).from = new Point(current.x, current.y);
// 							if(openSet.contains(nb.get(i))) {
// 								if(openSet.get(openSet.indexOf(bClone[nb.get(i).y][nb.get(i).x])).score>nb.get(i).score) {
// 									openSet.remove(bClone[nb.get(i).y][nb.get(i).x]);
// 									openSet.add(nb.get(i));
// 								}
// 							}else {
// 								openSet.add(nb.get(i));
// 							}
// 						}
// 					}
					
// 					ArrayList<node> mins = getSameNodes(openSet, getMinNode(openSet));
// 					node min = mins.get(0);
// 					for(node n : mins) {
// 						if(n.h<min.h) {
// 							min = n;
// 						}
// 					}
					
// 					openSet.remove(min);
// 					closedSet.add(min);
// 					current = min;
// 				}
// 			}
			
// 			boolean r2 = true;
// 			p.add(end);
// 			node tT = bClone[end.y][end.x];
// 			while(r2) {
// 				if(tT == bClone[start.y][start.x]) {
// 					r2 = false;
// 					break;
// 				}else {
// 					p.add(tT.from);
// 					tT = bClone[tT.from.y][tT.from.x];
// 				}
// 			}
			
// 			return p;
// 		}
		
// 		public ArrayList<Point> reverseAstar(int[][] b, ArrayList<block> s){
// 			ArrayList<Point> p = new ArrayList<Point>();
			
// 			Point start = new Point(s.get(0).x, s.get(0).y);
// 			Point end = new Point(foodx, foody);
// 			ArrayList<node> walls = new ArrayList<node>();
// 			node[][] bClone = new node[b.length][b[0].length];
// 			for(int i = 0; i<b.length; i++) {
// 				for(int j = 0; j<b[0].length; j++) {
// 					bClone[i][j] = new node(j,i);
// 					if(b[i][j]==1 && new Point(j,i)!=start) {
// 						walls.add(bClone[i][j]);
// 					}
// 				}
// 			}
// 			bClone[start.y][start.x].g = 0;
// 			bClone[end.y][end.x].h = 0;
			
// 			ArrayList<node> openSet = new ArrayList<node>();
// 			ArrayList<node> closedSet = new ArrayList<node>();
// 			node current = bClone[start.y][start.x];
// 			openSet.add(current);
// 			boolean running = true;
// 			while(running) {
// 				if(current == bClone[end.y][end.x]) {
// 					running = false;
// 					break;
// 				}else {
// 					ArrayList<node> nb = new ArrayList<node>();
// 					if(current.y<=bClone.length-2) {
// 						nb.add(bClone[current.y+1][current.x]);
// 					}else if(warp){
// 						nb.add(bClone[0][current.x]);
// 					}
// 					if(current.y>0) {
// 						nb.add(bClone[current.y-1][current.x]);
// 					}else if(warp){
// 						nb.add(bClone[bClone.length-1][current.x]);
// 					}
// 					if(current.x<=bClone[0].length-2) {
// 						nb.add(bClone[current.y][current.x+1]);
// 					}else if(warp){
// 						nb.add(bClone[current.y][0]);
// 					}
// 					if(current.x>0) {
// 						nb.add(bClone[current.y][current.x-1]);
// 					}else if(warp){
// 						nb.add(bClone[current.y][bClone[0].length-1]);
// 					}
					
// 					for(int i = nb.size()-1; i>=0; i--) {
// 						if(closedSet.contains(nb.get(i)) || walls.contains(nb.get(i))) {
// 							nb.remove(i);
// 						}else {
// 							nb.get(i).g = current.g+1;
// 							double toAdd =  Point.distance(nb.get(i).x, nb.get(i).y, end.x, end.y);
// 							if(warp) {
// 							double[] toCompare = {
// 									Point.distance(nb.get(i).x, nb.get(i).y, end.x-b[0].length, end.y),
// 									Point.distance(nb.get(i).x, nb.get(i).y, end.x, end.y-b[0].length),
// 							};
// 							for(double tCm : toCompare) {
// 								if(tCm>toAdd) {
// 									toAdd=tCm;
// 								}
// 							}
// 							}
// 							nb.get(i).h = toAdd;//Point.distance(nb.get(i).x, nb.get(i).y, end.x, end.y);
// 							nb.get(i).score = nb.get(i).g + nb.get(i).h;
// 							nb.get(i).from = new Point(current.x, current.y);
// 							if(openSet.contains(nb.get(i))) {
// 								if(openSet.get(openSet.indexOf(bClone[nb.get(i).y][nb.get(i).x])).score<nb.get(i).score) {
// 									openSet.remove(bClone[nb.get(i).y][nb.get(i).x]);
// 									openSet.add(nb.get(i));
// 								}
// 							}else {
// 								openSet.add(nb.get(i));
// 							}
// 						}
// 					}
					
// 					ArrayList<node> mins = getSameNodes(openSet, getMaxNode(openSet));
// 					node min = mins.get(0);
// 					for(node n : mins) {
// 						if(n.h>min.h) {
// 							min = n;
// 						}
// 					}
					
// 					openSet.remove(min);
// 					closedSet.add(min);
// 					current = min;
// 				}
// 			}
			
// 			boolean r2 = true;
// 			p.add(end);
// 			node tT = bClone[end.y][end.x];
// 			while(r2) {
// 				if(tT == bClone[start.y][start.x]) {
// 					r2 = false;
// 					break;
// 				}else {
// 					p.add(tT.from);
// 					tT = bClone[tT.from.y][tT.from.x];
// 				}
// 			}
			
// 			return p;
// 		}

// 		public node getMinNode(ArrayList<node> n) {
// 			node  minNode = n.get(0);
// 			for(node ns : n) {
// 				if(ns.score<minNode.score) {
// 					minNode=ns;
// 				}
// 			}
// 			return minNode;
// 		}

// 		public node getMaxNode(ArrayList<node> n) {
// 			node  minNode = n.get(0);
// 			for(node ns : n) {
// 				if(ns.score>minNode.score) {
// 					minNode=ns;
// 				}
// 			}
// 			return minNode;
// 		}
		
// 		public ArrayList<node> getSameNodes(ArrayList<node> i1, node tC){
// 			ArrayList<node> ret = new ArrayList<node>();
// 			for(node n : i1) {
// 				if(n==tC) {
// 					ret.add(n);
// 				}
// 			}
// 			return ret;
// 		}
		
// 	}
	
}
