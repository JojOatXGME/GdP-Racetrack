package gdp.racetrack;

public interface EventListener {

	public void onPlayerTurn(Player player, Point startPoint, Point endPoint, Point destinationPoint);

	public void onMapUpdate(Map map);

}
