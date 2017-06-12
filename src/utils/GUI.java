package utils;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import simulation.Settings;
import simulation.WolPanel;
import simulation.World;

public class GUI {
	private static JFrame frame;
	private static WolPanel worldPanel;

	public static void initialize(World world, int countx, int county) {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setTitle("G.o.L");
		frame.setLayout(new BorderLayout());
		frame.setSize(Settings.winx, Settings.winy);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				Settings.winx = worldPanel.getWidth();
				Settings.winy = worldPanel.getHeight();
				Settings.lwidth = Settings.winx / Settings.creatureScale;
				Settings.lheight = Settings.winx / Settings.creatureScale;
			}
		});
		
		worldPanel = new WolPanel(world, Settings.winx, Settings.winy, countx, county);
		GoLActionHandlers gbl = new GoLActionHandlers(world, worldPanel);
		
		
		frame.getContentPane().add(worldPanel, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);

		JButton btn_spawn = new JButton("Spawn");
		btn_spawn.addActionListener(gbl.spawn());
		panel.add(btn_spawn);

		JButton btn_kill = new JButton("Kill All");
		btn_kill.addActionListener(gbl.killAll());
		panel.add(btn_kill);
		JButton btn_next = new JButton("Next");

		JButton btn_togglePlay = new JButton("  Play  ");
		btn_togglePlay.addActionListener(gbl.play(btn_next,btn_togglePlay));
		panel.add(btn_togglePlay);

		btn_next.addActionListener(gbl.next());
		panel.add(btn_next);

		JLabel lbl_simspeed = new JLabel("    Simulation Speed " + Settings.simTime);

		JScrollBar scb_simspeed = new JScrollBar();
		scb_simspeed.setUnitIncrement(5);
		scb_simspeed.setOrientation(JScrollBar.HORIZONTAL);
		scb_simspeed.addAdjustmentListener(gbl.simSpeed(lbl_simspeed));

		panel.add(lbl_simspeed);
		panel.add(scb_simspeed);

		frame.pack();

	}
}
