package gdp.racetrack;

import java.awt.Image;

public class Map {

	public enum PointType {
		/**
		 * On points of this type is nothing.
		 * There are also not part of the track.
		 */
		NONE,
		/**
		 * Points of this type are just a part of the track.
		 */
		TRACK,
		/**
		 * This Points are possible start points.
		 * Points of this type are also part of the track.
		 */
		START,
		/**
		 * If a Player cross a point of this type it will normally finish the game.
		 * Points of this type are also part of the track.
		 */
		FINISH
	}

	/**
	 * Checks whether the given Point is an port of the track or not.
	 * <br>
	 * This method will return true by all parts of the track
	 * included START and FINISH.
	 * 
	 * @param point The point to check whether it is a part of the track
	 * @return true if the point is part of the track, false otherwise
	 */
	public boolean isTrack(Point point) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Gets the type of the given point.
	 * @param point The point to check the type
	 * @return The Type of the point
	 */
	public PointType getPointType(Point point) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Gets the size of the of the map.
	 * @return The size of the map
	 */
	public Vec2D getSize() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Gets an Image of map.
	 * <br>
	 * [Better description?]
	 * 
	 * @return The image of the map
	 */
	public Image getImage() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
