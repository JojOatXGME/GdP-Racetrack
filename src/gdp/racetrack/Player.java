package gdp.racetrack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class Player {
	private Game game = null;
	private int playerNumber = -1;

	// --- --- Initialization part --- ---

	final void init(Game game, int playerNumber) {
		if (this.game != null)
			throw new IllegalStateException("The player is already initialized");
		
		this.game = game;
		this.playerNumber = playerNumber;
		
		Log.logger.fine("Initialization of " + this + " has finished, run onLoad event");
		
		onLoad();
		
		Log.logger.fine("onLoad event finished");
	}

	// --- --- Methods to override --- ---

	/**
	 * Elaborate the next turn of the player and return its destination.
	 * @return The destination point of the next turn
	 */
	protected abstract Point turn();

	/**
	 * Gets the point where the player wand to start.
	 * @param possiblePositions A list of possible positions to start
	 * @return The point where the player wand to start
	 */
	protected abstract Point chooseStart(List<Point> possiblePositions);

	/**
	 * Will be called when the Player has loaded.
	 * <br>
	 * The only sense of this method is to override it.
	 * {@link #getGame()} don't work before this method was called.
	 * You can use this method to initial the player.
	 */
	protected void onLoad() {
		// do nothing
	}

	/**
	 * Will be called when the position of the player has changed.
	 * <br>
	 * The only sense of this method is to override it.
	 * Use it to handle changes of the position.
	 * This method will be called after the changes was made.
	 * {@link #getPosition()} will already return the new position.
	 * The first call is when the player has choose its start position.
	 * oldPos will be null in this situation.
	 * 
	 * @param oldPos Position of the player before it has changed
	 * @param newPos Position of the player after it has changed
	 */
	protected void onUpdatePosition(Point oldPos, Point newPos) {
		// do nothing
	}

	/**
	 * Will be called when the velocity of the player has changed.
	 * <br>
	 * The only sense of this method is to override it.
	 * Use it to handle changes of the velocity.
	 * This method will be called after the changes was made.
	 * {@link #getVelocity()} will already return the new velocity.
	 * 
	 * @param oldVelocity Velocity of the player before it has changed
	 * @param newVelocity Velocity of the player after it has changed
	 */
	protected void onUpdateVelocity(Vec2D oldVelocity, Vec2D newVelocity) {
		// do nothing
	}

	// --- --- Player logic --- ---

	private final LinkedList<Point> turnHistory = new LinkedList<>();
	private final List<Point> turnHistoryUnmodifiable;

	private Point position = null;
	private Vec2D velocity = null;

	public Player() {
		turnHistoryUnmodifiable = Collections.unmodifiableList(turnHistory);
	}

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
	public final List<Point> getTurnHistory() {
		return turnHistoryUnmodifiable;
	}

	/**
	 * Sets the position of this player.
	 * <br>
	 * This will also set a new velocity after the other changes was made.
	 * @param position The new position of the player
	 */
	public final void setPosition(Point position) {
		Point oldPos = this.position;
		// update position
		this.position = position;
		// add entry in turn history
		turnHistory.offerFirst(position);
		// event to handle changes
		onUpdatePosition(oldPos, position);
	}

	/**
	 * Sets the velocity of this player.
	 * @param velocity The new velocity of the player
	 */
	public final void setVelocity(Vec2D velocity) {
		Vec2D oldVelocity = this.velocity;
		// update velocity
		this.velocity = velocity;
		// event to handle changes
		onUpdateVelocity(oldVelocity, velocity);
	}

	@Override
	public String toString() {
		return "Player "+playerNumber+" ("+getClass().getSimpleName()+")";
	}

}
