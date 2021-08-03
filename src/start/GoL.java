package start;

import java.awt.EventQueue;

import simulation.Settings;
import simulation.Simulation;
import simulation.World;
import utils.CreatureLoader;
import utils.GUI;
import utils.IO;

public class GoL {
	private static World world;
	private static int countx;
	private static int county;
	@SuppressWarnings("unused")
	private static GoL window;
	private static long sleepTime = 0;
	private static long lastSimTime = 0;

	public GoL() {
		IO.init();
		world = new World(Settings.cellsX,Settings.cellsY);
		CreatureLoader.loadCreatures();
	}
	

	public static void main(String[] args) throws InterruptedException {
		window = new GoL();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI.initialize(world, countx, county, CreatureLoader.getCreatureList());
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
