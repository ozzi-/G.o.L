package simulation;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Creature {

	private static final String FORM = "form";
	private static final String NAME = "name";
	
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

	public String toJSON() {
		JsonObject jo = new JsonObject();
		jo.addProperty(NAME, name);
		JsonArray ja = new JsonArray();
		for (Cell cell : cells) {
			JsonArray cella = new JsonArray();
			cella.add(cell.getPosx());
			cella.add(cell.getPosy());
			ja.add(cella);
		}
		jo.add(FORM, ja);
		return jo.toString();
	}
}
