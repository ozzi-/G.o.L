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
	private static long sleepSimTime = 0;
	private static long lastSimTime = 0;
	
	public GoL() {
	    System.setProperty("sun.java2d.opengl", "true");
		IO.init();
		Settings.loadFromJSON();
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
		
		long initialTime = System.nanoTime();
		final double timeF = 1000000000 / Settings.FPS;
		double deltaF = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		while (true) {
			long currentTime = System.nanoTime();
			deltaF += (currentTime - initialTime) / timeF;
			initialTime = currentTime;

			
			long executionSimTime=0;
			long preSimTime = System.currentTimeMillis();
			if (world.isRunning() && preSimTime > lastSimTime + sleepSimTime) {
				lastSimTime = System.currentTimeMillis();
				Simulation.simulate(world);
				executionSimTime = System.currentTimeMillis() - preSimTime;
			}
			
			if (deltaF >= 1) {
				GUI.paint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				GUI.setTitleFPS(frames);
				frames = 0;
				timer += 1000;
			}

			sleepSimTime = Settings.simTime - executionSimTime;
			sleepSimTime = sleepSimTime < 1 ? 1 : sleepSimTime;
			
			Thread.sleep(1);
		}
	}
}
