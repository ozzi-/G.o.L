package utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import simulation.Settings;
import simulation.WolPanel;
import simulation.World;

public class GoLActionHandlers {
	private World world;
	private WolPanel worldPanel;

	public GoLActionHandlers(World world, WolPanel worldPanel) {
		this.world = world;
		this.worldPanel = worldPanel;
	}

	public ActionListener spawn() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				world.bearRandom(50);
				worldPanel.repaint();
			}
		};
	}

	public ActionListener killAll() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int x = 0; x < world.getWorldWidth(); x++) {
					for (int y = 0; y < world.getWorldHeight(); y++) {
						world.getInhabitants()[x][y].kill();
					}
				}
				worldPanel.repaint();
			}
		};
	}

	public ActionListener play(JButton btn_next, JButton btn_togglePlay) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				world.setRunning(!world.isRunning());
				btn_next.setEnabled(!world.isRunning());
				btn_togglePlay.setText(world.isRunning() ? "Pause" : "  Play  ");
			}
		};
	}

	public ActionListener next() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				world.setNext(true);
				world.setLastSimTime(System.currentTimeMillis());
				world.setSleepTime(0);
			}
		};
	}

	public AdjustmentListener simSpeed(JLabel lbl_simspeed) {
		return new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				Settings.simTime = 1000 - (arg0.getValue() * 10);
				lbl_simspeed.setText("    Simulation Speed " + Settings.simTime);
			}
		};
	}
}