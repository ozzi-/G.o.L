package utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
	private static JPanel creaturePanel;
	private static GoLActionHandlers gah;
	private static ArrayList<JButton> creatureButtons = new ArrayList<JButton>();

	public static JFrame getFrame() {
		return frame;
	}
	
	public static void setTitleFPS(int fps) {
		frame.setTitle("G.o.L - FPS: "+fps);
	}
	
	public static void initialize(World world, int countx, int county, ArrayList<Creature> creatureList) {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setTitle("G.o.L");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setSize(Settings.winX, Settings.winY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setMinimumSize(new Dimension(600,400));
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				Settings.winX = worldPanel.getWidth();
				Settings.winY = worldPanel.getHeight();
				int creatureScaleX = Settings.winX/Settings.cellsX;
				int creatureScaleY = Settings.winY/Settings.cellsY;
				Settings.cellScale = creatureScaleX>creatureScaleY?creatureScaleY:creatureScaleX;
				Settings.cellScale = Settings.cellScale<1?1:Settings.cellScale;
			}
		});

		worldPanel = new WolPanel(world, Settings.winX, Settings.winY, countx, county);
		gah = new GoLActionHandlers(world, worldPanel);
		worldPanel.setGah(gah);
		
		frame.getContentPane().add(worldPanel, BorderLayout.CENTER);
		
		
		creaturePanel = new JPanel();
		creaturePanel.setLayout(new BoxLayout(creaturePanel, BoxLayout.PAGE_AXIS));
		
        JScrollPane scrollPane = new JScrollPane (creaturePanel, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		frame.getContentPane().add(scrollPane, BorderLayout.EAST);
        
		JButton btn_download = new JButton("Download new");
		btn_download.addActionListener(gah.downloadCreature());
		creaturePanel.add(btn_download);
		
		// TODO controls to turn creatures 
		// TODO render to place creature in different color under current mouseX/Y
		for (Creature creature : creatureList) {
			JButton creatureSpawnBtn = new JButton(creature.getName());
			creatureSpawnBtn.addActionListener(gah.spawnCreature(creature,creatureSpawnBtn));
			creaturePanel.add(creatureSpawnBtn);
			creatureButtons.add(creatureSpawnBtn);
		}
		
		JPanel controlPanel = new JPanel();
		frame.getContentPane().add(controlPanel, BorderLayout.SOUTH);

		JButton btn_spawn = new JButton("Spawn Random");
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
		
		JButton btn_save = new JButton("  Save  ");
		btn_save.addActionListener(gah.save());
		controlPanel.add(btn_save);

		JButton btn_load = new JButton("  Load  ");
		btn_load.addActionListener(gah.load());
		controlPanel.add(btn_load);
		
        JCheckBox chkbtn_grid = new JCheckBox("  Grid  ");
        chkbtn_grid.addActionListener(gah.toggleGrid());
        controlPanel.add(chkbtn_grid);
        
        JRadioButton infiniteButton = new JRadioButton("Infinite");
        infiniteButton.setSelected(true);
        JRadioButton wrapButton = new JRadioButton("Wrap");
        JRadioButton boxedButton = new JRadioButton("Boxed");
        
        ButtonGroup group = new ButtonGroup();
        group.add(infiniteButton);
        group.add(wrapButton);
        group.add(boxedButton);
        controlPanel.add(infiniteButton);
        controlPanel.add(wrapButton);
        controlPanel.add(boxedButton);
        infiniteButton.addActionListener(gah.worldType(WorldType.INFINITE));
        wrapButton.addActionListener(gah.worldType(WorldType.WRAPED));
        boxedButton.addActionListener(gah.worldType(WorldType.BOXED));

		JLabel lbl_simspeed = new JLabel("    Simulation Speed " + Settings.simTime);

		JScrollBar scb_simspeed = new JScrollBar();
		scb_simspeed.setUnitIncrement(5);
		scb_simspeed.setOrientation(JScrollBar.HORIZONTAL);
		scb_simspeed.addAdjustmentListener(gah.simSpeed(lbl_simspeed));

		controlPanel.add(lbl_simspeed);
		controlPanel.add(scb_simspeed);
		
		JButton btn_settings = new JButton("\u2699");
		btn_settings.addActionListener(gah.settings());
		controlPanel.add(btn_settings);

		frame.pack();
	}


	public static void paint() {
		if (worldPanel!=null) {
			worldPanel.repaint();			
		}
	}

	public static void clearCreatureButtonBackgrounds() {
		for (JButton jButton : creatureButtons) {
			jButton.setBackground(null);
		}
	}

	public static void addCreatureButton(Creature creature) {
		JButton btn_test = new JButton(creature.getName());
		btn_test.addActionListener(gah.spawnCreature(creature,btn_test));
		
		GUI.clearCreatureButtonBackgrounds();
		btn_test.setBackground(Color.GREEN);
		
		creaturePanel.add(btn_test);
		creatureButtons.add(btn_test);
		frame.pack();
	}
}
