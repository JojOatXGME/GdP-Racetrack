package gdp.racetrack;

public class TurnRule_8 implements TurnRule{

	@Override
	public void init(final Game game) {
		// TODO Auto-generated method stub
	}

	public boolean isTurnAllowed(Player player, Point destination) {
		Point playerturn = player.turn();
		if (playerturn.getX() == destination.getX()-1 && playerturn.getY() == destination.getY()-1) {
			return true;
		}
		else if (playerturn.getX() == destination.getX() && playerturn.getY() == destination.getY()-1) {
			return true;
		}
		else if (playerturn.getX() == destination.getX()+1 && playerturn.getY() == destination.getY()-1) {
			return true;
		}
		else if (playerturn.getX() == destination.getX()-1 && playerturn.getY() == destination.getY()) {
			return true;
		}
		else if (playerturn.getX() == destination.getX()+1 && playerturn.getY() == destination.getY()) {
			return true;
		}
		else if (playerturn.getX() == destination.getX()-1 && playerturn.getY() == destination.getY()+1) {
			return true;
		}
		else if (playerturn.getX() == destination.getX() && playerturn.getY() == destination.getY()+1) {
			return true;
		}
		else if (playerturn.getX() == destination.getX()+1 && playerturn.getY() == destination.getY()+1) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public boolean isTurnAllowed(Point start, Vec2D velocity, Point destination) {
		
		Vec2D realdestination = new Vec2D(start.getX() + velocity.x,start.getY() + velocity.y);
		
		if (destination.getX() == realdestination.x -1 && destination.getY() == realdestination.y -1) {
			return true;
		}
		else if (destination.getX() == realdestination.x && destination.getY() == realdestination.y -1) {
			return true;
		}
		else if (destination.getX() == realdestination.x +1 && destination.getY() == realdestination.y -1) {
			return true;
		}
		else if (destination.getX() == realdestination.x -1 && destination.getY() == realdestination.y) {
			return true;
		}
		else if (destination.getX() == realdestination.x +1 && destination.getY() == realdestination.y) {
			return true;
		}
		else if (destination.getX() == realdestination.x -1 && destination.getY() == realdestination.y +1) {
			return true;
		}
		else if (destination.getX() == realdestination.x && destination.getY() == realdestination.y +1) {
			return true;
		}
		else if (destination.getX() == realdestination.x +1 && destination.getY() == realdestination.y +1) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public Point[] getAllowedTurns(Player player) {
		return getAllowedTurns(player.getPosition(), player.getVelocity());
	}

	@Override
	public Point[] getAllowedTurns(final Point position, final Vec2D velocity) {
		Point[] allowedturns = new Point[8];
		Point newposition = position.add(velocity);
		
		int x = newposition.getX();
		int y = newposition.getY();
		// x-1 & y-1
		x = x -1;
		y = y -1;
		Point turnone = new Point(x,y);
		allowedturns[0] = turnone;
		// x & y-1
		x = x +1;
		Point turntwo = new Point(x,y);
		allowedturns[1] = turntwo;
		// x+1 & y-1
		x = x +1;
		Point turnthree = new Point(x,y);
		allowedturns[2] = turnthree;
		// x-1 & y
		x = x -2;
		y = y +1;
		Point turnfour = new Point(x,y);
		allowedturns[3] = turnfour;
		// x+1 & y
		x = x +2;
		Point turnfive = new Point(x,y);
		allowedturns[4] = turnfive;
		// x-1 & y+1
		x = x -2;
		y = y +1;
		Point turnsix = new Point(x,y);
		allowedturns[5] = turnsix;
		// x & y+1;
		x = x +1;
		Point turnseven = new Point(x,y);
		allowedturns[6] = turnseven;
		// x+1 & y+1
		x = x +1;
		Point turneight = new Point(x,y);
		allowedturns[7] = turneight;
		
		return allowedturns;
	}

}
