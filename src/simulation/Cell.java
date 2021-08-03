package simulation;

public class Cell {
	private int posx;
	private int posy;
	private boolean alive;
	private boolean aliveNextRound;

	public Cell(int posx, int posy) {
		this.setPosx(posx);
		this.setPosy(posy);
		alive = false;
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
		alive = true;
	}

	public void kill() {
		alive = false;
		aliveNextRound = false;
	}

	public void killNextRound() {
		aliveNextRound = false;
	}

	public void resurrectNextRound() {
		aliveNextRound = true;
	}

	public void liveRound() {
		alive = aliveNextRound;
	}

}
