package gdp.racetrack;

import gdp.racetrack.Turn.TurnType;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import com.sun.corba.se.impl.orbutil.graph.Graph;

public class GurKI implements Runnable{
	
	private class Position extends Point {
		Position(int x, int y) {
			super(x, y);
		}
		
		Position translocate(Velocity Velocity) {
			return new Position(x + Velocity.x, y + Velocity.y);
		}
		
		Velocity sub(Position position) {
			return new Velocity(x - position.x, y-position.y);
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
	private static class Configuration extends SimpleImmutableEntry<Position, Velocity> {
		
		Configuration(Position p, Velocity v) {
			super(p,v);
			// TODO Auto-generated constructor stub
		}
		
		Configuration turn(Acceleration a) {
			return new Configuration(getKey().translocate(getValue()),getValue().accelerate(a));
		}
		
		Configuration(Position p, Position p2) {
			super(p,p2.sub(p));
		}
	}
	
	private HashSet<Position> track=new HashSet<Position>();
//	private HashMap<Configuration, HashSet<Acceleration>> turns=new HashMap<Configuration, HashSet<Acceleration>>();
	
	private Game game;
	public GurKI(Game game) {
		this.game=game;
		Vec2D mapsize=game.getMap().getSize();
//		Velocity rest=new Velocity(0,0);
		for(int x=0;x<mapsize.x;x++)
			for(int y=0;y<mapsize.y;y++) {
				Position position=new Position(x,y);
				switch(game.getMap().getPointType(position)) {
				case START:
//					newturns.put(new Configuration(position,rest),new HashSet<Acceleration>());
				case TRACK:
					track.add(position);
				default:
				}
			}
		Thread analyzer=new Thread(this);
		analyzer.setPriority(Thread.MIN_PRIORITY);
		analyzer.start();
	}

//	private HashMap<Configuration, HashSet<Acceleration>> newturns=new HashMap<Configuration, HashSet<Acceleration>>();
//	public void run() {
//		// TODO Auto-generated method stub
//		while(!newturns.isEmpty()) {
//			HashMap<Configuration, HashSet<Acceleration>> newnewturns=new HashMap<Configuration, HashSet<Acceleration>>();
//			for(Configuration config : newturns.keySet()) {
//				HashSet<Acceleration> accelerations=newturns.get(config);
//				for(Acceleration offset : offsets) {
//					Configuration newconfig=config.turn(offset);
//					if(!turns.containsKey(newconfig) && !newturns.containsKey(newconfig)) {
//						accelerations.add(offset);
//						newnewturns.put(newconfig, new HashSet<Acceleration>());
//					}
//				}
//			}
//			turns.putAll(newturns);
//			newturns=newnewturns;
//		}
//	}
	
	HashMap<Configuration, Double> utility=new HashMap<Configuration, Double>();
	HashMap<Configuration, HashSet<Configuration>> from=new HashMap<Configuration, HashSet<Configuration>>();
	HashMap<Configuration, HashSet<Configuration>> to=  new HashMap<Configuration, HashSet<Configuration>>();
	public void run() {
		for(Position positionfrom : track)
			for(Position positionto : track) {
				TurnType turntype = game.getRule().getTurnResult(positionfrom, positionto).getturnType();
				switch(turntype) {
				case COLLISION_ENVIRONMENT:
				case COLLISION_PLAYER:
				case FORBIDDEN:
					break;
				case OK:
				case FINISH:
				case FINISH_LOOSE:
				case FINISH_WIN:
					Configuration fromconfig=new Configuration(positionfrom, positionto);
					for(Acceleration offset : offsets) {						
						Configuration newconfig=fromconfig.turn(offset);
						addToGraph(newconfig, fromconfig, from);
						addToGraph(fromconfig, newconfig, to);
						switch (turntype) {
						case OK:
							utility.put(newconfig,1.0);
							break;
						default:
							utility.put(newconfig,1000.0);
							break;
						}
					}
					break;
				default:
					break;
				}
			}
		for(Configuration config:to.keySet())
			if(game.getMap().getPointType(point))
			for(Configuration fromconfig:from.get(config))
		while(true) {
			HashMap<Configuration, Double> newtility=new HashMap<Configuration, Double>();
			for(Configuration config:utility.keySet())
				for(Configuration fromconfig:from.get(config))
					newtility
		}
	}

	private void addToGraph(Configuration valueconfig, Configuration keyconfig,
			HashMap<Configuration, HashSet<Configuration>> graph) {
		HashSet<Configuration> set = graph.get(valueconfig);
		if (set == null) {
			set = new HashSet<Configuration>();
			graph.put(valueconfig, set);
		}
		set.add(keyconfig);
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