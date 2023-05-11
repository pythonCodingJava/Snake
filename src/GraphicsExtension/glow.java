package GraphicsExtension;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class glow {
	
	static double radius = 100;
	
	public static void glowCircle(double r, double x, double y, Graphics2D g) {
		for(int i = (int)r; i<radius+r; i++) {
			g.setColor(new Color(g.getColor().getRed(), g.getColor().getGreen(), g.getColor().getBlue(), (int)(255/(i-r+1))));
			g.draw(new Ellipse2D.Double(x-i, y-i, i*2, i*2));
		}
	}
	
	public static void glowRect(double x, double y, double height, double width, Graphics2D g) {
		double glowRadius = 2;
		for(int i = (int)0; i<radius; i+=glowRadius) {
			try {
			g.setColor(new Color(g.getColor().getRed(), g.getColor().getGreen(), g.getColor().getBlue(), (int)(255/((i*(100/radius))+1))));
			}catch(Exception e) {
				
			}
			Rectangle2D toBeDrawn = new Rectangle2D.Double(x-i/glowRadius, y-i/glowRadius, width+ (2*i/glowRadius), height+ (2*i/glowRadius));
			g.draw(toBeDrawn);
		}
	}
	
}
