
public class GameOver extends Message {
	public int winnerId;
	
	public GameOver(int id) {
		this.winnerId = id;
	}
	
	public int getWinnerID() {
		return this.winnerId;
	}
}
