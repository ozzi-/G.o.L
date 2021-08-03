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
		return new Dimension(Settings.winx, Settings.winy);
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics g) {
		Cell individual = null;
		
		for (int x = world.visibleWorldStartX; x < world.visibleWorldEndX; x++) {
			for (int y = world.visibleWorldStartY; y < world.visibleWorldEndY; y++) {
				individual = world.getInhabitants()[x][y];

				if (individual.isAlive()) {
					g.setColor(cell);
				} else {
					g.setColor(background);
				}
				g.fillRect((individual.getPosx()-world.visibleWorldStartX) * Settings.creatureScale, (individual.getPosy()-world.visibleWorldStartY) * Settings.creatureScale,
						Settings.creatureScale, Settings.creatureScale);
				g.setColor(Color.cyan);
				
			}
		}
		Creature pointerCreature = GoLActionHandlers.getPointerCreature();
		for (Cell cell : pointerCreature.getCells()) {
			g.fillRect((GoLActionHandlers.mouseX-world.visibleWorldStartX+cell.getPosx()) * Settings.creatureScale,(GoLActionHandlers.mouseY-world.visibleWorldStartY+cell.getPosy()) * Settings.creatureScale, Settings.creatureScale, Settings.creatureScale);
		}

		if (Settings.drawGrid) {
			for (int x = 0; x <= world.getWorldWidth(); x++) {
				g.setColor(Settings.cellColor);
				g.drawLine(x * Settings.creatureScale, 0, x * Settings.creatureScale,
						Settings.creatureScale * world.getWorldHeight());

			}
			for (int y = 0; y <= world.getWorldHeight(); y++) {
				g.setColor(Settings.cellColor);
				g.drawLine(0, y * Settings.creatureScale, Settings.creatureScale * world.getWorldWidth(),
						y * Settings.creatureScale);

			}
		}

	}

	public void setGah(GoLActionHandlers gah) {
		addMouseListener(gah.mouseListener());
		addMouseMotionListener(gah.mouseMotionListener());
	}
}
