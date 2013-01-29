//Ich weiß nicht, wie ich eine neue Datei über das Web-Interface erstelle, also hier mein Vorschlag:

package vectorAce;

import java.util.LinkedList;
import java.util.List;
import java.awt.Point;

public abstract class Player {

	protected final LinkedList<Point> turnHistory = new LinkedList<Point>();

	/**
	 * Elaborate the next turn of the player and return its destination.
	 * 
	 * @return The destination point of the next turn
	 */
	public final Point turn() {
		Point turn = getTurn();
		turnHistory.add(turn);
		return turn;
	}

	protected abstract Point getTurn();

	/**
	 * Gets the actual position of the player as Vec2D from bottom left corner.
	 * 
	 * @return The position of the player
	 */
	public Point getPosition() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Gets the current "velocity" of the player. It is normally the way which
	 * the player moved in the last round.
	 * 
	 * @return The velocity of the last move
	 */
	public Point getVelocity() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Gets a history of the positions of the player.
	 * 
	 * @return History of movements
	 */
	public final List<Point> getTurnHistory() {
		return turnHistory;
	}

	/**
	 * Sets the position of this player.
	 * 
	 * @param position
	 *            The new position of the player
	 */
	public void setPosition(Point position) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Sets the velocity of this player.
	 * 
	 * @param velocity
	 *            The new velocity of the player
	 */
	public void setVelocity(Point velocity) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
