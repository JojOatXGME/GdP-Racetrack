package gdp.racetrack;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BotLoader {

	private static final List<BotDescription> botList;

	static {
		ArrayList<BotDescription> list = new ArrayList<>();
		
		try {
			InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("bot.list");
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			
			int n = Integer.parseInt(reader.readLine());
			
			String name = null;
			String clazz = null;
			StringBuilder descBuilder = new StringBuilder();
			while (list.size() < n) {
				String line = reader.readLine();
				if (line.startsWith("---")) {
					descBuilder = new StringBuilder();
					
					String[] header = line.substring(3).trim().split("\\s", 2);
					clazz = header[0];
					name = header[1].trim();
				} else if (line.trim().equals(".")) {
					list.add(new BotDescription(
							name,
							(Class<? extends Player>) BotLoader.class.getClassLoader().loadClass(clazz),
							descBuilder.toString()
							));
				} else if (line.length() != 0) {
					descBuilder.append(line);
				}
			}
			
		} catch (Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			} else {
				throw new RuntimeException("Error occurred while loading bot.list", e);
			}
		}
		botList = Collections.unmodifiableList(list);
	}

	public static class BotDescription {
		public final String name;
		public final Class<? extends Player> clazz;
		public final String description;
		
		private BotDescription(String name, Class<? extends Player> clazz, String description) {
			this.name = name;
			this.clazz = clazz;
			this.description = description;
		}
	}

	public static List<BotDescription> getBots() {
		return botList;
	}

	public static Player getBot(int index) {
		Class<? extends Player> clazz = botList.get(index).clazz;
		try {
			return clazz.getConstructor().newInstance();
		} catch (Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			} else {
				throw new RuntimeException(e);
			}
		}
	}

}
