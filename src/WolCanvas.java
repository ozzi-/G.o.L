import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;

public class WolCanvas extends Canvas {
	private static final long serialVersionUID = 1L;

	int width, height, countx, county;
	private World world;


	WolCanvas(World world, int w, int h, int countx, int county) {
		this.world=world;
		setSize(width = w, height = h);
		this.countx=countx;
		this.county=county;
	}
	
	 @Override
	 public Dimension getPreferredSize() {
	      return new Dimension(Settings.winx, Settings.winy);  
	 } 

	public void paint(Graphics g) {
		for (Cell individual : world.inhabitants) {
			g.fillRect(individual.getPosx()*Settings.lwidth, individual.getPosy()*Settings.lheight, Settings.lwidth, Settings.lheight);			
		}
	}
}
