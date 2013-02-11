package gdp.racetrack.gui;

import gdp.racetrack.AI;
import gdp.racetrack.Difficulty;
import gdp.racetrack.EnvironmentCollisionRule;
import gdp.racetrack.EventListener;
import gdp.racetrack.Game;
import gdp.racetrack.Lists;
import gdp.racetrack.Map;
import gdp.racetrack.MapGenerator;
import gdp.racetrack.Player;
import gdp.racetrack.PlayerCollisionRule;
import gdp.racetrack.Point;
import gdp.racetrack.RuleSet;
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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	enum State {
		NEW_GAME,
		IN_GAME
	}

	private final JPanel topPanel;
	private final JPanel mainPanel;
	private final JLabel bottomLabel;
	
	private final JList<Object> turnRuleList = new JList<Object>(Lists.turnRule.getList().toArray());
	private final JList<Object> victoryRuleList = new JList<Object>(Lists.victoryRule.getList().toArray());
	private final JList<Object> envCollisionRuleList = new JList<Object>(Lists.envCollisionRule.getList().toArray());
	private final JList<Object> playerCollisionRuleList = new JList<Object>(Lists.playerCollisionRule.getList().toArray());
	private final JList<Object> aiList = new JList<Object>(Lists.ai.getList().toArray());
	private final JList<Difficulty> difficultyList = new JList<Difficulty>(Difficulty.values());

	private State state = State.NEW_GAME;

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
		
		JButton newGameButten = new JButton("New Game");
		newGameButten.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (state == State.NEW_GAME) {
					startGame();
				} else {
					showNewGameWizard();
				}
			}
		});
		topPanel.add(newGameButten);
		
		showNewGameWizard();
		
		setVisible(true);
	}

	private void showNewGameWizard() {
		mainPanel.removeAll();
		mainPanel.setLayout(new GridLayout(3, 2, 8, 8));
		
		mainPanel.add(createList("Turn Rule",                  turnRuleList));
		mainPanel.add(createList("Victory Rule",               victoryRuleList));
		mainPanel.add(createList("Environment Collision Rule", envCollisionRuleList));
		mainPanel.add(createList("AI",                         aiList));
		mainPanel.add(createList("Player Collision Rule",      playerCollisionRuleList));
		mainPanel.add(createList("Difficulty",                 difficultyList));
		
		info("Set up a new game. Press 'New Game' to start the game.");
		
		pack();
		setResizable(true);
		this.state = State.NEW_GAME;
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
		mainPanel.setLayout(null);
		
		GameField field = new GameField(map.getImage());
		mainPanel.add(field);
		mainPanel.setSize(field.getSize());
		mainPanel.setPreferredSize(field.getPreferredSize());
		mainPanel.setMinimumSize(field.getMinimumSize());
		mainPanel.setMaximumSize(field.getMaximumSize());
		
		info("Game is running.");
		
		pack();
		setResizable(false);
		this.state = State.IN_GAME;
	}

	private JPanel createList(String name, JList<?> list) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder(name));
		
		//JLabel label = new JLabel(name);
		//panel.add(label, BorderLayout.PAGE_START);
		
		panel.add(list, BorderLayout.CENTER);
		
		return panel;
	}

	private void info(String message) {
		bottomLabel.setForeground(getForeground());
		bottomLabel.setText(message);
	}

	private void error(String message) {
		bottomLabel.setForeground(Color.RED);
		bottomLabel.setText(message);
	}

	private class GameField extends JComponent {
		private static final long serialVersionUID = 1L;
		
		private final Image map;
		private GameField(Image mapImage) {
			this.map = mapImage;
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(map, 0, 0, null);
			g.drawLine(0, 0, 128, 128);
		}
	}

	private class HumanPlayer extends Player {
		@Override
		protected Point turn() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected Point chooseStart(List<Point> possiblePositions) {
			// TODO Auto-generated method stub
			return null;
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
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPlayerTurn(Player player, Point startPoint,
				Point endPoint, Point destinationPoint) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String toString() {
			return "GUI Updater";
		}
	}


	public static void main(String[] args) {
		new MainFrame();
	}

}
