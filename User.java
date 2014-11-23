import java.util.HashMap;

public class User{
	private String username;
	private int money;
	private int wins;
	private int losses;
	private HashMap<String, Integer> items;
	
	public User(String username, int money, int wins, int losses, HashMap<String, Integer> items){
		this.setUsername(username);
		this.setMoney(money);
		this.setWins(wins);
		this.setLosses(losses);
		this.setItems(items);
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

	public HashMap<String, Integer> getItems() {
		return items;
	}

	public void setItems(HashMap<String, Integer> items) {
		this.items = items;
	}
	
}