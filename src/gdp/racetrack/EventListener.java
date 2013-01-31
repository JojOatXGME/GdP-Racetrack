package gdp.racetrack;

public interface EventListener {

	public void onGameStart(boolean firstTime);

	public void onGamePause();

	public void onGameFinished();

	public void onPlayerChooseStart(Player player);

	public void onPlayerTurn(Player player, Point startPoint, Point endPoint, Point destinationPoint);

}
