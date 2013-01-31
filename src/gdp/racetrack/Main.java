package gdp.racetrack;

import javax.swing.JFrame;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 1 && args[0].equalsIgnoreCase("test")) {
			test(); return;
		}
	}

	private static void test() {
		final Map map = new MapGenerator().generateMap((int) (Math.random()-0.5)*2*Integer.MAX_VALUE, new Vec2D(512,512), 3);
		
		PlayerCollisionRule playerCollisionRule = null;
		EnvironmentCollisionRule envCollisionRule = null;
		TurnRule turnRule = null;
		VictoryRule victoryRule = null;
		final RuleSet ruleSet = new RuleSet(envCollisionRule, playerCollisionRule, turnRule, victoryRule);

		AI ai = AIList.createKI(AIList.getList().get(0));
		final Player[] bots = new Player[3];
		bots[0] = ai.createBot(Difficulty.EASY);
		bots[1] = ai.createBot(Difficulty.NORMAL);
		bots[2] = ai.createBot(Difficulty.HARD);
		
		Game game = new Game(map, ruleSet, bots);
		
		game.run();
		
		@SuppressWarnings("serial")
		javax.swing.JFrame frame = new javax.swing.JFrame() {
			@Override
			public void paintComponents(java.awt.Graphics g) {
				g.drawImage(map.getImage(), 0, 0, null);
				for (Player player : bots) {
					Point lastPos = null;
					for (Point point : player.getTurnHistory()) {
						if (lastPos != null) {
							g.drawLine(lastPos.getX(), lastPos.getY(), point.getX(), point.getY());
						}
						lastPos = point;
					}
				}
			}
		};
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
