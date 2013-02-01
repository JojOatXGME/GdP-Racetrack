package gdp.racetrack;

public class Turn {

	public enum TurnType {
		OK,
		FORBIDDEN,
		FINISH,
		FINISH_WIN,
		FINISH_LOOSE,
		COLLISION_PLAYER,
		COLLISION_ENVIRONMENT,
	}

	public Turn(Point newPosition, Vec2D newVelocity, TurnType turnType, Player affectedPlayer, PlayerInfo affectedPlayerInfo) {
		this.newPosition = newPosition;
		this.newVelocity = newVelocity;
		this.turnType = turnType;
		
		this.affectedPlayer = affectedPlayer;
		this.affectedPlayerInfo = affectedPlayerInfo;
	}

	public Turn(TurnType turnType){
		this.turnType = turnType;
	}
	
	public Turn(Point newPosition, TurnType turnType){
		this.newPosition = newPosition;
		this.turnType = turnType;
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
	 * @return the turnType
	 */
	public TurnType getTurnType() {
		return turnType;
	}
	/**
	 * @param turnType the turnType to set
	 */
	public void setTurnType(TurnType turnType) {
		this.turnType = turnType;
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

	private Point newPosition = null;
	private Vec2D newVelocity = null;
	private TurnType turnType = null;

	private Player affectedPlayer = null;
	private PlayerInfo affectedPlayerInfo = null;

}
