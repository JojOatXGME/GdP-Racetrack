package gdp.racetrack;

public class Vec2D {

	/**
	 * The first dimension of the vector. It represent the horizontal component.
	 * A negative value means the vector goes left and a positive value means it goes right.
	 */
	public final int x;

	/**
	 * The second dimension of the vector. It represent the vertical component.
	 * A negative value means the vector goes down and a positive value means it goes up.
	 */
	public final int y;


	/**
	 * Creates a new Vec2D with the given values.
	 * <br>
	 * The first dimension x represent the horizontal component and
	 * the second dimension y represent the vertical component.
	 * 
	 * @param x The first dimension of the vector. See {@link #x}
	 * @param y The second dimension of the vector. See {@link #y}
	 */
	public Vec2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Adds up the vector with the given vector and return the sum.
	 * @param summand The vector to adds up with
	 * @return The sum of the addition
	 */
	public Vec2D add(Vec2D summand) {
		return new Vec2D(this.x + summand.x, this.y + summand.y);
	}

	/**
	 * Subtract the given vector from this vector and return the difference.
	 * @param subtrahend The subtrahend to subtract from this vector
	 * @return The difference of the subtraction
	 */
	public Vec2D sub(Vec2D subtrahend) {
		return new Vec2D(this.x - subtrahend.x, this.y - subtrahend.y);
	}

	/**
	 * Multiply the vector with the given integer and returns the product.
	 * @param factor The factor to multiply with
	 * @return The product of the multiplication
	 */
	public Vec2D mul(int factor) {
		return new Vec2D(factor * this.x, factor * this.y);
	}

	/**
	 * Negate the vector and return the result.
	 * <br>
	 * The vector [x,y] returns the vector [-x,-y].
	 * @return The inverse vector
	 */
	public Vec2D negate() {
		return new Vec2D(-this.x, -this.y);
	}

}
