import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class User {
	private int id;
	private String username;
	private int money;
	private int wins;
	private int losses;
	private int opponentId;
	private HashMap<String, Integer> items;
	private ArrayList<Pokemon> pokemons; 
	private Pokemon current_pokemon; 
	private boolean inBattle;
	
	public User(){
		this.setItems(new HashMap<String, Integer>());
		pokemons = new ArrayList<Pokemon>();
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
	
	public boolean isInBattle() {
		return inBattle;
	}
	
	public void setInBattle(boolean inBattle) {
		this.inBattle = inBattle;
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
			return 0;
		} 
		return this.items.get(itemName).intValue();
	}

	public void setItems(HashMap<String, Integer> items) {
		this.items = items;
	}

	public Pokemon getCurrentPokemon() { 
		return current_pokemon; 
	}

	public ArrayList<Pokemon> getPokemons() {
		return pokemons;
	}
	
	public boolean addPokemon(String name) {
		if(!this.pokemons.contains(name)) {
			if(name.equals("venusaur")){pokemons.add(new Venusaur());}
			else if(name.equals("blastoise")){pokemons.add(new Blastoise());}
			else if(name.equals("charizard")){pokemons.add(new Charizard());}
			else if(name.equals("dragonite")){pokemons.add(new Dragonite());}
			else if(name.equals("gyrados")){pokemons.add(new Gyarados());}
			else if(name.equals("hitmonchan")){pokemons.add(new Hitmonchan());}
			else if(name.equals("lapras")){pokemons.add(new Lapras());}
			else if(name.equals("mewtwo")){pokemons.add(new Mewtwo());}
			else if(name.equals("onix")){pokemons.add(new Onix());}
			else if(name.equals("pidgeot")){pokemons.add(new Pidgeot());}
			else if(name.equals("pikachu")){pokemons.add(new Pikachu());}
			else if(name.equals("rapidash")){pokemons.add(new Rapidash());}
			else if(name.equals("rhydon")){pokemons.add(new Rhydon());}
			else if(name.equals("scyther")){pokemons.add(new Scyther());}
			else if(name.equals("snorlax")){pokemons.add(new Snorlax());}
			return true;
		} 
		return false;
	}
	
	public boolean removePokemon(String name) {
		for(int i = 0; i < this.pokemons.size(); i++) {
			if(this.pokemons.get(i).getName().equals(name)) {
				this.pokemons.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public Pokemon getPokemon(String name) {
		for(Pokemon p : this.pokemons) {
			if(p.getName().equals(name)) {
				return p;
			}
		}
		//not found
		return null;
	}
	
	public void print() {
		System.out.println("ID: " + this.getID());
		System.out.println("Username: " + this.getUsername());
		System.out.println("Money: " + this.getMoney());
		System.out.println("Wins: " + this.getWins());
		System.out.println("Losses: "+ this.getLosses());
		System.out.println("OpponentID: " + this.getOpponentID());
	}

	public void setCurrentPokemon(Pokemon pokemon) { 
		current_pokemon = pokemon; 
	}
	
}