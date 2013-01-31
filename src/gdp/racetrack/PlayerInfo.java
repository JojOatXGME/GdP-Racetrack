package gdp.racetrack;

public class PlayerInfo {

	private final Point lastPos;

	private Point newPos;
	private Vec2D velocity;

	public PlayerInfo(Point newPos, Point lastPos, Vec2D velocity) {
		this.newPos = newPos;
		this.lastPos = lastPos;
		this.velocity = velocity;
	}

	public Point getNewPos() {
		return newPos;
	}

	public Point getLastPos() {
		return lastPos;
	}

	public Vec2D getVelocity() {
		return velocity;
	}

	public void setPosition(Point newPos) {
		this.newPos = newPos;
	}

	public void setVelocity(Vec2D newVelocity) {
		this.velocity = newVelocity;
	}

}
