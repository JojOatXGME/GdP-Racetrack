package gdp.racetrack;

import gdp.racetrack.Map.PointType;

public class Point {
	private final Vec2D vec;
	private final Map map;

	public Point(int x, int y, Map map) {
		this(new Vec2D(x,y), map);
	}

	public Point(Vec2D vec, Map map) {
		this.vec = vec;
		this.map = map;
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

	public boolean isTrack() {
		return map.isTrack(this);
	}

	public PointType getType() {
		return map.getPointType(this);
	}

}
