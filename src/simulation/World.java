package simulation;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class World {
	private int worldWidth;
	private int worldHeight;
	private Cell[][] inhabitants;
	private boolean running;
	private long lastSimTime;
	private long sleepTime;
	private boolean infinite;
	private Cell dead;
	
	public World(int cellsX, int cellsY) {
		this.worldWidth = cellsX;
		this.worldHeight = cellsY;
		dead = new Cell(1,1);
		dead.kill();
		
		infinite=true;
		inhabitants = new Cell[worldWidth][worldHeight];
		for (int x = 0; x < worldWidth; x++) {
			for (int y = 0; y < worldHeight; y++) {
				inhabitants[x][y] = new Cell(x, y);
			}
		}
	}

	public void bear(int posx, int posy) {
		inhabitants[getBoundX(posx)][getBoundY(posy)].resurrect();
	}

	public void bearRandom(int count) {
		for (int i = 0; i < count; i++) {
			int x = ThreadLocalRandom.current().nextInt(0, worldWidth);
			int y = ThreadLocalRandom.current().nextInt(0, worldHeight);
			bear(x, y);
		}
	}

	public int getBoundX(int x) {
		if (x >= getWorldWidth()) {
			x = 0;
		}
		if (x < 0) {
			x = getWorldWidth() - 1;
		}
		return x;
	}

	public int getBoundY(int y) {
		if (y >= getWorldHeight()) {
			y = 0;
		}
		if (y < 0) {
			y = getWorldHeight() - 1;
		}
		return y;
	}

	public Cell getBound(int x, int y) {
		if(!infinite){
			if(x >= getWorldWidth() || x < 0 || y >= getWorldHeight() || y < 0){
				return dead;
			}
		}
		if (x >= getWorldWidth()) {
			x = 0;
		}
		if (x < 0) {
			x = getWorldWidth() - 1;
		}
		if (y >= getWorldHeight()) {
			y = 0;
		}
		if (y < 0) {
			y = getWorldHeight() - 1;
		}
		return inhabitants[x][y];
	}
	
	public int getWorldWidth() {
		return worldWidth;
	}

	public int getWorldHeight() {
		return worldHeight;
	}

	public void setWorldHeight(int worldHeight) {
		this.worldHeight = worldHeight;
	}

	public void setWorldWidth(int worldWidth) {
		this.worldWidth = worldWidth;
	}

	public Cell[][] getInhabitants() {
		return inhabitants;
	}

	public long getLastSimTime() {
		return lastSimTime;
	}

	public void setLastSimTime(long lastSimTime) {
		this.lastSimTime = lastSimTime;
	}

	public long getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void bearCreature(Creature creature, int x, int y) {
		ArrayList<Cell> cells = creature.getCells();
		for (Cell cell : cells) {
			bear(getBoundX(cell.getPosx()+x), getBoundY(cell.getPosy()+y));
		}
	}

	public void killAll() {
		for (int x = 0; x < getWorldWidth(); x++) {
			for (int y = 0; y < getWorldHeight(); y++) {
				inhabitants[x][y].kill();
			}
		}		
	}


	public void toggleInfinite() {
		infinite=!infinite;
	}

}
