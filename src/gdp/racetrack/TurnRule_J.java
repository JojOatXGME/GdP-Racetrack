package gdp.racetrack;

import java.util.ArrayList;

public class TurnRule_J implements TurnRule {

	@Override
	public void init(Game game) { }

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
			if (res.equals(destination) && res.getX() >= 0 && res.getY() >= 0) {
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
			if (res.getX() >= 0 && res.getY() >= 0) {
				allowedPoints.add(res);
			}
		}
		return allowedPoints.toArray(new Point[0]);
	}

}
