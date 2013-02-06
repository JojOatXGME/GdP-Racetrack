package gdp.racetrack;

/**
 * Completed turn which can not changed anymore.
 */
public class IrrevocableTurn {
	private final Point startPosition;
	private final Point endPosition;
	private final Vec2D velocity;
	private final boolean crossedFinishLine;
	private final boolean collideWithPlayer;
	private final boolean collideWithEnv;
	private final boolean changedPathValid;
	private final boolean wasPathValid;
	
	private final Player affectedPlayer;
	private final Vec2D affectedPlayerVelocity;

	IrrevocableTurn(Point startPosition, Point endPosition,
			Vec2D velocity, boolean crossedFinishLine,
			boolean collideWithPlayer, boolean collideWithEnv,
			boolean changedPathValid, boolean wasPathValid,
			Player affectedPlayer, Vec2D affectedPlayerVelocity) {
		
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.velocity = velocity;
		this.crossedFinishLine = crossedFinishLine;
		this.collideWithPlayer = collideWithPlayer;
		this.collideWithEnv = collideWithEnv;
		this.changedPathValid = changedPathValid;
		this.wasPathValid = wasPathValid;
		
		this.affectedPlayer = affectedPlayer;
		this.affectedPlayerVelocity = affectedPlayerVelocity;
	}

	/**
	 * Gets the position for the move of the turn.
	 * @return Position for the move
	 */
	public Point getStartPosition() {
		return startPosition;
	}

	/**
	 * Gets the position after the move was done.
	 * @return Position after the move
	 */
	public Point getEndPosition() {
		return endPosition;
	}

	/**
	 * Gets the velocity of the player at the end of move.
	 * @return Velocity of the player
	 */
	public Vec2D getVelocity() {
		return velocity;
	}

	/**
	 * Whether the player has crossed the finish line in the move or not.
	 * @return true if the player crossed the finish line, false otherwise
	 */
	public boolean hasCrossedFinishLine() {
		return crossedFinishLine;
	}

	/**
	 * Gets whether the player has collide with something or not.
	 * @return true if the player has collide, false otherwise
	 */
	public boolean hasCollide() {
		return (collideWithPlayer || collideWithEnv);
	}

	/**
	 * Gets whether the player has collide another player or not.
	 * @return true if the player has collide another player, false otherwise
	 */
	public boolean hasCollideWithPlayer() {
		return collideWithPlayer;
	}

	/**
	 * Gets whether the player has collide the border of map or not.
	 * @return true if the player has collide the border, false otherwise
	 */
	public boolean hasCollideWithEnv() {
		return collideWithEnv;
	}

	/**
	 * Gets whether the turn has changed the valid state of the player's path.
	 * @return true if the state has changed, false otherwise
	 */
	public boolean changedPathValidState() {
		return changedPathValid;
	}

	/**
	 * Gets whether the player's path was valid after the turn.
	 * @return true if the path was valid, false otherwise
	 */
	public boolean wasPathValid() {
		return wasPathValid;
	}

	/**
	 * Gets also affected player of this turn.
	 * @return Player which was affected or null, if there was no other player
	 */
	public Player getAffectedPlayer() {
		return affectedPlayer;
	}

	/**
	 * Gets the velocity of the affected player if there is one.
	 * @return Velocity of affected player or null of there was no other player
	 */
	public Vec2D getAffectedPlayerVelocity() {
		return affectedPlayerVelocity;
	}

}
