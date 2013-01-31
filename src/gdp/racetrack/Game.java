package gdp.racetrack;

import java.util.ArrayList;
import java.util.List;

public class Game {

	public enum State {
		/**
		 * The Game is running.
		 */
		RUNNING,
		/**
		 * The game don't run but is not finished.
		 */
		PAUSED,
		/**
		 * The game is finished.
		 */
		FINISHED
	}

	private final Map map;
	private final Player[] players;
	private final RuleSet rule;

	private final List<EventListener> listeners = new ArrayList<EventListener>();

	private State state = State.PAUSED;
	private Player winner = null;

	/**
	 * Creates a new game.
	 * 
	 * @param map The used map for the game
	 * @param players The players which play the game
	 * @param rule The used rules of the game
	 */
	public Game(Map map, Player[] players, RuleSet rule) {
		this.map = map;
		this.players = players;
		this.rule = rule;
		
		// initial player
		for (Player player : players) {
			player.init(this);
		}
		
		// TODO: all player must pick a start
	}

	/**
	 * Gets the map of this game.
	 * @return The map of this game
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Gets the game rule of this game.
	 * @return The game rule of this game
	 */
	public RuleSet getRule() {
		return rule;
	}

	/**
	 * Gets the specific player.
	 * @param index The index of the player you wand to have
	 * @return The specific player
	 */
	public Player getPlayer(int index) {
		return players[index];
	}

	/**
	 * Gets the players which play this game.
	 * @return The players which play this game
	 */
	public Player[] getPlayers() {
		return players;
	}

	/**
	 * Gets the current state of the game.
	 * There are 3 possibilities. The game is running, paused or finished.
	 * @return The state of the game
	 */
	public State getState() {
		return state;
	}

	/**
	 * Pause the game.
	 * <br>
	 * It will end the run-Method in the next time.
	 * @throws IllegalStateException if the game have already finished
	 */
	public void pause() {
		synchronized(this) {
			if (state == State.FINISHED)
				throw new IllegalStateException("The game can't pasue if it is finished");
			
			state = State.PAUSED;
		}
	}

	/**
	 * Start or continue the game.
	 * <br>
	 * This method will run and block your sequence until the game is finished or paused.
	 * @throws IllegalStateException if the game is already running or finished
	 */
	public void run() {
		if (state == State.RUNNING)
			throw new IllegalStateException("The game is already running");
		if (state == State.FINISHED)
			throw new IllegalStateException("The game have finished and there is nothing to do");
		
		state = State.RUNNING;
		while (state == State.RUNNING) {
			for (Player player : players) {
				final Vec2D velocity = player.getVelocity();
				final Point start = player.getPosition();
				final Point destination = player.turn();
				
				if (!rule.isTurnAllowed(player, destination))
					throw new IllegalTurnException("The turn of "+player+" is not allowed");
				if (player.getPosition() != start)
					throw new IllegalTurnException(player+" had manipulate his position");
				if (player.getVelocity() != velocity)
					throw new IllegalTurnException(player+" had manipulate his velocity");
				
				// TODO: handle turn
				
				onPlayerTurn(player, start, player.getPosition(), destination);
			}
			
			synchronized (this) {
				winner = rule.getWinner();
				if (winner != null) {
					state = State.FINISHED;
				}
			}
		}
	}

	/**
	 * Registers an EventListener to call it by events.
	 * @param listener The EventListener to register
	 */
	public void registerListener(EventListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("EventListener can not be null");
		
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	private void onPlayerTurn(Player player, Point startPoint,Point endPoint, Point destinationPoint) {
		for (EventListener l : listeners) {
			l.onPlayerTurn(player, startPoint, endPoint, destinationPoint);
		}
	}

	private void onMapUpdate(Map map) {
		for (EventListener l : listeners) {
			l.onMapUpdate(map);
		}
	}

}
