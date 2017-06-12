package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import utils.GoLMouseActionHandler;

public class WolPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private final Color grey = new Color(234, 234, 234);
	private final Color black = new Color(0, 0, 0);
	int width, height, countx, county;
	private World world;

	public WolPanel(World world, int w, int h, int countx, int county) {
		this.world = world;
		setSize(width = w, height = h);
		this.countx = countx;
		this.county = county;
		addMouseListener(new GoLMouseActionHandler(world, this));
	}
	
	public void repaintPanel(){
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Settings.winx, Settings.winy);
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics g) {
		Cell individual = null;
		for (int x = 0; x < world.getWorldWidth(); x++) {
			for (int y = 0; y < world.getWorldHeight(); y++) {
				individual = world.getInhabitants()[x][y];
				if (individual.isAlive()) {
					g.setColor(black);
				} else {
					g.setColor(grey);
				}
				g.fillRect(	individual.getPosx() * Settings.lwidth, 
							individual.getPosy() * Settings.lheight,
							Settings.lwidth, Settings.lheight );
			}
		}
	}
}