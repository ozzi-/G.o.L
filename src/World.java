import java.util.concurrent.ThreadLocalRandom;

public class World {
	private int worldWidth;
	private int worldHeight;
	Cell[][] inhabitants;

	public World(int worldWidth, int worldHeight) {
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;

		inhabitants = new Cell[worldWidth][worldHeight];
		for (int x = 0; x < worldWidth; x++) {
			for (int y = 0; y < worldWidth; y++) {
				inhabitants[x][y] = new Cell(x, y);
			}
		}
		// bearRandom(10);

		inhabitants[5][5].resurrect();
		inhabitants[6][4].resurrect();
		inhabitants[6][6].resurrect();
		inhabitants[7][5].resurrect();
		
		inhabitants[10][10].resurrect();
		inhabitants[11][10].resurrect();
		inhabitants[12][10].resurrect();
		inhabitants[12][9].resurrect();
		inhabitants[11][8].resurrect();
		

	}

	public void bear(int posx, int posy) {
		inhabitants[posx][posy].resurrect();
	}
	public int getWorldWidth(){
		return worldWidth;
	}
	public int getWorldHeight(){
		return worldHeight;
	}
	public void bearRandom(int count) {
		for (int i = 0; i < count; i++) {
			int x = ThreadLocalRandom.current().nextInt(0, worldWidth);
			int y = ThreadLocalRandom.current().nextInt(0, worldHeight);
			bear(x, y);
		}
	}

	public Cell getBound(int x, int y) {

		if(x>=getWorldWidth()){
			x=0;
		}
		if(x<0){
			x=getWorldWidth()-1;
		}
		if(y>=getWorldHeight()){
			y=0;
		}
		if(y<0){
			y=getWorldWidth()-1;
		}
		return inhabitants[x][y];
	}

}
