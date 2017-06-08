package wol;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JLabel;

public class Wol {
	private JFrame frame;
	private World world;
	private static WolCanvas worldCanvas;
	private static boolean running = false;
	private static boolean next = false;
	private static Wol window;

	public static void main(String[] args) throws InterruptedException {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					window = new Wol();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		while (true) {
			long preSimTime = System.currentTimeMillis();
			if (running || next) {
				next = false;
				System.out.println("TICK");
				// DO THE SIM
				//worldCanvas.update(g);
			}
			long afterSimTime = System.currentTimeMillis();
			long executionSimTime = afterSimTime - preSimTime;
			long sleepTime = Settings.simTime - executionSimTime;
			sleepTime = sleepTime < 1 ? 1 : sleepTime;
			Thread.sleep(sleepTime);
		}

	}

	public Wol() {
		world = new World(Settings.winx, Settings.winy);
		initialize();

	}

	private void initialize() {
		frame = new JFrame();
		frame.setSize(Settings.winx, Settings.winy);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		int countx = (int) (Settings.winx / Settings.lwidth) - 1;
		int county = (int) (Settings.winy / Settings.lheight) - 1;

		worldCanvas = new WolCanvas(world, Settings.winx, Settings.winy, countx, county);
		frame.getContentPane().add(worldCanvas, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);

		JButton btn_spawn = new JButton("Spawn");
		btn_spawn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				world.bearRandom(50);
			}
		});
		panel.add(btn_spawn);

		JButton btn_kill = new JButton("Kill All");
		btn_kill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				world.inhabitants.clear();
			}
		});
		panel.add(btn_kill);
		JButton btn_next = new JButton("Next");

		JButton btn_togglePlay = new JButton("  Play  ");
		btn_togglePlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				running = !running;
				btn_next.setEnabled(!running);
				btn_togglePlay.setText(running ? "Pause" : "  Play  ");
			}
		});
		panel.add(btn_togglePlay);

		btn_next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				next = true;
			}
		});
		panel.add(btn_next);

		JLabel lbl_simspeed = new JLabel("    Simulation Speed " + Settings.simTime);

		JScrollBar scb_simspeed = new JScrollBar();
		scb_simspeed.setUnitIncrement(5);
		scb_simspeed.setOrientation(JScrollBar.HORIZONTAL);
		scb_simspeed.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				Settings.simTime = 1000 - (scb_simspeed.getValue() * 10);
				lbl_simspeed.setText("    Simulation Speed " + Settings.simTime);
			}
		});

		panel.add(lbl_simspeed);
		panel.add(scb_simspeed);

		frame.pack();

	}

}
