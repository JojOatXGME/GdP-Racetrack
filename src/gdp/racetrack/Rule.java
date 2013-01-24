package gdp.racetrack;

public abstract class Rule {

	private final EnvironmentCollisionRule environmentCollisionRule;
	private final PlayerCollisionRule playerCollisionRule;
	private final TurnRule turnRule;
	private final VictoryRule victoryRule;

	public Rule(EnvironmentCollisionRule envCollisionRule, PlayerCollisionRule playerCollisionRule, TurnRule turnRule, VictoryRule rule) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Check whether the given turn would be allowed or not.
	 * <br>
	 * 
	 * 
	 * @param player
	 * @param position
	 * @return
	 */
	public boolean isTurnAllowed(Player player, Vec2D position) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public Vec2D[] getAllowedTurns(Player player) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public Player getWinner() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
