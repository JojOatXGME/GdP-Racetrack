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
			if (player.finished() && player.isfinishallowed()) {
				return player;
			}
		}
		return null;
	}

}
