package GraphicsExtension;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class particleEffect {
	
	class particle{
		
		double x;
		double y;
		double size;
		double angle;
		double acceleration;
		double velocity = 0;
		boolean isDead = false;
		double radius;
		double s;
		
		int times = 0;
		
		public particle(double x, double y, double size, double angle, double acceleration, double radius) {
			this.x = x;
			this.y = y;
			this.size = size;
			this.angle = angle;
			this.acceleration = acceleration;
			this.radius = radius;
			this.s = size;
		}
		
		public void update() {
			if(!isDead) {
			velocity += acceleration;
			double xv = Math.cos(Math.toRadians(angle))*velocity;
			double yv = Math.sin(Math.toRadians(angle))*velocity;
			x+=xv;
			y+=yv;
			size -= 50/(Math.pow(radius/acceleration ,0.5));
			}
			if(size<=0) {
				isDead = true;
			}else {
				times++;
			}
		}
		
		public void show(Graphics2D g) {
			g.fill(new Rectangle2D.Double(x-(size/2), y-(size/2), size, size));
		}
	}
	
	
	/*class panel extends JPanel{
		
		ArrayList<particle> particles = new ArrayList<particle>();
		
		Timer t = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		
		public panel(int n, double x, double y) {
			for(int i = 0; i<n; i++) {
				particles.add(new particle(x, y, new Random().nextDouble()+new Random().nextInt(50), ((double)360/n)*i, new Random().nextDouble()+0.001, 500));
			}
			
			this.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					t.start();
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(1));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(Color.white);
			//g2d.draw(new Ellipse2D.Double(50,50,400,400));
			
			for(particle p : particles) {
				p.update();
				p.show(g2d);
			}
		}
	}
	
	public static void main(String[] args) {
		particleEffect me = new particleEffect();
		
		JFrame f = new JFrame();
		f.setSize(500,500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setContentPane(me.new panel(50, 250,250));
		f.getContentPane().setBackground(Color.black);
		f.setVisible(true);
	}
	*/
	
	ArrayList<particle> particles = new ArrayList<particle>();
	long t;
	
	public particleEffect(double x, double y, double n, double radius, int maxParticleSize) {
		for(int i = 0; i<n; i++) {
			particles.add(new particle(x, y, new Random().nextDouble()+new Random().nextInt(maxParticleSize-1), ((double)360/n)*i, Math.random()/2+0.001, 500));
		}
		t = System.currentTimeMillis();
	}
	
	public void update() {
		if(System.currentTimeMillis()-t >= 10){
			for(int i = 0; i<particles.size(); i++) {
				if(particles.get(i).isDead) {
					particles.remove(i);
				}else {
					particles.get(i).update();
				}
			}
			t = System.currentTimeMillis();
		}
	}
	
	public void show(Graphics2D g) {
		for(int i = 0; i<particles.size(); i++) {
			particles.get(i).show(g);
		}
	}
}
