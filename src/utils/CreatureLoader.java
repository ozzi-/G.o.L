package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import simulation.Creature;

public class CreatureLoader {
	public CreatureLoader() {
		try {
			List<String> creatures = getResourceFiles("creatures");
			for (String creature : creatures) {
				InputStream a = getResourceAsStream("creatures/" + creature);
				System.out.println(inputStreamToString(a));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Creature loadCreature(String json){
		return null;
	}

	private String inputStreamToString(InputStream is) {
		Scanner sc = new Scanner(is);
		Scanner scd = sc.useDelimiter("\\A");
		String result = scd.hasNext() ? scd.next() : "";
		sc.close();
		scd.close();
		return result;
	}

	private List<String> getResourceFiles(String path) throws IOException {
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

	private InputStream getResourceAsStream(String resource) {
		final InputStream in = getContextClassLoader().getResourceAsStream(resource);
		return in == null ? getClass().getResourceAsStream(resource) : in;
	}

	private ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
}
