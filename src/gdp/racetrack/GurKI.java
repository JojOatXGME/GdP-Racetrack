package gdp.racetrack;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import com.sun.corba.se.impl.orbutil.graph.Graph;

public class GurKI {
	
	private class Position extends Point {
		Position(int x, int y, Map map) {
			super(x, y, map);
		}
		
		Position translocate(Velocity Velocity) {
			return new Position(x + Velocity.x, y + Velocity.y, getMap());
		}
	}
	
	private class Velocity extends Vec2D {
		Velocity(int x, int y) {
			super(x, y);
		}
		
		Velocity accelerate(Acceleration acceleration) {
			return new Velocity(x + acceleration.x, y + acceleration.y);
		}
		
		Velocity revert() {
			return new Velocity(-x, -y);
		}
	}
	
	private class Acceleration extends Vec2D {
		Acceleration(int x, int y) {
			super(x, y);
		}
		
		Acceleration revert() {
			return new Acceleration(-x, -y);
		}
	}
	
	public HashSet<Point> track;
	public final HashMap<Point, Integer> goaldist = new HashMap<Point, Integer>();
	public final Acceleration[] offsets = new Acceleration[] {
		new Acceleration(-1, -1), new Acceleration(-1, 0),
		new Acceleration(-1, 1), new Acceleration(0, -1),
		new Acceleration(0, 1), new Acceleration(1, -1),
		new Acceleration(1, 0), new Acceleration(1, 1) };
//	public final int maxspeed = 6;
//	
//	public final HashMap<Entry<Point, Velocity>, HashSet<Velocity>> nextVelocity = new HashMap<Entry<Point, Velocity>, HashSet<Velocity>>();
//	
//	public void analyseTrack(HashSet<Point> track,
//			ArrayList<Position> goal) {
//		this.track = track;
//		// ArrayList<Position> Positions = goal;
//		// for (int dist = 0; !Positions.isEmpty(); dist++) {
//		// ArrayList<Position> newPositions = new ArrayList<Position>();
//		// for (Position Position : Positions)
//		// if (onMap(Position) && track.contains(Position)
//		// && !goaldist.containsKey(Position)) {
//		// goaldist.put(Position, dist);
//		// for (Position offset : offsets)
//		// newPositions.add(translocate(Position, offset));
//		// }
//		// Positions = newPositions;
//		// }
//		HashMap<Entry<Position, Velocity>, HashSet<Acceleration>> newVelocitymap = new HashMap<Entry<Position, Velocity>, HashSet<Acceleration>>();
////		for (Position Position : goal)
////			for (Velocity speedVelocity : speedVelocitys)
////				newVelocitymap
////				.put(new AbstractMap.SimpleImmutableEntry<Position, Velocity>(
////						Position, speedVelocity), null);
//		while (!newVelocitymap.isEmpty()) {
//			HashMap<Entry<Position, Velocity>, HashSet<Acceleration>> newnewVelocitymap = new HashMap<Entry<Position, Velocity>, HashSet<Acceleration>>();
//			for (Entry<Position, Velocity> winconfig : newVelocitymap.keySet()) {
//				Position pullposition = winconfig.getKey().translocate(
//						winconfig.getValue().revert());
//				if (track.contains(pullposition))
//					for (Acceleration offset : offsets) {
//						Velocity pullVelocity = winconfig.getValue()
//								.accelerate(offset);
//						newnewVelocitymap
//						.get(new AbstractMap.SimpleImmutableEntry<Position, Velocity>(
//								pullposition, pullVelocity)).add(
//										offset.revert());
//					}
//			}
//		}
//	}
	
	//Version 2
	
	@SuppressWarnings("serial")
	private class Configuration extends SimpleImmutableEntry<Position, Velocity> {
		
		public Configuration(Position p, Velocity v) {
			super(p,v);
			// TODO Auto-generated constructor stub
		}
		
	}
	
	private HashMap<Configuration, HashMap<Acceleration, Integer>> utility=new HashMap<Configuration, HashMap<Acceleration, Integer>>();
	
	public GurKI(Game game) {
		
	}
	
	public class GurBot extends Player {
		private Position position;
		private Velocity velocity = new Velocity(0, 0);
	
		@Override
		protected void onLoad() {
			// TODO Auto-generated method stub getGame
		}
	
		@Override
		protected void onUpdatePosition(Point oldPos, Point newPos) {
			this.position = new Position(newPos.getX(), newPos.getY(),
					newPos.getMap());
		}
	
		@Override
		protected void onUpdateVelocity(Vec2D oldVelocity, Vec2D newVelocity) {
			this.velocity = new Velocity(newVelocity.x, newVelocity.y);
		}
	
		@Override
		public Point turn() {
			return position.translocate(velocity.accelerate(offsets[(int) (Math
					.random() * 8)]));
		}
	}
}