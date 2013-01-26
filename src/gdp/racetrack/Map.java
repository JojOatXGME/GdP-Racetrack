package gdp.racetrack;

import java.awt.Image;
import java.awt.image.BufferedImage;

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
	
	public Map(BufferedImage image) {
		mapImage = image;
		mapData = new PointType[image.getWidth()][image.getHeight()];
		for(int x=0; x<image.getWidth(); x++){
			for(int y=0; y<image.getHeight(); y++){
				switch(image.getRGB(x,y)&0xFFFFFF){
					case COLOR_TRACK:
						mapData[x][y] = PointType.TRACK;
						break;
					case COLOR_START:
						mapData[x][y] = PointType.START;
						break;
					case COLOR_FINISH:
						mapData[x][y] = PointType.FINISH;
						break;
					default:
						mapData[x][y] = PointType.NONE;
				}				
			}
		}
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
		return mapData[point.getVec().x*16][point.getVec().y*16] != PointType.NONE;
	}

	/**
	 * Gets the type of the given point.
	 * @param point The point to check the type
	 * @return The Type of the point
	 */
	public PointType getPointType(Point point) {
		return mapData[point.getVec().x*16][point.getVec().y*16];
	}

	/**
	 * Gets the size of the of the map.
	 * @return The size of the map
	 */
	public Vec2D getSize() {
		return new Vec2D(mapImage.getWidth(null), mapImage.getHeight(null));
	}

	/**
	 * Gets an Image of map.
	 * <br>
	 * [Better description?]
	 * @return The image of the map
	 */
	public Image getImage() {
		return mapImage;
	}
	

	private final PointType mapData[][];
 	private final Image mapImage;
 	public static final int COLOR_TRACK  = 0xFFFFFF;
 	public static final int COLOR_START  = 0xFF0000;
 	public static final int COLOR_FINISH = 0x00FF00;
	public static final int COLOR_BACKGROUND = 0xDCDCDC;

}
