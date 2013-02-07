package gdp.racetrack;

import java.util.ArrayList;

public class TurnRule_J implements TurnRule {
	private Game game;

	@Override
	public void init(Game game) {
		this.game = game;
	}

	private final Vec2D[] accs = {
			new Vec2D(-1, -1),
			new Vec2D( 0, -1),
			new Vec2D( 1, -1),
			new Vec2D(-1,  0),
			new Vec2D( 1,  0),
			new Vec2D(-1,  1),
			new Vec2D( 0,  1),
			new Vec2D( 1,  1)
	};

	@Override
	public boolean isTurnAllowed(Player player, Point destination) {
		return isTurnAllowed(player.getPosition(), player.getVelocity(), destination);
	}

	@Override
	public boolean isTurnAllowed(Point start, Vec2D velocity, Point destination) {
		for (Vec2D acc : accs) {
			Point res = start.add(velocity).add(acc);
			if (res.equals(destination) && isPointValid(res)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Point[] getAllowedTurns(Player player) {
		return getAllowedTurns(player.getPosition(), player.getVelocity());
	}

	@Override
	public Point[] getAllowedTurns(Point position, Vec2D velocity) {
		ArrayList<Point> allowedPoints = new ArrayList<Point>();
		for (Vec2D acc : accs) {
			Point res = position.add(velocity).add(acc);
			if (isPointValid(res)) {
				allowedPoints.add(res);
			}
		}
		return allowedPoints.toArray(new Point[0]);
	}

	private boolean isPointValid(Point p) {
		return (p.getX() >= 0 && p.getY() >= 0 &&
				p.getX() < game.getMap().getSize().x
				&& p.getY() < game.getMap().getSize().y
				);
	}

}
