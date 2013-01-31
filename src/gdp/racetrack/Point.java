package gdp.racetrack;

public class Point {
	/**
	 * @deprecated It is better to use {@link #getX()}
	 */
	public final int x;
	/**
	 * @deprecated It is better to use {@link #getY()}
	 */
	public final int y;

	private final Vec2D vec;
//	private final Map map;

	/**
	 * Creates a Point which represent the given position (x,y) on the given map.
	 * @param x The x-coordinate of the point
	 * @param y The y-coordinate of the point
	 * @param map The Map on which is the point
	 */
	public Point(final int x, final int y /*, final Map map */) {
		this(new Vec2D(x,y)/*, map*/);
	}

	/**
	 * Creates a Point which represent the given position on the given map.
	 * @param vec The Vector from (0,0) to this point
	 * @param map The Map on which is the point
	 */
	public Point(final Vec2D vec /*, final Map map */) {
		this.vec = vec;
//		this.map = map;
		
		this.x = vec.x;
		this.y = vec.y;
	}

	/**
	 * Gets the x-coordinate of the point.
	 * It represents the horizontal position. 0 is the left side.
	 * @return The x-coordinate of the point
	 */
	public int getX() {
		return vec.x;
	}

	/**
	 * Gets the y-coordinate of the point.
	 * It represents the vertical position. 0 is the bottom.
	 * @return The y-coordinate of the point
	 */
	public int getY() {
		return vec.y;
	}

	/**
	 * Gets the vector which represent the line from (0,0) to this point.
	 * @return The vector from (0,0) to this point
	 */
	public Vec2D getVec() {
		return vec;
	}

//	/**
//	 * Gets the Map in which the point is set.
//	 * @return The Map of this point
//	 */
//	public Map getMap() {
//		return map;
//	}

	public Point add(Vec2D summand) {
		return new Point(vec.add(summand) /*, map */);
	}

	public Point sub(Vec2D subtrahend) {
		return new Point(vec.sub(subtrahend) /*, map */);
	}

	public Point mul(int factor) {
		return new Point(vec.mul(factor) /*, map */);
	}
//
//	/**
//	 * Check whether the point is a part of the track or not.
//	 * <br>
//	 * This method use {@link Map#isTrack(Point)}.
//	 * 
//	 * @return true if the point is part of the track, false otherwise
//	 */
//	public boolean isTrack() {
//		return map.isTrack(this);
//	}
//
//	/**
//	 * Gets the Type of the point on the map.
//	 * <br>
//	 * This method use {@link Map#getPointType(Point)} to gets the type.
//	 * 
//	 * @return The Type of the point
//	 */
//	public PointType getType() {
//		return map.getPointType(this);
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vec == null) ? 0 : vec.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (vec == null) {
			if (other.vec != null)
				return false;
		} else if (!vec.equals(other.vec))
			return false;
		return true;
	}

}
