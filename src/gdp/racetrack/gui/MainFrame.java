package gdp.racetrack.gui;

import gdp.racetrack.AI;
import gdp.racetrack.Difficulty;
import gdp.racetrack.EnvironmentCollisionRule;
import gdp.racetrack.EventListener;
import gdp.racetrack.Game;
import gdp.racetrack.IrrevocableTurn;
import gdp.racetrack.Lists;
import gdp.racetrack.Log;
import gdp.racetrack.Map;
import gdp.racetrack.MapGenerator;
import gdp.racetrack.Player;
import gdp.racetrack.PlayerCollisionRule;
import gdp.racetrack.Point;
import gdp.racetrack.RuleSet;
import gdp.racetrack.Turn;
import gdp.racetrack.TurnRule;
import gdp.racetrack.VictoryRule;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private enum State {
		PREPARING,
		NEW_GAME,
		IN_GAME
	}

	private final JPanel topPanel;
	private final JPanel mainPanel;
	private final JLabel bottomLabel;

	private final JButton newGameButton;

	private final JList<Object> turnRuleList = new JList<Object>(Lists.turnRule.getList().toArray());
	private final JList<Object> victoryRuleList = new JList<Object>(Lists.victoryRule.getList().toArray());
	private final JList<Object> envCollisionRuleList = new JList<Object>(Lists.envCollisionRule.getList().toArray());
	private final JList<Object> playerCollisionRuleList = new JList<Object>(Lists.playerCollisionRule.getList().toArray());
	private final JList<Object> aiList = new JList<Object>(Lists.ai.getList().toArray());
	private final JList<Difficulty> difficultyList = new JList<Difficulty>(Difficulty.values());

	private State state = State.PREPARING;
	private Thread gameThread = null;
	private JPanel gameField = null;

	public MainFrame() {
		super("VectorAce");
		setMinimumSize(new Dimension(256, 256));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getSize().width) / 2;
		int y = (d.height - getSize().height) / 2;
		setLocation(x, y);
		
		topPanel = new JPanel(new GridLayout(1, 0));
		getContentPane().add(topPanel, BorderLayout.PAGE_START);
		
		mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		bottomLabel = new JLabel();
		getContentPane().add(bottomLabel, BorderLayout.PAGE_END);
		
		bottomLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (state == State.NEW_GAME) {
					startGame();
				} else {
					if (state == State.IN_GAME) {
						stopGame();
					}
					showNewGameWizard();
				}
			}
		});
		topPanel.add(newGameButton);
		
		showNewGameWizard();
		
		setVisible(true);
	}

	private void showNewGameWizard() {
		newGameButton.setEnabled(false);
		
		mainPanel.removeAll();
		mainPanel.setLayout(new GridLayout(3, 2, 8, 8));
		
		mainPanel.add(createList("Turn Rule",                  turnRuleList));
		mainPanel.add(createList("Victory Rule",               victoryRuleList));
		mainPanel.add(createList("Environment Collision Rule", envCollisionRuleList));
		mainPanel.add(createList("AI",                         aiList));
		mainPanel.add(createList("Player Collision Rule",      playerCollisionRuleList));
		mainPanel.add(createList("Difficulty",                 difficultyList));
		
		info("Set up a new game. Press 'Start new Game' to start the game.");
		
		pack();
		this.state = State.NEW_GAME;
		
		newGameButton.setText("Start new Game");
		newGameButton.setEnabled(true);
	}

	private void startGame() {
		// check whether all is set
		if (turnRuleList.isSelectionEmpty()) {
			error("There was no Turn Rule set.");
			return;
		}
		if (victoryRuleList.isSelectionEmpty()) {
			error("There was no Victory Rule set.");
			return;
		}
		if (envCollisionRuleList.isSelectionEmpty()) {
			error("There was no Environment Collision Rule set.");
			return;
		}
		if (playerCollisionRuleList.isSelectionEmpty()) {
			error("There was no Player Collision Rule set.");
			return;
		}
		if (aiList.isSelectionEmpty()) {
			error("There was no AI set tu use.");
			return;
		}
		if (difficultyList.isSelectionEmpty()) {
			error("There was no Difficulty set.");
			return;
		}
		
		newGameButton.setEnabled(false);
		
		// create the new game
		// --- get map
		final Map map = new MapGenerator().generateMap(
				(int) (Math.random()-0.5)*2*Integer.MAX_VALUE, 4, difficultyList.getSelectedValue());
		
		// --- get rules
		TurnRule turnRule =
				Lists.turnRule.createInstance(turnRuleList.getSelectedIndex());
		VictoryRule victoryRule =
				Lists.victoryRule.createInstance(victoryRuleList.getSelectedIndex());
		PlayerCollisionRule playerCollisionRule =
				Lists.playerCollisionRule.createInstance(playerCollisionRuleList.getSelectedIndex());
		EnvironmentCollisionRule envCollisionRule =
				Lists.envCollisionRule.createInstance(envCollisionRuleList.getSelectedIndex());
		
		final RuleSet ruleSet = new RuleSet(envCollisionRule, playerCollisionRule, turnRule, victoryRule);
		
		// --- create instance of game with one human player
		final Game game = new Game(map, ruleSet, new Player[] {new HumanPlayer()});
		
		// --- create AI and add 3 bots to the game
		final AI ai = Lists.ai.createInstance(aiList.getSelectedIndex(), game);
		game.addPlayer(ai, difficultyList.getSelectedValue(), difficultyList.getSelectedValue(), difficultyList.getSelectedValue());
		
		// --- register an listener for changes in game
		game.registerListener(new Updater());
		
		// show game in GUI
		mainPanel.removeAll();
		mainPanel.setLayout(new GridLayout(1,1));
		
		gameField = new GameField(map.getImage(), game);
		mainPanel.add(gameField);
		// mainPanel.setSize(field.getSize());
		// mainPanel.setPreferredSize(field.getSize());
		// mainPanel.setMinimumSize(field.getMinimumSize());
		// mainPanel.setMaximumSize(field.getMaximumSize());
		
		info("Game is running ...");
		
		pack();
		setResizable(false);
		this.state = State.IN_GAME;
		
		newGameButton.setText("New Game");
		newGameButton.setEnabled(true);
		
		gameThread = new Thread(game);
		// TODO add exception handler
		gameThread.start();
	}

	private void stopGame() {
		// TODO remove listener from game?
		// mainPanel.setPreferredSize(null);
		// mainPanel.setMinimumSize(null);
		// mainPanel.setMaximumSize(null);
		setResizable(true);
	}

	private JPanel createList(String name, JList<?> list) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder(name));
		
		//JLabel label = new JLabel(name);
		//panel.add(label, BorderLayout.PAGE_START);
		
		panel.add(list, BorderLayout.CENTER);
		
		return panel;
	}

	private Color getPlayerColor(Player player) {
		switch (player.getNumber()) {
		case 0: return Color.BLACK;
		case 1: return Color.BLUE;
		case 2: return Color.RED;
		case 3: return Color.GREEN;
		case 4: return Color.YELLOW;
		default: return Color.BLACK;
		}
	}

	private void info(String message) {
		bottomLabel.setForeground(getForeground());
		bottomLabel.setText(message);
	}

	private void error(String message) {
		bottomLabel.setForeground(Color.RED);
		bottomLabel.setText(message);
	}

	private class GameField extends JPanel {
		private static final long serialVersionUID = 1L;

		private final Image map;
		private final Game game;
		private GameField(Image mapImage, Game game) {
			this.map = mapImage;
			this.game = game;
			
			this.setSize(map.getWidth(this), map.getHeight(this)); // TODO this or null
			this.setMinimumSize(getSize());
			this.setMaximumSize(getSize());
			this.setPreferredSize(getSize());
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(map, 0, 0, this); // TODO this or null
			if (game.getState() == Game.State.RUNNING) {
				for (Player player : game.getPlayers()) {
					g.setColor(getPlayerColor(player));
					List<IrrevocableTurn> turnHistory = player.getTurnHistory();
					if (turnHistory.size() == 0 && player.getPosition() != null) {
						g.fillOval(player.getPosition().getX()*Map.GRIDSIZE-4, player.getPosition().getY()*Map.GRIDSIZE-4, 8, 8);
					} else {
						boolean first = false;
						for (IrrevocableTurn turn : player.getTurnHistory()) {
							final Point s = turn.getStartPosition();
							final Point e = turn.getEndPosition();
							if (first) {
								g.drawOval(s.getX()*Map.GRIDSIZE-4, s.getY()*Map.GRIDSIZE-4, 8, 8);
								first = false;
							}
							g.drawLine(s.getX(), s.getY(), e.getX(), e.getY());
						}
					}
				}
			}
		}
	}

	private class HumanPlayer extends Player {
		@Override
		protected Point turn() {
			final Point[] lock = new Point[1];
			gameField.setLayout(null);
			for (final Turn turn : getGame().getRule().getAllowedTurns(this)) {
				gameField.add(new Button(lock, turn.getDestination(), turn.getDestination()));
			}
			info("Player "+getNumber()+" have to choose the next turn");
			
			// wait until the player have choose a start position
			synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					Log.logger.log(Level.SEVERE, "Thread was interrupted by waitung for players turn", e);
				}
			}
			
			info("Game is running ...");
			
			return lock[0];
		}

		@Override
		protected Point chooseStart(List<Point> possiblePositions) {
			final Point[] lock = new Point[1];
			gameField.setLayout(null);
			for (final Point pos : possiblePositions) {
				gameField.add(new Button(lock, pos, pos));
			}
			info("Player "+getNumber()+" have to choose a start position");
			
			// wait until the player have choose a start position
			synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					Log.logger.log(Level.SEVERE, "Thread was interrupted by waiting of player", e);
				}
			}
			
			info("Game is running ...");
			
			return lock[0];
		}

		private class Button extends JButton {
			private static final long serialVersionUID = 1L;

			Button(final Point[] lock, final Point pos, final Point dest) {
				super("+"); // TODO use Icon
				this.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						synchronized(lock) {
							lock[0] = dest;
							lock.notify();
						}
						gameField.removeAll();
					}
				});
				this.setLocation(pos.getX()*Map.GRIDSIZE-8, pos.getY()*Map.GRIDSIZE-8);
				this.setSize(17, 17);
			}
		}
	}

	private class Updater implements EventListener {
		@Override
		public void onGameStart(boolean firstTime) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGamePause() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGameFinished() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPlayerChooseStart(Player player) {
			gameField.repaint();
		}

		@Override
		public void onPlayerTurn(Player player, Point startPoint,
				Point endPoint, Point destinationPoint) {
			
			gameField.repaint();
		}

		@Override
		public String toString() {
			return "GUI Updater";
		}
	}

}
