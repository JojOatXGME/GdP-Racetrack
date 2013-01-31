package gdp.racetrack;

public interface TurnRule {

	/**
	 * Check whether the given turn would be allowed or not.
	 * @param player The player which would do the turn
	 * @param destination The destination of the requested turn
	 * @return true if the turn would be allowed or false otherwise
	 */
	public boolean isTurnAllowed(Player player, Point destination);

	/**
	 * Check whether the given Turn would be allowed for a player
	 * on the given position with the given velocity.
	 * @param start The players start position
	 * @param velocity The players velocity
	 * @param destination The destination of the turn
	 * @return true if the turn would be allowed, false otherwise
	 */
	public boolean isTurnAllowed(Point start, Vec2D velocity, Point destination);

	/**
	 * Gets all allowed turns which the given player can do.
	 * @param player The Player to be checked for
	 * @return A array of points where the player can move to
	 */
	public Vec2D[] getAllowedTurns(Player player);

}
