package simulation;

public class Settings {
	/* Canvas Resolution */
	public static int winx = 900;
	public static int winy = 540;

	/* Default Simulation Speed in ms */
	public static long defaultSimTime = 1000;

	/* Defines creature size and thus the grid dimensions ('level' size) */
	public final static int creatureScale = 100;
	
	/* Dont change */
	public static long simTime = defaultSimTime;
	public static int lwidth = winx / creatureScale;
	public static int lheight = winx / creatureScale;

}
