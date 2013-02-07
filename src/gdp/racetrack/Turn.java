package gdp.racetrack;

public class Turn {

	/*
	public enum TurnType {
		OK,
		FORBIDDEN,
		FINISH,
		FINISH_WIN,
		FINISH_LOOSE,
		COLLISION_PLAYER,
		COLLISION_ENVIRONMENT,
	}
	*/

	public Turn(Player player, Point destination) {
		this.oldPosition = player.getPosition();
		this.oldVelocity = player.getVelocity();
		
		this.newPosition = destination;
		this.newVelocity = newPosition.getVec().sub(oldPosition.getVec());
		this.isPathValid = player.isPathValid();
		
		this.crossFinishLine = false;
	}

	public Point getOldPosition() {
		return oldPosition;
	}

	public Vec2D getOldVelocity() {
		return oldVelocity;
	}

	/**
	 * @return the newPosition
	 */
	public Point getNewPosition() {
		return newPosition;
	}

	/**
	 * @param newPosition the newPosition to set
	 */
	public void setNewPosition(Point newPosition) {
		this.newPosition = newPosition;
	}

	/**
	 * @return the newVelocity
	 */
	public Vec2D getNewVelocity() {
		return newVelocity;
	}

	/**
	 * @param newVelocity the newVelocity to set
	 */
	public void setNewVelocity(Vec2D newVelocity) {
		this.newVelocity = newVelocity;
	}

	/**
	 * @return the affectedPlayer
	 */
	public Player getAffectedPlayer() {
		return affectedPlayer;
	}

	/**
	 * @param affectedPlayer the affectedPlayer to set
	 */
	public void setAffectedPlayer(Player affectedPlayer) {
		this.affectedPlayer = affectedPlayer;
	}

	public boolean isPathValid() {
		return isPathValid;
	}

	public void setPathValid(boolean isPathValid) {
		this.isPathValid = isPathValid;
	}

	public boolean crossFinishLine() {
		return crossFinishLine;
	}

	public void setCrossFinishLine(boolean crossFinishLine) {
		this.crossFinishLine = crossFinishLine;
	}

	public boolean collidePlayer() {
		return collidePlayer;
	}

	public void setCollidePlayer(boolean collidePlayer) {
		this.collidePlayer = collidePlayer;
	}

	public boolean collideEnv() {
		return collideEnv;
	}

	public void setCollideEnv(boolean collideEnv) {
		this.collideEnv = collideEnv;
	}

	/**
	 * @return the affectedPlayerInfo
	 */
	public PlayerInfo getAffectedPlayerInfo() {
		return affectedPlayerInfo;
	}

	/**
	 * @param affectedPlayerInfo the affectedPlayerInfo to set
	 */
	public void setAffectedPlayerInfo(PlayerInfo affectedPlayerInfo) {
		this.affectedPlayerInfo = affectedPlayerInfo;
	}

	private final Point oldPosition;
	private final Vec2D oldVelocity;

	private Point newPosition;
	private Vec2D newVelocity;
	private boolean isPathValid;
	
	private boolean crossFinishLine;
	private boolean collidePlayer;
	private boolean collideEnv;

	private Player affectedPlayer = null;
	private PlayerInfo affectedPlayerInfo = null;

}
