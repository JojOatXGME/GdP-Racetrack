package gdp.racetrack;
import gdp.racetrack.Turn.TurnType;

public class EnvironmentCollisionRule_Bounce implements EnvironmentCollisionRule {
	
	public void init(Game game){
		this.game = game;
	}
	
	public void handleCollision(PlayerInfo player) {
		
		Point newposition = player.getNewPos();
		Point lastposition = player.getLastPos();
		
		Map map = game.getMap();
		
		Turn turninfo = map.getTurnResult(lastposition, newposition);
		
		if (turninfo.getTurnType() == TurnType.COLLISION_ENVIRONMENT) {
			Vec2D stop = new Vec2D(0,0);
			player.setPosition(turninfo.getNewPosition());
			player.setVelocity(stop);
		}
		else {
			return;
		}
		
		
	}
	private Game game;
}
