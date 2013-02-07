package gdp.racetrack;

public class TurnRule_8 implements TurnRule{
	
	public static boolean isTurnAllowed(Player player, Point destination) {
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
		
		Vec2D realdestination =new Vec2D(start.getX() + velocity.x, start.getY() + velocity.y);
		
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
	
	public Vec2D[] getAllowedTurns(Player player) {
		
		Vec2D[] allowedturns = new Vec2D[7];
		Point currentplayer = player.getPosition();
		Vec2D currentvelocity = player.getVelocity();
		Vec2D currentposition = currentplayer.getVec();
		Vec2D newposition = currentposition.add(currentvelocity);
		
		int x = newposition.x;
		int y = newposition.y;
		// x-1 & y-1
		x = x -1;
		y = y -1;
		Vec2D turnone = new Vec2D(x,y);
		allowedturns[0] = turnone;
		// x & y-1
		x = x +1;
		Vec2D turntwo = new Vec2D(x,y);
		allowedturns[1] = turntwo;
		// x+1 & y-1
		x = x +1;
		Vec2D turnthree = new Vec2D(x,y);
		allowedturns[2] = turnthree;
		// x-1 & y
		x = x -2;
		y = y +1;
		Vec2D turnfour = new Vec2D(x,y);
		allowedturns[3] = turnfour;
		// x+1 & y
		x = x +2;
		Vec2D turnfive = new Vec2D(x,y);
		allowedturns[4] = turnfive;
		// x-1 & y+1
		x = x -2;
		y = y +1;
		Vec2D turnsix = new Vec2D(x,y);
		allowedturns[5] = turnsix;
		// x & y+1;
		x = x +1;
		Vec2D turnseven = new Vec2D(x,y);
		allowedturns[6] = turnseven;
		// x+1 & y+1
		x = x +1;
		Vec2D turneight = new Vec2D(x,y);
		allowedturns[7] = turneight;
		
		return allowedturns;
	}
	
	public static void main(String[] args) {
	}

}
