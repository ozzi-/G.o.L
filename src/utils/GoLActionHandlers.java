package utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import simulation.Cell;
import simulation.Creature;
import simulation.Settings;
import simulation.Simulation;
import simulation.WolPanel;
import simulation.World;

public class GoLActionHandlers {
	private World world;
	private WolPanel worldPanel;
	private Creature pointerCreature;

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
	
	public ActionListener spawnCreature(Creature creature) {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pointerCreature = creature;
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
				world.setLastSimTime(System.currentTimeMillis());
				world.setSleepTime(0);
				Simulation.simulate(world);
				GUI.paint();
			}
		};
	}
	
	public MouseListener mouseListener(){
		return new MouseListener() {
			
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
					if(pointerCreature==null){
						world.bear(e.getX() / Settings.lwidth, e.getY() / Settings.lheight);
					}else{
						world.bearCreature(pointerCreature, e.getX() / Settings.lwidth, e.getY() / Settings.lheight);						
					}
				} else if (SwingUtilities.isRightMouseButton(e)) {
					Cell dyingCell = world.getInhabitants()[e.getX() / Settings.lwidth][e.getY() / Settings.lheight];
					dyingCell.kill();
				}
				GUI.paint();			
			}
		};
	}

	public AdjustmentListener simSpeed(JLabel lbl_simspeed) {
		return new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				Settings.simTime = 1000 - (arg0.getValue() * 11);
				lbl_simspeed.setText("    Simulation Speed " + Settings.simTime);
			}
		};
	}
}