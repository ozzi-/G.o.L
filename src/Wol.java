import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

public class Wol {
	private JFrame frame;
	private static World world;
	private int countx;
	private int county;
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
		long sleepTime = 0;
		long lastSimTime = 0;
		while (true) {
			long preSimTime = System.currentTimeMillis();
			if ((running || next) && preSimTime > lastSimTime + sleepTime) {
				next = false;
				lastSimTime = System.currentTimeMillis();
				System.out.println("TICK------------------------");
				worldCanvas.repaint();
				
				for (int x = 0; x < world.getWorldWidth(); x++) {
					for (int y = 0; y < world.getWorldHeight(); y++) {
						
						Cell c = world.inhabitants[x][y];
						int aliveNeighbours = 0 ;

						Cell n = world.getBound(x,y-1);
						Cell e = world.getBound(x-1,y);
						Cell s = world.getBound(x,y+1);
						Cell w = world.getBound(x+1,y);

						Cell ne = world.getBound(x-1,y-1);
						Cell nw = world.getBound(x+1,y-1);
						
						Cell sw = world.getBound(x+1,y+1);
						Cell se = world.getBound(x-1,y+1);
						
						aliveNeighbours+=nw.isAlive()?1:0;
						aliveNeighbours+=n.isAlive()?1:0;
						aliveNeighbours+=ne.isAlive()?1:0;
						aliveNeighbours+=w.isAlive()?1:0;
						aliveNeighbours+=e.isAlive()?1:0;
						aliveNeighbours+=s.isAlive()?1:0;
						aliveNeighbours+=sw.isAlive()?1:0;
						aliveNeighbours+=se.isAlive()?1:0;
						
					
						if(!c.isAlive() && aliveNeighbours==3){
							c.resurrect();
						}else if(c.isAlive() && aliveNeighbours<2){
							c.kill();
						}else if(c.isAlive() && (aliveNeighbours == 2 || aliveNeighbours == 3)){
							c.resurrect();
						}else if (c.isAlive() && aliveNeighbours > 3){
							c.kill();
						}
					}
				}
				
			
			}
			long afterSimTime = System.currentTimeMillis();
			long executionSimTime = afterSimTime - preSimTime;
			sleepTime = Settings.simTime - executionSimTime;
			sleepTime = sleepTime < 1 ? 1 : sleepTime;
			Thread.sleep(20);
		}

	}

	public Wol() {

		countx = (int) (Settings.winx / Settings.lwidth) - 1;
		county = (int) (Settings.winy / Settings.lheight) - 1;

		world = new World(countx, county);
		initialize();

	}

	private void initialize() {
		frame = new JFrame();
		frame.setSize(Settings.winx, Settings.winy);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		worldCanvas = new WolCanvas(world, Settings.winx, Settings.winy, countx, county);
		frame.getContentPane().add(worldCanvas, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);

		JButton btn_spawn = new JButton("Spawn");
		btn_spawn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				world.bearRandom(50);
				worldCanvas.repaint();
			}
		});
		panel.add(btn_spawn);

		JButton btn_kill = new JButton("Kill All");
		btn_kill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int x = 0; x < world.getWorldWidth(); x++) {
					for (int y = 0; y < world.getWorldHeight(); y++) {
						world.inhabitants[x][y].kill();
					}
				}
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
