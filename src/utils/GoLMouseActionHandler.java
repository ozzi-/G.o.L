package utils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import simulation.Cell;
import simulation.Settings;
import simulation.WolPanel;
import simulation.World;

public class GoLMouseActionHandler implements MouseListener {
	private World world;
	private WolPanel panel;

	public GoLMouseActionHandler(World world, WolPanel panel) {
		this.world = world;
		this.panel = panel;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			world.bear(e.getX() / Settings.lwidth, e.getY() / Settings.lheight);
		} else if (SwingUtilities.isRightMouseButton(e)) {
			Cell dyingCell = world.getInhabitants()[e.getX() / Settings.lwidth][e.getY() / Settings.lheight];
			dyingCell.kill();
		}
		panel.repaint();
	}

}
