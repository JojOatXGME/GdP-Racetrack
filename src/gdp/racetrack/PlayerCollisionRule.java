package gdp.racetrack;

public interface PlayerCollisionRule {

	public void init(Game game);

	/**
	 * Handles the collision of two players which the given properties.
	 * @param actor PlayerInfo of the Player which has caused the collision
	 * @param victim PlayerInfo of the object
	 */
	public void handleCollision(PlayerInfo actor, PlayerInfo victim);

}
