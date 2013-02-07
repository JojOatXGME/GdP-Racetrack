package gdp.racetrack;

public class EnvironmentCollisionRule_Bounce implements EnvironmentCollisionRule {
	
	public void init(Game game){
		this.game = game;
	}
	
	public void handleCollision(Turn turninfo) {
		
		Point newposition = turninfo.getNewPosition();
		Point lastposition = turninfo.getOldPosition();
		
		Map map = game.getMap();
		
		map.getTurnResult(turninfo);
		
		if (turninfo.collideEnv()) {
			Vec2D stop = new Vec2D(0,0);
			// turninfo.setNewPosition(turninfo.getNewPosition());
			turninfo.setNewVelocity(stop);
		}
		else {
			return;
		}
		
		
	}
	private Game game;

}
