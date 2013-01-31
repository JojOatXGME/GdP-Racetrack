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
	
	public Point getNewPosition () {
		return  newPosition;
	}
	
	public Vec2D getNewVelocity () {
		return newVelocity;
	}
	
	public TurnType getTurnType () {
		return turnType;
	}
	
	private final Point newPosition;
	private final Vec2D newVelocity;
	private final TurnType turnType;

	private final Player affectedPlayer;
	private final PlayerInfo affectedPlayerInfo;

}
