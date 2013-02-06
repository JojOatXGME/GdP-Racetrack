package gdp.racetrack;

import javax.swing.JFrame;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Log.addDebugWindow();
		// TODO if (args.length == 1 && args[0].equalsIgnoreCase("test")) {
			test(); return;
		// TODO }
	}

	private static void test() {
		System.out.println("The system will run a test. Please wait");
		
		System.out.println("create map ...");
		final Map map = new MapGenerator().generateMap((int) (Math.random()-0.5)*2*Integer.MAX_VALUE, 3, Difficulty.NORMAL);
		
		System.out.println("create game rules ...");
		PlayerCollisionRule playerCollisionRule =
				Lists.playerCollisionRule.createInstance(0);
		EnvironmentCollisionRule envCollisionRule =
				Lists.envCollisionRule.createInstance(0);
		TurnRule turnRule =
				Lists.turnRule.createInstance(0);
		VictoryRule victoryRule =
				Lists.victoryRule.createInstance(0);

		final RuleSet ruleSet = new RuleSet(envCollisionRule, playerCollisionRule, turnRule, victoryRule);
		
		System.out.println("create game ...");
		final Game game = new Game(map, ruleSet, null);
		
		System.out.println("create an AI ...");
		final AI ai = Lists.ai.createInstance(0, game);
		System.out.println("create 3 bots of the created AI ...");
		game.addPlayer(ai, Difficulty.EASY, Difficulty.NORMAL, Difficulty.HARD);
		
		System.out.println("run the game ...");
		game.run();
		
		@SuppressWarnings("serial")
		javax.swing.JFrame frame = new javax.swing.JFrame() {
			@Override
			public void paintComponents(java.awt.Graphics g) {
				g.drawImage(map.getImage(), 0, 0, null);
				for (Player player : game.getPlayers()) {
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.out.println("drawing output ...");
		frame.setVisible(true);
		
		System.out.println("FINISHED.");
	}
}
