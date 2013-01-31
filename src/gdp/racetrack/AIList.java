package gdp.racetrack;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import gdp.racetrack.util.ClassList;
import gdp.racetrack.util.ClassList.ClassDescription;
import gdp.racetrack.util.ClassListException;

public class AIList {

	private static final ClassList classList;

	static {
		try {
			classList = new ClassList(new FileReader("data/ai.list"), AIList.class.getClassLoader());
		} catch (ClassListException | IOException e) {
			throw new RuntimeException("Error occured by loading ai.list");
		}
	}

	public static List<ClassDescription> getList() {
		return classList.getList();
	}

	public static AI createKI(ClassDescription desc) {
		try {
			Object instance = desc.createInstance();
			return (AI) instance;
		} catch (Exception e) {
			throw new RuntimeException("Error occured by crating an instance of AI " +
					desc.name + " ("+desc.clazz.getSimpleName()+")", e);
		}
	}

}
