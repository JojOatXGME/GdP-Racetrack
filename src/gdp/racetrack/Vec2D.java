package gdp.racetrack;

public class Vec2D {

	public final int x;
	public final int y;

	public Vec2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vec2D add(Vec2D vec) {
		return new Vec2D(this.x + vec.x, this.y + vec.y);
	}

	public Vec2D mul(int factor) {
		return new Vec2D(factor * this.x, factor * this.y);
	}

}
