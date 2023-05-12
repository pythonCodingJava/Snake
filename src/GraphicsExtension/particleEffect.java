package GraphicsExtension;

import java.awt.*;
import java.awt.geom.*;
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
