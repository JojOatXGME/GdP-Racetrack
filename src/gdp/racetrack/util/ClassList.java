package gdp.racetrack.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassList {

	private final List<ClassDescription> classList;

	public ClassList(InputStream input, ClassLoader classLoader)
			throws ClassListException, IOException {
		
		this(new InputStreamReader(input), classLoader);
	}

	public ClassList(Reader input, ClassLoader classLoader)
			throws ClassListException, IOException {
		
		if (input == null)
			throw new IllegalArgumentException("Input stream can not be null");
		if (classLoader == null)
			classLoader = getClass().getClassLoader();
		
		classList = Collections.unmodifiableList(parse(input, classLoader));
	}

	public List<ClassDescription> getList() {
		return classList;
	}

	public Object createInstance(int index, Object... initargs)
			throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		
		return classList.get(index).createInstance(initargs);
	}

	public static class ClassDescription {
		public final String name;
		public final String description;
		public final Class<?> clazz;
	
		private ClassDescription(final String name,
				final String description, final Class<?> clazz) {
			
			this.name = name;
			this.description = description;
			this.clazz = clazz;
		}
	
		public Object createInstance(Object... initargs)
				throws NoSuchMethodException, SecurityException,
				InstantiationException, IllegalAccessException,
				IllegalArgumentException, InvocationTargetException {
			
			ArrayList<Class<?>> parameterTypes = new ArrayList<Class<?>>();
			for (Object arg : initargs) {
				parameterTypes.add(arg.getClass());
			}
			
			Constructor<?> c = clazz.getConstructor(parameterTypes.toArray(new Class<?>[0]));
			return c.newInstance(initargs);
		}
	}

	private static List<ClassDescription> parse(Reader input,
			ClassLoader classLoader) throws ClassListException, IOException {
		
		ArrayList<ClassDescription> list = new ArrayList<ClassDescription>();
		BufferedReader reader = new BufferedReader(input);
		
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
				ClassDescription desc;
				try {
					desc = new ClassDescription(
							name,
							descBuilder.toString(),
							classLoader.loadClass(clazz));
				} catch (ClassNotFoundException e) {
					throw new ClassListException("There is no class "+clazz, e);
				}
				list.add(desc);
			} else {
				if (descBuilder.length() > 0) {
					descBuilder.append(' ');
				}
				descBuilder.append(line);
			}
		}
			
		return list;
	}

	// --- --- TEST UNIT --- ---

	public static void main(String[] args) throws Exception {
		String source =
				"4\n" +
				"--- gdp.racetrack.util.ClassList Util Class\n" +
				"This should be the first class in the list.\n" +
				"It is the main class of this tool.\n" +
				".\n" +
				"--- gdp.racetrack.util.ClassListException Exception\n" +
				"Here is the second class.\n" +
				"It is the exception which is throwen when the list is not valid.\n" +
				".\n" +
				"! ! This should never be printed ! !\n" +
				"--- java.lang.String The String class\n" +
				"When all classes was printed, the test will try to create a new instance of this element.\n" +
				".\n" +
				"--- java.lang.Integer The last class\n" +
				"This should be the last element of the list\n" +
				".";
		
		Reader reader = new java.io.StringReader(source);
		ClassList list = new ClassList(reader, null);
		
		for (ClassDescription desc : list.getList()) {
			System.out.println("name: "+desc.name);
			System.out.println("class: "+desc.clazz.getName());
			System.out.println(desc.description);
			System.out.println("----------------");
		}
		
		Object string = list.createInstance(2, "The String was successfully created");
		System.out.println(string);
	}

}
