package gdp.racetrack;

import java.util.List;

public class VictoryRule_First implements VictoryRule {
	
	private Game game;
	
	public void init(Game game) {
		this.game = game;
	}
	
	public Player getWinner() {
		List<Player> players = game.getPlayers();
		for(Player player : players){
			boolean finish = player.getLastTurn().hasCrossedFinishLine();
			if (finish && player.isPathValid()) {
				return player;
			}
		}
		return null;
	}

}
