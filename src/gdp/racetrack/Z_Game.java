package gdp.racetrack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Z_Game extends JFrame {
	public JLabel Ein;
	private JPanel panel;
	MapGenerator mapGen = new MapGenerator();
	private Map m;
	ImageIcon Feld;
	public HL lol;
	public int ox = 0;
	public int oy = 0;
	RuleSet ST;
	boolean first = true;
	HumanPlayer[] P;
	List<Point> startPoints;
	Game Gr;

	class HL extends JLabel {

		HL() {
			super();
		}

		public void paint(Graphics gi) {
			Graphics2D g = (Graphics2D) gi;
			g.setStroke(new BasicStroke(2));
			g.drawImage(Feld.getImage(), 0, 0, this);
			for (int i = 0; i < Feld.getIconHeight(); i = i + 16)
				g.drawLine(0, i, Feld.getIconWidth(), i);
			for (int a = 0; a < Feld.getIconWidth(); a = a + 16)
				g.drawLine(a, 0, a, Feld.getIconHeight());
			for (int t = 0; t < m.getStartPoints().length; t++) {
				kreis(g, m.getStartPoints()[t].getX() * 16,
						m.getStartPoints()[t].getY() * 16, Color.YELLOW);

			}
		}
	}

	public Z_Game(Difficulty g) {
		super();
		m = mapGen.generateMap((int) (Math.random() * 1000), 4, g);
		final RuleSet ST = new RuleSet(new EnvironmentCollisionRule_Bounce(),
				new PlayerCollision_PhaseThrough(), new TurnRule_J(),
				new VictoryRule_First());
		P = new HumanPlayer[1];
		P[0] = new HumanPlayer(this);
		Gr = new Game(m, ST, P);
		startPoints = new ArrayList<Point>();
		for (Point startPoint : m.getStartPoints()) {
			startPoints.add(startPoint);
		}
		Feld = new ImageIcon(m.getImage());
		Dimension dim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 4, dim.height / 4);
		setUndecorated(true);
		panel = new JPanel();
		panel.setLayout(null);
		setSize(Feld.getIconWidth(), Feld.getIconHeight());
		getContentPane().add(panel);
		setVisible(true);
		panel.setVisible(true);
		lol = new HL();
		lol.setLayout(null);
		panel.add(lol);
		lol.getGraphics().drawLine(0, 0, 100, 100);
		lol.setSize(Feld.getIconWidth(), Feld.getIconHeight());
		lol.addMouseListener(new MouseListener() {
			Turn[] urgu = null;
			Vec2D vej = new Vec2D(0, 0);;

			@Override
			public void mouseClicked(MouseEvent e) {
				Graphics2D g2d = (Graphics2D) lol.getGraphics();
				// if (!first)Gr.run();
				/*
				 * TurnRule_J ji=new TurnRule_J(); ji.init(Gr);
				 */

				if (!first) {
					for (int i = 0; i < urgu.length; i++) {
						int zx = urgu[i].getNewPosition().getX() * 16;
						int zy = urgu[i].getNewPosition().getY() * 16;
						kreis(g2d, zx, zy, Color.BLACK);
					}
				}

				int nx = 0;
				int ny = 0;
				nx = e.getX();
				ny = e.getY();
				if (nx % 16 < 8)
					while (nx % 16 != 0) {
						nx--;
					}
				if (nx % 16 > 8)
					while (nx % 16 != 0) {
						nx++;
					}
				if (ny % 16 < 8)
					while (ny % 16 != 0) {
						ny--;
					}
				if (ny % 16 > 8)
					while (ny % 16 != 0) {
						ny++;
					}
				if (first) {
					first = false;
					ox = nx;
					oy = ny;
					P[0].chooseStart(startPoints);
					for (int t = 0; t < m.getStartPoints().length; t++) {
						kreis(g2d, m.getStartPoints()[t].getX() * 16,
								m.getStartPoints()[t].getY() * 16, Color.BLACK);
					}
				} else {
					g2d.setStroke(new BasicStroke(3));
					g2d.setColor(Color.red);
					g2d.drawLine(ox, oy, nx, ny);
				}
				int dx = (nx - ox) / 16;
				int dy = (ny - oy) / 16;
				ox = nx;
				oy = ny;
				// vej=vej.add(vej);
				urgu = ST.getAllowedTurns(new Point(ox / 16, oy / 16), vej);
				vej = (new Vec2D(dx, dy).mul(2).add(new Vec2D(dx, dy)));
				for (int i = 0; i < urgu.length; i++) {
					int zx = urgu[i].getNewPosition().getX() * 16;
					int zy = urgu[i].getNewPosition().getY() * 16;
					kreis(g2d, zx, zy, Color.YELLOW);
				}
				System.out.println(dx + "/" + vej.x + "/" + dy + "/" + vej.y);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});

		lol.setVisible(true);
	}

	public void kreis(Graphics ui, int x, int y, Color Farbe) {
		Graphics2D g2d = (Graphics2D) ui;
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Farbe);
		g2d.fillRect(x - 1, y - 1, 2, 2);
	}

	public static void create(Difficulty g) {
		Z_Game Z = new Z_Game(g);
	}

}
