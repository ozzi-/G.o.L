package simulation;

import java.util.ArrayList;

public class Creature {

	private String name;
	private ArrayList<Cell> cells = new ArrayList<>();
	
	public Creature(String name) {
		this.name = name;
	}

	public void addCell(int x, int y) {
		cells.add(new Cell(x, y));
	}
	
	public ArrayList<Cell> getCells() {
		return cells;
	}

	public String getName() {
		return name;
	}
}
