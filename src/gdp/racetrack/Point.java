package gdp.racetrack;

import gdp.racetrack.Map.PointType;

public class Point {
	@Deprecated public final int x;
	@Deprecated public final int y;

	private final Vec2D vec;
	private final Map map;

	public Point(int x, int y, Map map) {
		this(new Vec2D(x,y), map);
	}

	public Point(Vec2D vec, Map map) {
		this.vec = vec;
		this.map = map;
		
		this.x = vec.x;
		this.y = vec.y;
	}

	public int getX() {
		return vec.x;
	}

	public int getY() {
		return vec.y;
	}

	public Vec2D getVec() {
		return vec;
	}

	public Point add(Vec2D summand) {
		return new Point(vec.add(summand), map);
	}

	public Point sub(Vec2D subtrahend) {
		return new Point(vec.sub(subtrahend), map);
	}

	public Point mul(int factor) {
		return new Point(vec.mul(factor), map);
	}

	/**
	 * Check whether the point is a part of the track or not.
	 * <br>
	 * This method use Map.isTrack(Point).
	 * 
	 * @return true if the point is part of the track, false otherwise
	 */
	public boolean isTrack() {
		return map.isTrack(this);
	}

	/**
	 * Gets the Type of the point on the map.
	 * <br>
	 * This method use Map.getPointType(Point) to gets the type.
	 * 
	 * @return The Type of the point
	 */
	public PointType getType() {
		return map.getPointType(this);
	}

}
