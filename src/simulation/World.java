package simulation;

import java.util.concurrent.ThreadLocalRandom;

public class World {
	private int worldWidth;
	private int worldHeight;
	private Cell[][] inhabitants;

	public World(int worldWidth, int worldHeight) {
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
		inhabitants = new Cell[worldWidth][worldHeight];
		for (int x = 0; x < worldWidth; x++) {
			for (int y = 0; y < worldHeight; y++) {
				inhabitants[x][y] = new Cell(x, y);
			}
		}
		// bearRandom(10);

		bearTub(6, 5);
		bearBlinker(20, 20);

		bearGlider(10, 8);

		bearGlider(15, 8);

	}

	private void bearGlider(int x, int y) {
		inhabitants[getBoundX(x + 1)][getBoundY(y)].resurrect();
		inhabitants[getBoundX(x + 2)][getBoundY(y + 1)].resurrect();
		inhabitants[getBoundX(x + 2)][getBoundY(y + 2)].resurrect();
		inhabitants[getBoundX(x)][getBoundY(y + 2)].resurrect();
		inhabitants[getBoundX(x + 1)][getBoundY(y + 2)].resurrect();
	}

	private void bearBlinker(int x, int y) {
		inhabitants[getBoundX(x)][getBoundY(y)].resurrect();
		inhabitants[getBoundX(x)][getBoundY(y + 1)].resurrect();
		inhabitants[getBoundX(x)][getBoundY(y + 2)].resurrect();
	}

	private void bearTub(int x, int y) {
		inhabitants[getBoundX(x - 1)][getBoundY(y)].resurrect();
		inhabitants[getBoundX(x)][getBoundY(y - 1)].resurrect();
		inhabitants[getBoundX(x)][getBoundY(y + 1)].resurrect();
		inhabitants[getBoundX(x + 1)][getBoundY(y)].resurrect();
	}

	public void bear(int posx, int posy) {
		inhabitants[posx][posy].resurrect();
	}

	public int getWorldWidth() {
		return worldWidth;
	}

	public int getWorldHeight() {
		return worldHeight;
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

	public void setWorldHeight(int worldHeight) {
		this.worldHeight = worldHeight;
	}

	public void setWorldWidth(int worldWidth) {
		this.worldWidth = worldWidth;
	}

	public Cell[][] getInhabitants() {
		return inhabitants;
	}

}
