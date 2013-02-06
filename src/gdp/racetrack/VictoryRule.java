package gdp.racetrack;

public interface VictoryRule {

	public void init(Game game);

	/**
	 * Gets the winner of the game or returns null if there is no winner yet.
	 * @return The winner of the game or null, if there is no winner yet
	 */
	public Player getWinner();

}
