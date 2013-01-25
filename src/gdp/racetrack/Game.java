package gdp.racetrack;

public class Game {

	private final Map map;
	private final Player[] players;
	private final RuleCombination rule;

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

}
