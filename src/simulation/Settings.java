package simulation;

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import utils.IO;

public class Settings {
	
	static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	static int desktopWidth = gd.getDisplayMode().getWidth();
	static int desktopHeight = gd.getDisplayMode().getHeight();

	/* Canvas Resolution */
	public static int winX = desktopWidth/2;
	public static int winY = desktopHeight/2;

	/* Default Simulation Speed in ms */
	public static long defaultSimTime = 1000;

	/* Defines creature size and thus the grid dimensions ('level' size) */
	public static int cellSize = 3;
	public static int cellsX = winX/cellSize;
	public static int cellsY = winY/cellSize;
	
	public static boolean drawGrid = false;

	/* Cosmetics */
	public static Color backgroundColor = new Color(210, 210, 210);
	public static Color cellColor = new Color(0, 0, 0);

	/* Don't change */
	public static long simTime = defaultSimTime;
	
	private final static int creatureScaleX = winX/cellsX;
	private final static int creatureScaleY = winY/cellsY;
	public static int cellScale = creatureScaleX>creatureScaleY?creatureScaleY:creatureScaleX;
	
	public static String settingsPath = IO.userHomeFolder+File.separator+"config.json";
	public static int FPS = 60;
	
	public static String toJSON() {
		JsonObject settingsJO = new JsonObject();
		settingsJO.addProperty("cellSize", cellSize);
		settingsJO.addProperty("winX", winX);
		settingsJO.addProperty("winY", winY);
		settingsJO.addProperty("FPS", FPS);
		return settingsJO.toString();
	}
	
	public static void loadFromJSON() {
		File settingsFile = new File(settingsPath);
		if(!settingsFile.exists()) {
			IO.writeToFile(settingsFile, Settings.toJSON());
		}
		String settingsJson = IO.readFileToString(settingsPath);
		JsonObject settingsJO = JsonParser.parseString(settingsJson).getAsJsonObject();
		if(settingsJO.get("winX")!=null) {
			winX = settingsJO.get("winX").getAsInt();			
		}
		if(settingsJO.get("winY")!=null) {
			winY = settingsJO.get("winY").getAsInt();
		}
		if(settingsJO.get("FPS")!=null) {
			FPS = settingsJO.get("FPS").getAsInt();
		}
		if(settingsJO.get("cellSize")!=null) {
			setCellSize(settingsJO.get("cellSize").getAsInt());
		}
	}
	
	public static void setCellSize(int cellSize) {
		Settings.cellSize = cellSize;
		cellsX = winX/cellSize;
		cellsY = winY/cellSize;
	}
}
