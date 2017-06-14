package simulation;

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class Settings {
	
	static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	static int desktopWidth = gd.getDisplayMode().getWidth();
	static int desktopHeight = gd.getDisplayMode().getHeight();

	/* Canvas Resolution */
	public static int winx = desktopWidth/2;
	public static int winy = desktopHeight/2;

	/* Default Simulation Speed in ms */
	public static long defaultSimTime = 1000;

	/* Defines creature size and thus the grid dimensions ('level' size) */
	public final static int cellsX = winx/5;
	public final static int cellsY = winy/5;

	/* Cosmetics */
	public static Color backgroundColor = new Color(210, 210, 210);
	public static Color cellColor = new Color(0, 0, 0);

	/* Don't change */
	public static long simTime = defaultSimTime;
	
	private final static int creatureScaleX = winx/cellsX;
	private final static int creatureScaleY = winy/cellsY;
	public static int creatureScale = creatureScaleX>creatureScaleY?creatureScaleY:creatureScaleX;


}
