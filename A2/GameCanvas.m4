package M4_PACKAGE_NAME;

import javax.swing.JPanel;
import java.awt.*;

public class GameCanvas extends JPanel{
	@Override
	public void paintComponent(Graphics g){
			Graphics2D g2 = (Graphics2D) g;
			g2.translate(20, 240);
			g2.setStroke(new BasicStroke(3));
			g2.drawLine(0, 0, 0, -200); // vertical axis
			g2.drawLine(0, 0, 200, 0);  // horizontal axis
			g2.setStroke(new BasicStroke(5));   // line
			g2.setColor(Color.RED); 
		   g2.drawLine(40, 0, 120, 0); 
		   g2.drawOval(40-4, -4, 8, 8);
			g2.drawOval(120-4, -4, 8, 8); 
			 // Copy last 4 lines.  Change color to GREEN.
			// What transformations to include to have it rotate
			// 45 degrees about the left-most endpoint?
			g2.setColor(Color.GREEN); 
			g2.translate(40,0);
			g2.rotate(-Math.PI/4.0);
			g2.translate(-40,0);
		   g2.drawLine(40, 0, 120, 0); 
		   g2.drawOval(40-4, -4, 8, 8);
			g2.drawOval(120-4, -4, 8, 8); 
	}
}
