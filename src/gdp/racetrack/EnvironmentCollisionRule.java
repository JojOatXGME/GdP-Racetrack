package gdp.racetrack;

public interface EnvironmentCollisionRule {

	public void init(Game game);

	/**
	 * Handle the behavior of an player which have the given properties.
	 * @param player PlayerInfo to handle behavior.
	 */
	public void handleCollision(Turn turn);

}
