package net.mcavenue.redspigot.configuration;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import lombok.Getter;

public class ConfigManager<T> {

	@Getter
	private Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED)
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).registerTypeHierarchyAdapter(String.class, new PathAdapter())
			.registerTypeHierarchyAdapter(Class.class, new ClassAdapter()).setPrettyPrinting().create();
	private String fileName;
	private Class clazz;
	private T config;

	public ConfigManager(String fileName, Class clazz) {
		super();
		this.fileName = fileName;
		this.clazz = clazz;
	}

	public void init() {
		initConfig();
	}

	public void writeConfig() {
		try (FileWriter w = new FileWriter("config" + File.separator + fileName)) {
			getGson().toJson(config, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void initConfig() {
		System.out.println("INITIALIZING CONFIG: " + this.fileName);
		File f = new File("config");
		File confFile = new File("config" + File.separator + fileName);
		if (!f.exists())
			f.mkdir();
		if (!confFile.exists()) {
			URL inputUrl = getClass().getResource("/" + fileName);
			System.out.println(inputUrl);
			try {
				System.out.println("COPYING TO: " + new File("config/" + fileName));
				FileUtils.copyURLToFile(inputUrl, new File("config/" + fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try (FileReader reader = new FileReader("config" + File.separator + fileName)) {
			System.out.println("CLASS: " + clazz);
			T conf = (T) getGson().fromJson(reader, clazz);
			this.config = conf;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public T getConfig() {
		return config;
	}

	public void setConfig(T config) {
		this.config = config;
	}

	public static class ClassAdapter extends TypeAdapter<Class> {
		@Override
		public void write(JsonWriter jsonWriter, Class material) throws IOException {

		}

		@Override
		public Class<?> read(JsonReader jsonReader) throws IOException {
			String className = jsonReader.nextString();
			try {
				return Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public static class PathAdapter extends TypeAdapter<String> {

		@Override
		public String read(JsonReader arg0) throws IOException {
			String string = arg0.nextString();
			if (string.contains("http"))
				return string;
			return string.replace("//", File.separator).replace("\\", File.separator);
		}

		@Override
		public void write(JsonWriter arg0, String arg1) throws IOException {
			arg0.value(arg1);
		}

	}

}
