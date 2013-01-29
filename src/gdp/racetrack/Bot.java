package gdp.racetrack;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class Bot extends Player {

	private static class Position extends Point {
		Position(int x, int y, Map map) {
			super(x, y, map);
		}

		Position translocate(Velocity Velocity) {
			return new Position(x + Velocity.x, y + Velocity.y, getMap());
		}

		static final long serialVersionUID = 1L;
	}

	private static class Velocity extends Vec2D {
		Velocity(int x, int y) {
			super(x, y);
		}

		Velocity accelerate(Acceleration acceleration) {
			return new Velocity(x + acceleration.x, y + acceleration.y);
		}

		Velocity revert() {
			return new Velocity(-x, -y);
		}

		static final long serialVersionUID = 1L;

	}

	private static class Acceleration extends Vec2D {
		Acceleration(int x, int y) {
			super(x, y);
		}

		Acceleration revert() {
			return new Acceleration(-x, -y);
		}

		static final long serialVersionUID = 1L;
	}

	public static HashSet<Point> track;
	public static final HashMap<Point, Integer> goaldist = new HashMap<Point, Integer>();
	public static final Acceleration[] offsets = new Acceleration[] { new Acceleration(-1, -1), new Acceleration(-1, 0), new Acceleration(-1, 1),
			new Acceleration(0, -1), new Acceleration(0, 1), new Acceleration(1, -1), new Acceleration(1, 0), new Acceleration(1, 1) };
	public static final int maxspeed = 6;
	public static final HashSet<Velocity> speedVelocitys;
	static {
		HashSet<Velocity> Velocitys = new HashSet<Velocity>();
		Velocitys.add(new Velocity(0, 0));
		HashSet<Velocity> newVelocitys = new HashSet<Velocity>();
		for (int i = 0; i < maxspeed; i++, Velocitys = newVelocitys, newVelocitys = new HashSet<Velocity>())
			for (Velocity Velocity : Velocitys)
				for (Acceleration offset : offsets)
					newVelocitys.add(Velocity.accelerate(offset));
		speedVelocitys = Velocitys;
	}

	public static final HashMap<Entry<Point, Velocity>, HashSet<Velocity>> nextVelocity = new HashMap<Entry<Point, Velocity>, HashSet<Velocity>>();

	public static void analyseTrack(HashSet<Point> track, ArrayList<Position> goal) {
		Bot.track = track;
//		ArrayList<Position> Positions = goal;
//		for (int dist = 0; !Positions.isEmpty(); dist++) {
//			ArrayList<Position> newPositions = new ArrayList<Position>();
//			for (Position Position : Positions)
//				if (onMap(Position) && track.contains(Position)
//						&& !goaldist.containsKey(Position)) {
//					goaldist.put(Position, dist);
//					for (Position offset : offsets)
//						newPositions.add(translocate(Position, offset));
//				}
//			Positions = newPositions;
//		}
		HashMap<Entry<Position, Velocity>, HashSet<Acceleration>> newVelocitymap = new HashMap<Entry<Position, Velocity>, HashSet<Acceleration>>();
		for (Position Position : goal)
			for (Velocity speedVelocity : speedVelocitys)
				newVelocitymap.put(new AbstractMap.SimpleImmutableEntry<Position, Velocity>(Position, speedVelocity), null);
		while (!newVelocitymap.isEmpty()) {
			HashMap<Entry<Position, Velocity>, HashSet<Acceleration>> newnewVelocitymap = new HashMap<Entry<Position, Velocity>, HashSet<Acceleration>>();
			for (Entry<Position, Velocity> winconfig : newVelocitymap.keySet()) {
				Position pullposition = winconfig.getKey().translocate(winconfig.getValue().revert());
				if (track.contains(pullposition))
					for (Acceleration offset : offsets) {
						Velocity pullVelocity = winconfig.getValue().accelerate(offset);
						newnewVelocitymap.get(new AbstractMap.SimpleImmutableEntry<Position, Velocity>(pullposition, pullVelocity)).add(offset.revert());
					}
			}
		}
	}

	private Position position;
	private Velocity velocity = new Velocity(0, 0);

	public Bot(Position position) {
		super();
		this.position = position;
	}

	@Override
	protected void onUpdatePosition(Point oldPos, Point newPos) {
		this.position = new Position(newPos.getX(), newPos.getY(), newPos.getMap());
	}

	@Override
	protected void onUpdateVelocity(Vec2D oldVelocity, Vec2D newVelocity) {
		this.velocity = new Velocity(newVelocity.x, newVelocity.y);
	}

	@Override
	public Point turn() {
		Position p = position.translocate(velocity.accelerate(offsets[(int) (Math.random() * 8)]));
		return new Point(p.x, p.y, getGame().getMap());
	}
}
