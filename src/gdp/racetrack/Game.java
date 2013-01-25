package gdp.racetrack;

public class Game {

	enum State {
		RUNNING,
		PAUSED,
		FINISHED
	}

	private final Map map;
	private final Player[] players;
	private final RuleCombination rule;

	private State state = State.PAUSED;

	/**
	 * Creates a new game.
	 * 
	 * @param map The used map for the game
	 * @param players The players which play the game
	 * @param rule The used rules of the game
	 */
	public Game(Map map, Player[] players, RuleCombination rule) {
		this.map = map;
		this.players = players;
		this.rule = rule;
	}

	/**
	 * Gets the map of this game.
	 * @return The map of this game
	 */
	public Map getMap() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Gets the game rule of this game.
	 * @return The game rule of this game
	 */
	public RuleCombination getRule() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Gets the players which play this game.
	 * @return The players which play this game
	 */
	public Player[] getPlayers() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Gets the current state of the game.
	 * There are 3 possibilities. The game is running, paused or finished.
	 * @return The state of the game
	 */
	public State getState() {
		return state;
	}

	/**
	 * Pause the game.
	 * <br>
	 * It will end the run-Method in the next time.
	 */
	public void pause() {
		synchronized(this) {
			if (state == State.FINISHED)
				throw new IllegalStateException("The game can't pasue if it is finished");
			
			state = state.PAUSED;
		}
	}

	/**
	 * Start or continue the game.
	 * <br>
	 * This method will run and block your sequence until the game is finished or paused.
	 */
	public void run() {
		if (state == State.RUNNING)
			throw new IllegalStateException("The game is already running");
		if (state == State.FINISHED)
			throw new IllegalStateException("The game have finished and there is nothing to do");
		
		while (state == State.RUNNING) {
			
		}
	}

}
