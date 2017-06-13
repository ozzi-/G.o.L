package utils;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import simulation.Creature;
import simulation.Settings;
import simulation.WolPanel;
import simulation.World;

public class GUI {
	private static JFrame frame;
	private static WolPanel worldPanel;


	public static void initialize(World world, int countx, int county, ArrayList<Creature> creatureList) {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setTitle("G.o.L");
		frame.getContentPane().setLayout(new BorderLayout());
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
		GoLActionHandlers gah = new GoLActionHandlers(world, worldPanel);
		worldPanel.setGah(gah);
		
		frame.getContentPane().add(worldPanel, BorderLayout.CENTER);
		
		JPanel creaturePanel = new JPanel();
		creaturePanel.setLayout(new BoxLayout(creaturePanel, BoxLayout.PAGE_AXIS));
		
        JScrollPane scrollPane = new JScrollPane (creaturePanel, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		frame.getContentPane().add(scrollPane, BorderLayout.EAST);        
        
		// TODO controls to turn creatures 
		for (Creature creature : creatureList) {
			JButton btn_test = new JButton(creature.getName());
			btn_test.addActionListener(gah.spawnCreature(creature));
			creaturePanel.add(btn_test);		
		}
		
		JPanel controlPanel = new JPanel();
		frame.getContentPane().add(controlPanel, BorderLayout.SOUTH);

		JButton btn_spawn = new JButton("Spawn");
		btn_spawn.addActionListener(gah.spawn());
		controlPanel.add(btn_spawn);

		JButton btn_kill = new JButton("Kill All");
		btn_kill.addActionListener(gah.killAll());
		controlPanel.add(btn_kill);
		JButton btn_next = new JButton("Next");

		JButton btn_togglePlay = new JButton("  Play  ");
		btn_togglePlay.addActionListener(gah.play(btn_next, btn_togglePlay));
		controlPanel.add(btn_togglePlay);

		btn_next.addActionListener(gah.next());
		controlPanel.add(btn_next);

		JLabel lbl_simspeed = new JLabel("    Simulation Speed " + Settings.simTime);

		JScrollBar scb_simspeed = new JScrollBar();
		scb_simspeed.setUnitIncrement(5);
		scb_simspeed.setOrientation(JScrollBar.HORIZONTAL);
		scb_simspeed.addAdjustmentListener(gah.simSpeed(lbl_simspeed));

		controlPanel.add(lbl_simspeed);
		controlPanel.add(scb_simspeed);

		frame.pack();
	}

	public static void paint() {
		worldPanel.repaint();
	}


}
