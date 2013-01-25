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
		return map;
	}

	/**
	 * Gets the game rule of this game.
	 * @return The game rule of this game
	 */
	public RuleCombination getRule() {
		return rule;
	}

	/**
	 * Gets the specific player.
	 * @param index The index of the player you wand to have
	 * @return The specific player
	 */
	public Player getPlayer(int index) {
		return players[index];
	}

	/**
	 * Gets the players which play this game.
	 * @return The players which play this game
	 */
	public Player[] getPlayers() {
		return players;
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
	 * @throws IllegalStateException if the game have already finished
	 */
	public void pause() {
		synchronized(this) {
			if (state == State.FINISHED)
				throw new IllegalStateException("The game can't pasue if it is finished");
			
			state = State.PAUSED;
		}
	}

	/**
	 * Start or continue the game.
	 * <br>
	 * This method will run and block your sequence until the game is finished or paused.
	 * @throws IllegalStateException if the game is already running or finished
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
