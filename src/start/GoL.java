package start;
import java.awt.EventQueue;

import javax.swing.JFrame;

import simulation.Settings;
import simulation.Simulation;
import simulation.WolPanel;
import simulation.World;
import utils.GUI;

public class GoL {
	private JFrame frame;
	private static World world;
	private int countx;
	private int county;
	private static WolPanel worldPanel;
	private static boolean running = false;
	private static boolean next = false;
	private static GoL window;
	private static long sleepTime = 0;
	private static long lastSimTime = 0;

	public GoL() {
		countx = (int) (Settings.winx / Settings.lwidth) - 1;
		county = (int) (Settings.winy / Settings.lheight) - 1;
		world = new World(countx, county);
		System.out.println(world.toString());
		GUI.initialize(world, countx, county);
	}


	public static void main(String[] args) throws InterruptedException {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					window = new GoL();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		while (true) {
			long preSimTime = System.currentTimeMillis();
			if ((running || next) && preSimTime > lastSimTime + sleepTime) {
				next = false;
				lastSimTime = System.currentTimeMillis();
				worldPanel.repaint();
				Simulation.simulate(world);			
			}
			long afterSimTime = System.currentTimeMillis();
			long executionSimTime = afterSimTime - preSimTime;
			sleepTime = Settings.simTime - executionSimTime;
			sleepTime = sleepTime < 1 ? 1 : sleepTime;
			Thread.sleep(20);
		}

	}



}
