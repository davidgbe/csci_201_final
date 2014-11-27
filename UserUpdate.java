import java.io.Serializable;


public class UserUpdate implements Serializable{
	
	private int id;
	private String username;
	private int money;
	private int wins;
	private int losses;
	private int opponentId;
	
	private int steroids;
	private int morphine;
	private int epinephrine;
	
	public UserUpdate(int id, String username, int money, int wins, int losses, int opponentId, int steroids, int morphine, int epinephrine) {
		this.steroids = steroids;
		this.morphine = morphine;
		this.epinephrine = epinephrine;
		this.id = id;
		this.username = username;
		this.money = money;
		this.wins = wins;
		this.losses = losses;
		this.opponentId = opponentId;
	}
	
	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public int getOpponentID() {
		return opponentId;
	}

	public void setOpponentID(int opponentId) {
		this.opponentId = opponentId;
	}

	public int getSteroids() {
		return steroids;
	}

	public void setSteroids(int steroids) {
		this.steroids = steroids;
	}

	public int getMorphine() {
		return morphine;
	}

	public void setMorphine(int morphine) {
		this.morphine = morphine;
	}

	public int getEpinephrine() {
		return epinephrine;
	}

	public void setEpinephrine(int epinephrine) {
		this.epinephrine = epinephrine;
	}
	
	
}
