package gdp.racetrack;

public interface PlayerCollisionRule {

	public void handleCollision(PlayerInfo actor, PlayerInfo victim);

}
