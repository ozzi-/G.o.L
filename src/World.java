package wol;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class World {
	public ArrayList<Cell> inhabitants;
	private int worldWidth;
	private int worldHeight;

	public World(int worldWidth, int worldHeight) {
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;		
		inhabitants = new ArrayList<Cell>();
		bearRandom(100);
	}

	public void bear(int posx, int posy) {
		inhabitants.add(new Cell(posx, posy));
	}
	
	public void bearRandom(int count){
		System.out.println("creating "+count+" new cells");
		for (int i = 0; i < count; i++) {
			int x = ThreadLocalRandom.current().nextInt(0, worldWidth);
			int y = ThreadLocalRandom.current().nextInt(0, worldHeight);
			bear(x, y);
		}
	}
	
	
}
