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
					Creature loadedCreature = loadCreature(inputStreamToString(resource));
					if(loadedCreature!=null){
						cl.add(loadedCreature);
					}else{
						System.out.println("could not load creature "+creature);						
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cl;
	}

	private static Creature loadCreature(String json){
		try {
			JSONObject job = new JSONObject(json);
			Creature creature = new Creature(job.get("name").toString());
		    creature.getName();
		    JSONArray formArr = job.getJSONArray("form");
			for (Object form : formArr) {
		        JSONArray formCoordinates = (JSONArray) form;
		        int x = (Integer) formCoordinates.get(0);
		        int y = (Integer) formCoordinates.get(1);
		        creature.addCell(x,y);
		    }
			return creature;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
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
