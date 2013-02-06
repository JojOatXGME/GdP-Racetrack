package gdp.racetrack;

public interface EnvironmentCollisionRule {

	public void init(Game game);
	
	public void handleCollision(PlayerInfo player);

}
