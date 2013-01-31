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
	
	public Turn(Point newPosition, Vec2D newVelocity, TurnType turnType){
		this.newPosition = newPosition;
		this.newVelocity = newVelocity;
		this.turnType = turnType;
	}
	
	public Point getnewPosition () {
		return  newPosition;
	}
	
	public Vec2D getnewVelocity () {
		return newVelocity;
	}
	
	public TurnType getturnType () {
		return turnType;
	}
	
	private final Point newPosition;
	private final Vec2D newVelocity;
	private final TurnType turnType;

}
