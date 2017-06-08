package wol;

public class Cell {
	private int posx;
	private int posy;
	private boolean alive;

	public Cell(int posx, int posy) {
		this.setPosx(posx);
		this.setPosy(posy);
		alive = true;
	}

	public int getPosx() {
		return posx;
	}

	public void setPosx(int posx) {
		this.posx = posx;
	}

	public int getPosy() {
		return posy;
	}

	public void setPosy(int posy) {
		this.posy = posy;
	}

	public boolean isAlive() {
		return alive;
	}

	public void resurrect() {
		this.alive = true;
	}

	public void kill() {
		this.alive = false;
	}
}
