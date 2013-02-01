package gdp.racetrack;

import gdp.racetrack.Turn.TurnType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.xml.internal.txw2.IllegalSignatureException;

public class Game {

	public enum State {
		/**
		 * The game does never run yet.
		 */
		PREPARING,
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
	private final RuleSet rule;
	private final List<Player> players;
	private final List<Player> playersUnmodifiable;

	private final List<EventListener> listeners = new ArrayList<EventListener>();

	private State state = State.PREPARING;
	private Player winner = null;

	/**
	 * Creates a new game.
	 * 
	 * @param map The used map for the game
	 * @param players The players which play the game
	 * @param rule The used rules of the game
	 */
	public Game(Map map, RuleSet ruleSet, Player[] players) {
		this.map = map;
		this.rule = ruleSet;
		this.players = new ArrayList<Player>();
		
		this.playersUnmodifiable = Collections.unmodifiableList(this.players);
		
		if (players != null) {
			for (Player p : players) {
				this.players.add(p);
			}
		}
	}

	/**
	 * Adds a player to the game.
	 * If the game have already started the method will throw a {@link IllegalStateException}.
	 * @param player The Player to add to the game
	 */
	public void addPlayer(final Player player) {
		if (state != State.PREPARING)
			throw new IllegalSignatureException("The game must have never run to change the list of players");
		
		players.add(player);
	}

	/**
	 * Adds some player to the game.
	 * The method will get the players from the given AI instance.
	 * If the AI don't support difficulty, submit null as difficulty.
	 * The game will create as many players as difficulty passed.
	 * If the game have already started the method will throw a {@link IllegalStateException}.
	 * @param ai The AI to create the player.
	 * @param difficulties The difficulties of the players.
	 */
	public void addPlayer(final AI ai, final Difficulty... difficulties) {
		for (Difficulty dif : difficulties) {
			addPlayer(ai.createBot(dif));
		}
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
	 * Gets the players which play this game.
	 * This method will throw a {@link IllegalStateException} if you call
	 * it while the game is preparing.
	 * @return The players which play this game
	 */
	public List<Player> getPlayers() {
		if (state == State.PREPARING)
			throw new IllegalStateException("The game must be ready to get informations like that");
		
		return playersUnmodifiable;
	}

	/**
	 * Gets the current state of the game.
	 * There are 4 possibilities.
	 * The game can be preparing, running, paused or finished.
	 * @return The state of the game
	 */
	public State getState() {
		return state;
	}

	/**
	 * Gets the winner of the game.
	 * If this method is called while the game is not finished,
	 * it will throw a {@link IllegalStateException}.
	 * @return The winner of the game
	 */
	public Player getWinner() throws IllegalStateException {
		if (state != State.FINISHED)
			throw new IllegalStateException("Game must be finished to get a winner");
		
		return winner;
	}

	/**
	 * Pause the game.
	 * <br>
	 * It will end the run-Method in the next time.
	 * @throws IllegalStateException if the game have already finished or has never run
	 */
	public void pause() {
		synchronized(this) {
			if (state == State.FINISHED)
				throw new IllegalStateException("Can not pause the game when it is finished");
			if (state == State.PREPARING)
				throw new IllegalStateException("Can not pause the game when it was never started");
			
			state = State.PAUSED;
		}
	}

	/**
	 * Start or continue the game.
	 * <br>
	 * This method will run and block your sequence until the game is finished or paused.
	 * If you call this method while the it is running or already finished,
	 * it will throw a {@link IllegalStateException}.
	 * @throws IllegalStateException if the game is already running or finished
	 */
	public void run() {
		if (state == State.RUNNING)
			throw new IllegalStateException("The game is already running");
		if (state == State.FINISHED)
			throw new IllegalStateException("The game have finished and there is nothing to do");
		
		boolean firstRun = (state == State.PREPARING);
		state = State.RUNNING;
		
		if (firstRun) {
			// initial player
			int i = 1;
			for (Player p : players) {
				p.init(this, i++);
			}
			
			List<Point> startPoints = new ArrayList<Point>(); // TODO: get possible start positions
			List<Point> unmodifiable = Collections.unmodifiableList(startPoints);
			for (Player player : players) {
				Point selected = player.chooseStart(unmodifiable);
				if (!startPoints.contains(selected))
					throw new IllegalTurnException(player+" has selected an illigal start position");
				
				player.setPosition(selected);
				onPlayerChooseStart(player);
				
				startPoints.remove(selected);
			}
		}
		
		onGameStart(firstRun);
		
		while (state == State.RUNNING) {
			for (Player player : players) {
				final Vec2D velocity = player.getVelocity();
				final Point start = player.getPosition();
				final Point destination = player.turn();
				final Turn turn = rule.getTurnResult(player, destination);
				
				if (turn.getTurnType() == TurnType.FORBIDDEN)
					throw new IllegalTurnException("The turn of "+player+" is not allowed");
				if (player.getPosition() != start)
					throw new IllegalTurnException(player+" had manipulate his position");
				if (player.getVelocity() != velocity)
					throw new IllegalTurnException(player+" had manipulate his velocity");
				
				player.setPosition(turn.getNewPosition());
				player.setVelocity(turn.getNewVelocity());
				if (turn.getAffectedPlayer() != null) {
					turn.getAffectedPlayer().setPosition(turn.getAffectedPlayerInfo().getNewPos());
					turn.getAffectedPlayer().setVelocity(turn.getAffectedPlayerInfo().getVelocity());
				}
				
				onPlayerTurn(player, start, player.getPosition(), destination);
			}
			
			synchronized (this) {
				winner = rule.getWinner();
				if (winner != null) {
					state = State.FINISHED;
				}
			}
		}
		
		if (state == State.PAUSED) {
			onGamePause();
		} else if (state == State.FINISHED) {
			onGameFinished();
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

	private void onGameStart(boolean firstTime) {
		for (EventListener l : listeners) {
			l.onGameStart(firstTime);
		}
	}

	private void onGamePause() {
		for (EventListener l : listeners) {
			l.onGamePause();
		}
	}

	private void onGameFinished() {
		for (EventListener l : listeners) {
			l.onGameFinished();
		}
	}

	private void onPlayerChooseStart(Player player) {
		for (EventListener l : listeners) {
			l.onPlayerChooseStart(player);
		}
	}

	private void onPlayerTurn(Player player, Point startPoint,Point endPoint, Point destinationPoint) {
		for (EventListener l : listeners) {
			l.onPlayerTurn(player, startPoint, endPoint, destinationPoint);
		}
	}

}
