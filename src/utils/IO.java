package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class IO {
	public final static String userHomeFolder = System.getProperty("user.home")+File.separator+"GoL";
	
	public static void init() {
		File userHomeFolderF = new File(userHomeFolder);
		if (!userHomeFolderF.exists()){
			userHomeFolderF.mkdirs();
		}		
	}
	
	public static String readFileToString(String filePath) {
		StringBuilder contentBuilder = new StringBuilder();

		try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return contentBuilder.toString();
	}
	
	public static InputStream getResourceAsStream(String resource) {
		final InputStream in = getContextClassLoader().getResourceAsStream(resource);
		return in;
	}

	public static ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
	
	public static String inputStreamToString(InputStream is) {
		Scanner sc = new Scanner(is);
		Scanner scd = sc.useDelimiter("\\A");
		String result = scd.hasNext() ? scd.next() : "";
		sc.close();
		scd.close();
		return result;
	}

}
