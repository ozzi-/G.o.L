package start;

import java.awt.EventQueue;
import java.util.ArrayList;

import simulation.Creature;
import simulation.Settings;
import simulation.Simulation;
import simulation.World;
import utils.CreatureLoader;
import utils.GUI;

public class GoL {
	private static World world;
	private static int countx;
	private static int county;
	@SuppressWarnings("unused")
	private static GoL window;
	private static long sleepTime = 0;
	private static long lastSimTime = 0;
	private static ArrayList<Creature> creatureList;

	public GoL() {
		countx  = (int) (Settings.winx / Settings.lwidth) - 1;
		county =  (int) (Settings.winy / Settings.lheight) - 1;
		world = new World(countx, county);
		
		creatureList = CreatureLoader.loadCreatures();
		
	}

	public static void main(String[] args) throws InterruptedException {
		window = new GoL();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI.initialize(world, countx, county, creatureList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		while (true) {
			long executionSimTime=0;
			long preSimTime = System.currentTimeMillis();
			if (world.isRunning() && preSimTime > lastSimTime + sleepTime) {
				lastSimTime = System.currentTimeMillis();
				GUI.paint();
				Simulation.simulate(world);
				executionSimTime = System.currentTimeMillis() - preSimTime;
			}
			sleepTime = Settings.simTime - executionSimTime;
			sleepTime = sleepTime < 1 ? 1 : sleepTime;
			Thread.sleep(20);
		}
	}
}
