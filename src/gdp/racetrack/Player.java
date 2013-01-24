package gdp.racetrack;

import java.util.LinkedList;
import java.util.List;

public abstract class Player {

	private final LinkedList<Vec2D> turnHistory = new LinkedList<Vec2D>();

	/**
	 * Does the turn of the player and return the "ortsvector" for the destination.
	 * @return The destination point of this turn
	 */
	public abstract Vec2D turn();

	/**
	 * Gets the actual position of this player as Vec2D from bottom left corner.
	 * @return The position of the player
	 */
	public final Vec2D getPosition() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Gets the "velocity" of the move in last turn of the player.
	 * @return The velocity of the last move
	 */
	public final Vec2D getVelocity() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Gets a history of the movements of the player.
	 * Return points or velocities?
	 * @return History of movements
	 */
	public final List<Vec2D> getTurnHistory() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Sets the position of this player.
	 * @param position The new position of the player
	 */
	public final void setPosition(Vec2D position) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
