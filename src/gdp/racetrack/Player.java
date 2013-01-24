package gdp.racetrack;

import java.util.LinkedList;
import java.util.List;

public abstract class Player {

	private final LinkedList<Vec2D> turnHistory = new LinkedList<Vec2D>();

	public abstract Vec2D turn();

	public final Vec2D getPosition() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public final Vec2D getVelocity() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public final List<Vec2D> getTurnHistory() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public final void setPosition(Vec2D position) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
