package gdp.racetrack;

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
