package gdp.racetrack;

public interface AI {

	public boolean isDifficultySupported();

	public Player createBot(Difficulty difficulty);

}
