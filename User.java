import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;

public class User {
	private int id;
	private String username;
	private int money;
	private int wins;
	private int losses;
	private int opponentId;
	private HashMap<String, Integer> items;
	private Pokemon [] pokemons; 
	private Pokemon current_pokemon; 
	
	public User(){
		this.setItems(new HashMap<String, Integer>());
	}
	
	public User(int id, String username, int money, int wins, int losses, HashMap<String, Integer> items){
		this.setUsername(username);
		this.setMoney(money);
		this.setWins(wins);
		this.setLosses(losses);
		if(items != null) {
			this.setItems(items);
		} else {
			this.setItems(new HashMap<String, Integer>());
		}
	}
	
	public int getID() {
		return this.id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getOpponentID() {
		return this.opponentId;
	}
	
	public void setOpponentID(int id) {
		this.opponentId = id;
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
	
	public void updateItem(String itemName, int quantity) {
		if(this.items.containsKey(itemName)) {
			this.items.put(itemName, new Integer(this.items.get(itemName).intValue() + quantity));
		} else {
			this.items.put(itemName, new Integer(quantity));
		}
	}
	
	public int getItemQuantity(String itemName) {
		if(!this.items.containsKey(itemName)) {
			System.out.println("MISSING: " + itemName);
			//throw
		} 
		return this.items.get(itemName).intValue();
	}

	public void setItems(HashMap<String, Integer> items) {
		this.items = items;
	}

	public Pokemon getCurrentPokemon() { 
		return current_pokemon; 
	}
	
	public void print() {
		System.out.println("ID: " + this.getID());
		System.out.println("Username: " + this.getUsername());
		System.out.println("Money: " + this.getMoney());
		System.out.println("Wins: " + this.getWins());
		System.out.println("Losses: "+ this.getLosses());
		System.out.println("OpponentID: " + this.getOpponentID());
	}
	
}