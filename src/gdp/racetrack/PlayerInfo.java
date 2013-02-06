package gdp.racetrack;

public class PlayerInfo {

	private final Point lastPos;

	private Point newPos;
	private Vec2D velocity;
	private boolean pathValid;

	public PlayerInfo(Point newPos, Point lastPos, Vec2D velocity, boolean pathValid) {
		this.newPos = newPos;
		this.lastPos = lastPos;
		this.velocity = velocity;
		this.pathValid = pathValid;
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

	public boolean isPathValid() {
		return pathValid;
	}

	public void setPosition(Point newPos) {
		this.newPos = newPos;
	}

	public void setVelocity(Vec2D newVelocity) {
		this.velocity = newVelocity;
	}

	public void setPathValid(boolean valid) {
		pathValid = valid;
	}

}
