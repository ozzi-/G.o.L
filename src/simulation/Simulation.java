package simulation;

public class Simulation {

	public static void simulate(World world) {

		for (int x = 0; x < world.getWorldWidth(); x++) {
			for (int y = 0; y < world.getWorldHeight(); y++) {

				Cell c = world.getInhabitants()[x][y];
				int aliveNeighbours = 0;

				Cell n = world.getBound(x, y - 1);
				Cell e = world.getBound(x + 1, y);
				Cell s = world.getBound(x, y + 1);
				Cell w = world.getBound(x - 1, y);
				Cell ne = world.getBound(x + 1, y - 1);
				Cell nw = world.getBound(x - 1, y - 1);
				Cell sw = world.getBound(x - 1, y + 1);
				Cell se = world.getBound(x + 1, y + 1);

				aliveNeighbours += nw.isAlive() ? 1 : 0;
				aliveNeighbours += n.isAlive() ? 1 : 0;
				aliveNeighbours += ne.isAlive() ? 1 : 0;
				aliveNeighbours += w.isAlive() ? 1 : 0;
				aliveNeighbours += e.isAlive() ? 1 : 0;
				aliveNeighbours += s.isAlive() ? 1 : 0;
				aliveNeighbours += sw.isAlive() ? 1 : 0;
				aliveNeighbours += se.isAlive() ? 1 : 0;

				if (c.isAlive()) {
					if (!(aliveNeighbours == 2 || aliveNeighbours == 3)) {
						c.killNextRound();
					} else {
						c.resurrectNextRound();
					}
				} else {
					if (aliveNeighbours == 3) {
						c.resurrectNextRound();
					}
				}

			}
		}
		
	}

}
