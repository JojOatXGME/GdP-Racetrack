package gdp.racetrack;

public interface TurnRule {

	public void init(Game game);

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
	public Point[] getAllowedTurns(Player player);

	/**
	 * Gets all allowed turns which a player
	 * with the given position and velocity can do.
	 * @param position The players position
	 * @param velocity The players velocity
	 * @return A Array of Points where the player turn to
	 */
	public Point[] getAllowedTurns(Point position, Vec2D velocity);

}
