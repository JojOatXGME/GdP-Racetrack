package gdp.racetrack;

public class RuleSet {

	private final EnvironmentCollisionRule envCollisionRule;
	private final PlayerCollisionRule playerCollisionRule;
	private final TurnRule turnRule;
	private final VictoryRule victoryRule;

	public RuleSet(EnvironmentCollisionRule envCollisionRule, PlayerCollisionRule playerCollisionRule, TurnRule turnRule, VictoryRule victoryRule) {
		this.envCollisionRule = envCollisionRule;
		this.playerCollisionRule = playerCollisionRule;
		this.turnRule = turnRule;
		this.victoryRule = victoryRule;
	}

	/**
	 * Check whether the given turn would be allowed or not.
	 * @param player The player which would do the turn
	 * @param destination The destination of the requested turn
	 * @return true if the turn would be allowed or false otherwise
	 */
	public Turn getTurnResult(Player player, Point destination) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Check whether the given turn would be allowed ot not.
	 * @param position The players position
	 * @param velocity The players velocity
	 * @param destination The destination of the requested turn
	 * @return true if the turn would be allowed or false otherwise
	 */
	public Turn getTurnResult(Point start, Point destination) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Gets all allowed turns which the given player can do.
	 * @param player The Player to be checked for
	 * @return A array of possible turns
	 */
	public Turn[] getAllowedTurns(Player player) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Gets all allowed turns which a player with the given position and velocity can do.
	 * @param position The Players position
	 * @param velocity The players velocity
	 * @return A array possible turns
	 */
	public Turn[] getAllowedTurns(Point position, Vec2D velocity) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Gets the winner of the game or returns null if there is no winner yet.
	 * @return The winner of the game or null, if there is no winner yet
	 */
	public Player getWinner() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
