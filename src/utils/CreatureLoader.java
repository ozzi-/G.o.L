package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import simulation.Creature;

public class CreatureLoader {
	
	private static ArrayList<Creature> creatureList = new ArrayList<Creature>();

	public static ArrayList<Creature> getCreatureList(){
		return creatureList;
	}
	
	public static void loadCreatures() {
		try {
			List<String> creatures = getResourceFiles("creatures");
			for (String creature : creatures) {
				if (creature.endsWith(".json")) {
					InputStream resource = IO.getResourceAsStream("creatures/" + creature);
					Creature loadedCreature = loadCreature(IO.inputStreamToString(resource));
					if (loadedCreature != null) {
						creatureList.add(loadedCreature);
					} else {
						System.out.println("could not load creature " + creature);
					}
				}
			}
			Files.list(new File(IO.userHomeFolder).toPath()).forEach(path -> {
				if (path != null && path.toString().endsWith(".creature")) {
					Creature loadedCreature = loadCreature(IO.readFileToString(path.toString()));
					creatureList.add(loadedCreature);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Creature loadCreature(String jsonString) {
		try {
			JsonObject job = JsonParser.parseString(jsonString).getAsJsonObject();
			Creature creature = new Creature(job.get("name").getAsString());
			System.out.println("Loading creature '" + creature.getName() + "'");
			creature.getName();
			JsonArray formArr = job.get("form").getAsJsonArray();
			for (JsonElement formCoordinates : formArr) {
				int x = (Integer) formCoordinates.getAsJsonArray().get(0).getAsInt();
				int y = (Integer) formCoordinates.getAsJsonArray().get(1).getAsInt();
				creature.addCell(x, y);
			}
			return creature;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	private static List<String> getResourceFiles(String path) throws IOException {
		List<String> filenames = new ArrayList<>();

		try (InputStream in = IO.getResourceAsStream(path);
				BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			String resource;

			while ((resource = br.readLine()) != null) {
				filenames.add(resource);
			}
		}

		return filenames;
	}

	public static Creature loadFromCellsSyntax(String res, String creatureName) {
		String nameMarker = "!Name:";
		char dotChar = '.';

		Creature creature = null;
		String[] lines = res.split(System.lineSeparator());
		int y = 0;
		for (String line : lines) {
			int x = 0;
			if (line.startsWith("!")) {
				if (line.startsWith(nameMarker)) {
					creature = new Creature(line.substring(nameMarker.length() + 1));
				}
			} else {
				if (creature == null) {
					creature = new Creature(creatureName);
				}
				for (char cur : line.toCharArray()) {
					x++;
					if (cur != dotChar) {
						creature.addCell(x, y);

					}
				}
				y++;
			}
		}
		return creature;
	}

}
