package ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {

	private static final String PROPERTIES_FILENAME = "fileRenamer.properties";
	private static final String DEFAULT_PATH = "C:/";

	public static String loadProperties(Properties properties) {
		String defaultPath = "";
		try {
			FileInputStream in = new FileInputStream(PROPERTIES_FILENAME);
			properties.load(in);
			in.close();
		} catch (IOException ioe) {
			return DEFAULT_PATH;
		}
		defaultPath = properties.getProperty("defaultPath", DEFAULT_PATH);
		return defaultPath;
	}

	public static void storeProperties(Properties properties, String path) {
		properties.put("defaultPath", path);
		try {
			FileOutputStream out = new FileOutputStream(PROPERTIES_FILENAME);
			properties.store(out, "Directory Default Path");
			out.close();
		} catch (IOException ioe) {
			return;
		}
	}

}
