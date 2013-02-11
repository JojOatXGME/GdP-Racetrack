package gdp.racetrack;

import java.util.List;

public class HumanPlayer extends Player{
	Z_Game g;
	
	public HumanPlayer(){
		this.g=null;
	}
	
	public HumanPlayer(Z_Game g)
	{
		if (g==null){}
		else this.g=g;
	}
	
	@Override
	protected Point turn() {
		
		Turn[] Ty = this.getGame().getRule().getAllowedTurns(this);
		for (int i=0;i<Ty.length;i++){
			int zx=Ty[i].getNewPosition().getX()*16;
			int zy=Ty[i].getNewPosition().getY()*16;		
		}
		return null;
	}

	@Override
	public Point chooseStart(List<Point> possiblePositions) {
		return (new Point(this.g.ox/16,this.g.oy/16));
	}

}
