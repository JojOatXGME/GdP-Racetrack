import java.awt.Point;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class Bot extends Player {

	private static class Position extends Point {
		Position(int x, int y) {
			super(x, y);
		}

		Position translocate(Vector vector) {
			return new Position(x + vector.x, y + vector.y);
		}

		static final long serialVersionUID = 1L;
	}

	private static class Vector extends Point {
		Vector(int x, int y) {
			super(x, y);
		}

		Vector accelerate(Acceleration acceleration) {
			return new Vector(x + acceleration.x, y + acceleration.y);
		}

		Vector revert() {
			return new Vector(-x, -y);
		}

		static final long serialVersionUID = 1L;

	}

	private static class Acceleration extends Point {
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
	private static Point mapsize;
	public static final Acceleration[] offsets = new Acceleration[] { new Acceleration(-1, -1), new Acceleration(-1, 0), new Acceleration(-1, 1),
			new Acceleration(0, -1), new Acceleration(0, 1), new Acceleration(1, -1), new Acceleration(1, 0), new Acceleration(1, 1) };
	public static final int maxspeed = 6;
	public static final HashSet<Vector> speedvectors;
	static {
		HashSet<Vector> vectors = new HashSet<Vector>();
		vectors.add(new Vector(0, 0));
		HashSet<Vector> newvectors = new HashSet<Vector>();
		for (int i = 0; i < maxspeed; i++, vectors = newvectors, newvectors = new HashSet<Vector>())
			for (Vector vector : vectors)
				for (Acceleration offset : offsets)
					newvectors.add(vector.accelerate(offset));
		speedvectors = vectors;
	}

	public static final HashMap<Entry<Point, Vector>, HashSet<Vector>> nextvector = new HashMap<Entry<Point, Vector>, HashSet<Vector>>();

	public static void analyseTrack(HashSet<Point> track, ArrayList<Position> goal, Point mapsize) {
		KI.track = track;
		KI.mapsize = mapsize;
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
		HashMap<Entry<Position, Vector>, HashSet<Acceleration>> newvectormap = new HashMap<Entry<Position, Vector>, HashSet<Acceleration>>();
		for (Position Position : goal)
			for (Vector speedvector : speedvectors)
				newvectormap.put(new AbstractMap.SimpleImmutableEntry<Position, Vector>(Position, speedvector), null);
		while (!newvectormap.isEmpty()) {
			HashMap<Entry<Position, Vector>, HashSet<Acceleration>> newnewvectormap = new HashMap<Entry<Position, Vector>, HashSet<Acceleration>>();
			for (Entry<Position, Vector> winconfig : newvectormap.keySet()) {
				Position pullposition = winconfig.getKey().translocate(winconfig.getValue().revert());
				if (track.contains(pullposition))
					for (Acceleration offset : offsets) {
						Vector pullvector = winconfig.getValue().accelerate(offset);
						newnewvectormap.get(new AbstractMap.SimpleImmutableEntry<Position, Vector>(pullposition, pullvector)).add(offset.revert());
					}
			}
		}
	}

//	private static boolean onMap(Point point) {
//		return point.x >= 0 && point.y >= 0 && point.x < mapsize.y && point.y < mapsize.y;
//	}

	public Acceleration turn() {
		return offsets[(int) (Math.random() * 8)];
	}
}
