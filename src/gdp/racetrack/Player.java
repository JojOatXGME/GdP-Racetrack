package gdp.racetrack;

import java.util.LinkedList;
import java.util.List;

public abstract class Player {
	private Game game = null;

	// --- --- Initialization part --- ---

	public Player() {
		// do nothing
	}

	protected final void init(Game game) {
		if (game != null)
			throw new IllegalStateException("The player is already initialized");
		
		this.game = game;
		
		onLoad();
	}

	// --- --- Methods to override --- ---

	/**
	 * Elaborate the next turn of the player and return its destination.
	 * @return The destination point of the next turn
	 */
	public abstract Point turn();

	protected void onLoad() {
		// do nothing
	}

	protected void onUpdatePosition(Point oldPos, Point newPos) {
		// do nothing
	}

	protected void onUpdateVelocity(Vec2D oldVelocity, Vec2D newVelocity) {
		// do nothing
	}

	// --- --- Player logic --- ---

	private final LinkedList<Vec2D> turnHistory = new LinkedList<Vec2D>();

	private Point position = null;
	private Vec2D velocity = null;

	public final Game getGame() {
		if (game == null)
			throw new IllegalStateException("The player is not initialized yet");
		
		return game;
	}

	/**
	 * Gets the actual position of the player as Vec2D from bottom left corner.
	 * @return The position of the player
	 */
	public final Point getPosition() {
		return position;
	}

	/**
	 * Gets the current "velocity" of the player.
	 * It is normally the way which the player moved in the last round.
	 * @return The velocity of the last move
	 */
	public final Vec2D getVelocity() {
		return velocity;
	}

	/**
	 * Gets a history of positions of the player.
	 * @return History of positions
	 */
	public final List<Vec2D> getTurnHistory() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Sets the position of this player.
	 * @param position The new position of the player
	 */
	public final void setPosition(Point position) {
		Point oldPos = this.position;
		this.position = position;
		onUpdatePosition(oldPos, position);
	}

	/**
	 * Sets the velocity of this player.
	 * @param velocity The new velocity of the player
	 */
	public final void setVelocity(Vec2D velocity) {
		Vec2D oldVelocity = this.velocity;
		this.velocity = velocity;
		onUpdateVelocity(oldVelocity, velocity);
	}

}
