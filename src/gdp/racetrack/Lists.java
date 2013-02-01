package gdp.racetrack;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import gdp.racetrack.util.ClassList;
import gdp.racetrack.util.ClassListException;

public final class Lists {

	public static final ImplementationList<AI> ai;
	
	public static final ImplementationList<TurnRule> turnRule;
	public static final ImplementationList<VictoryRule> victoryRule;
	public static final ImplementationList<PlayerCollisionRule> playerCollisionRule;
	public static final ImplementationList<EnvironmentCollisionRule> envCollisionRule;

	static {
		try {
			ai = new ImplementationList<AI>("data/ai.list", AI.class);
			
			turnRule = new ImplementationList<TurnRule>("data/rules/turn.list", TurnRule.class);
			victoryRule = new ImplementationList<VictoryRule>("data/rules/victory.list", VictoryRule.class);
			playerCollisionRule = new ImplementationList<PlayerCollisionRule>("data/rules/playercollision.list", PlayerCollisionRule.class);
			envCollisionRule = new ImplementationList<EnvironmentCollisionRule>("data/rules/envcollision.list", EnvironmentCollisionRule.class);
		} catch (ClassListException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static class ImplementationList<T> extends ClassList {
		private final Class<T> type;
	
		private ImplementationList(String file, Class<T> type)
				throws FileNotFoundException, ClassListException, IOException {
			
			super(new FileReader(file), Game.class.getClassLoader());
			
			this.type = type;
		}
	
		@SuppressWarnings("unchecked")
		@Override
		public T createInstance(int index, Object... initargs) {
			ClassDescription desc = getList().get(index);
			if (!type.isAssignableFrom(desc.clazz))
				throw new RuntimeException(type.getSimpleName() +
						" must be a superclass of " +
						desc.clazz.getSimpleName());
			try {
				Object instance = desc.createInstance(initargs);
				return (T) instance;
			} catch (Exception e) {
				throw new RuntimeException("Error occured by crating an instance of " +
						desc.name + " ("+desc.clazz.getSimpleName()+")", e);
			}
		}
	
	}

	private Lists() { } // make it impossible to create a instance of this class

}
