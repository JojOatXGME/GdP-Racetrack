package gdp.racetrack;

public interface AI {

	/**
	 * Gets whether the difficulty options are supported or not.
	 * @return true if difficulty is supported, false otherwise
	 */
	public boolean isDifficultySupported();

	/**
	 * Create a new bot player and return it.
	 * You can set a specific difficulty if the AI does support this. Otherwise set it null.
	 * @param difficulty The difficulty of the new bot or null if it is not supported
	 * @return A new created Bot
	 */
	public Player createBot(Difficulty difficulty);

}
