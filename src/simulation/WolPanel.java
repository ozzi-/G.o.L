package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import utils.GoLActionHandlers;

public class WolPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private final Color background = Settings.backgroundColor;
	private final Color cell = Settings.cellColor;
	int width, height, countx, county;
	private World world;


	public WolPanel(World world, int w, int h, int countx, int county) {
		this.world = world;
		setSize(width = w, height = h);
		this.countx = countx;
		this.county = county;
	}

	public void repaintPanel() {
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Settings.winX, Settings.winY);
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics g) {
		Cell individual = null;

		// CLS
		g.setColor(Color.black);
		g.fillRect(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		
		// Render world background
		g.setColor(background);
		Cell borderInhabitant = world.getInhabitants()[world.visibleWorldEndX][world.visibleWorldEndY];
		int worldAbsPxEndX = (borderInhabitant.getPosx()-world.visibleWorldStartX) * Settings.cellScale;
		int worldAbsPxEndY = (borderInhabitant.getPosy()-world.visibleWorldStartY) * Settings.cellScale;
		g.fillRect(0, 0, worldAbsPxEndX, worldAbsPxEndY);
		
		// Render alive cells
		for (int x = world.visibleWorldStartX; x < world.visibleWorldEndX; x++) {
			for (int y = world.visibleWorldStartY; y < world.visibleWorldEndY; y++) {
				individual = world.getInhabitants()[x][y];
				if (individual.isAlive()) {
					g.setColor(cell);
					g.fillRect((individual.getPosx()-world.visibleWorldStartX) * Settings.cellScale, (individual.getPosy()-world.visibleWorldStartY) * Settings.cellScale,
							Settings.cellScale, Settings.cellScale);
				}
				
			}
		}
		
		// Render Mouse Pointer
		g.setColor(Color.CYAN);
		Creature pointerCreature = GoLActionHandlers.getPointerCreature();
		for (Cell cell : pointerCreature.getCells()) {
			g.fillRect((GoLActionHandlers.mouseX-world.visibleWorldStartX+cell.getPosx()) * Settings.cellScale,(GoLActionHandlers.mouseY-world.visibleWorldStartY+cell.getPosy()) * Settings.cellScale, Settings.cellScale, Settings.cellScale);
		}
		
		// Render Grid
		if (Settings.drawGrid) {
			for (int x = 0; x <= world.getWorldWidth(); x++) {
				g.setColor(Settings.cellColor);
				g.drawLine(x * Settings.cellScale, 0, x * Settings.cellScale,
						Settings.cellScale * world.getWorldHeight());

			}
			for (int y = 0; y <= world.getWorldHeight(); y++) {
				g.setColor(Settings.cellColor);
				g.drawLine(0, y * Settings.cellScale, Settings.cellScale * world.getWorldWidth(),
						y * Settings.cellScale);

			}
		}
	}

	public void setGah(GoLActionHandlers gah) {
		addMouseListener(gah.mouseListener());
		addMouseMotionListener(gah.mouseMotionListener());
	}
}
