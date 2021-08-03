package simulation;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import utils.WorldType;

public class World {
	private int worldWidth;
	private int worldHeight;
	private Cell[][] inhabitants;
	private boolean running;
	private long lastSimTime;
	private long sleepTime;
	private Cell dead;
	private WorldType worldType;
	public int visibleWorldStartX;
	public int visibleWorldStartY;
	public int visibleWorldEndX;
	public int visibleWorldEndY;

	public World(int cellsX, int cellsY) {
		this.worldWidth = cellsX*2;
		this.worldHeight = cellsY*2;
		this.visibleWorldStartX = this.worldWidth/4;
		this.visibleWorldStartY = this.worldHeight/4;
		this.visibleWorldEndX = visibleWorldStartX+this.worldWidth/2;
		this.visibleWorldEndY = visibleWorldStartY+this.worldHeight/2;
		
		dead = new Cell(1,1);
		dead.kill();
		
		worldType=WorldType.INFINITE;
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
	
	public WorldType getWorldType() {
		return worldType;
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
		if(worldType == WorldType.BOXED){
			if(x >= visibleWorldEndX || x < visibleWorldStartX || y >= visibleWorldEndY || y < visibleWorldStartY){
				return dead;
			}
		}
		
		if(worldType == WorldType.INFINITE) {
			if(x >= worldWidth || x < 0 || y >= worldHeight || y < 0){
				return dead;
			}
		}
		if(worldType == WorldType.WRAPED) {
			if (x >= visibleWorldEndX) {
				x = visibleWorldStartX;
			}
			if (x < visibleWorldStartX) {
				x = visibleWorldEndX - 1;
			}
			if (y >= visibleWorldEndY) {
				y = visibleWorldStartY;
			}
			if (y < visibleWorldStartY) {
				y = visibleWorldEndY - 1;
			}
		}else {
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

	public void setType(WorldType worldType) {
		this.worldType = worldType;
	}
}
