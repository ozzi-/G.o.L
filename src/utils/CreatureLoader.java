package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import simulation.Creature;

public class CreatureLoader {
	public static ArrayList<Creature> loadCreatures(){
		ArrayList<Creature> cl = new ArrayList<Creature>();
		try {
			List<String> creatures = getResourceFiles("creatures");
			for (String creature : creatures) {
				if(creature.endsWith(".json")){
					InputStream resource = getResourceAsStream("creatures/" + creature);
					cl.add(loadCreature(inputStreamToString(resource)));					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cl;
	}

	private static Creature loadCreature(String json){
		JSONObject job = new JSONObject(json);
		JSONArray forms = job.getJSONArray("form");
		Creature creature = new Creature(job.get("name").toString());
	    creature.getName();
		for (Object form : forms) {
	        JSONArray formArray = (JSONArray) form;
	        int x = (int)formArray.get(0);
	        int y = (int)formArray.get(1);
	        creature.addCell(x,y);
	    }
		return creature;
	}

	private static String inputStreamToString(InputStream is) {
		Scanner sc = new Scanner(is);
		Scanner scd = sc.useDelimiter("\\A");
		String result = scd.hasNext() ? scd.next() : "";
		sc.close();
		scd.close();
		return result;
	}

	private static List<String> getResourceFiles(String path) throws IOException {
		List<String> filenames = new ArrayList<>();

		try (InputStream in = getResourceAsStream(path);
				BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			String resource;

			while ((resource = br.readLine()) != null) {
				filenames.add(resource);
			}
		}

		return filenames;
	}

	private static InputStream getResourceAsStream(String resource) {
		final InputStream in = getContextClassLoader().getResourceAsStream(resource);
		return in;
	}

	private static ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
}
