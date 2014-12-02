import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;


public class ServerUser extends User implements Runnable {
	
	private Socket userSocket;
	ObjectOutputStream out;
	Vector<ServerUser> allUsers;
	Server server;
	ServerUser opponent;
	
	public static final String DB_ADDRESS = "jdbc:mysql://localhost/";
	public static final String DB_NAME = "group_db";
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String USER = "root";
	public static final String PASSWORD = "";
	
	public static final int STEROIDS_PRICE = 15;
	public static final int MORPHINE_PRICE = 25;
	public static final int EPINEPHRINE_PRICE = 50;
	
	private static ReentrantLock lock = new ReentrantLock();
	private static DamageCalculator dc = new DamageCalculator();
	
	public ServerUser(Socket s, Vector<ServerUser> allUsers, Server server) {
		super(-1, "", 0, 0, 0, new HashMap<String, Integer>());
		this.userSocket = s;
		this.allUsers = allUsers;
		this.server = server;
		try {
			out = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean login(String username, String pass, Connection con) {
		for(User user : allUsers) {
			if(user.getUsername().equals(username)) {
				return false;
			}
		}
		if(con == null) {
			con = establishConnection();
		}
		if(con == null) {
			System.out.println("Connection to DB is NULL");
			return false;
		}
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("SELECT id FROM users WHERE username = ? AND password = ?;");
			stmt.setString(1, username);
			stmt.setString(2, pass);
			ResultSet results = stmt.executeQuery();
			if(results.next()) {
				this.setID(results.getInt("id"));
				this.setUsername(username);
				this.fetch();
				return true;
			} 
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createUser(String username, String pass) {
		Connection con = establishConnection();
		
		if(con == null) {
			System.out.println("Connection to db is NULL");
			return false;
		}
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("SELECT id FROM users WHERE username = ?;");
			stmt.setString(1, username);
			ResultSet results = stmt.executeQuery();
			
			if(results.next()) {
				System.out.println(userSocket.getRemoteSocketAddress().toString() + " tried to register with an existing name");
				return false;
			}
			PreparedStatement stmt2 = con.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?);");
			
			stmt2.setString(1, username);
			stmt2.setString(2, pass);
			stmt2.execute();
			return login(username, pass, con);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private Connection establishConnection() {
		Connection con = null;
		try {
			con =  DriverManager.getConnection(this.DB_ADDRESS + this.DB_NAME, this.USER, this.PASSWORD);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public boolean update() {
		//lock.lock();
		try {
			Connection con = establishConnection();
			if(con == null) {
				System.out.println("Unable to establish connection");
				return false;
			}
			PreparedStatement stmt = con.prepareStatement("UPDATE users SET wins=?, total_games=?, money=?, steroids=?, morphine=?, epinephrine=? where id=?;");
			stmt.setInt(1, this.getWins());
			stmt.setInt(2, this.getWins() + this.getLosses());
			stmt.setInt(3, this.getMoney());
			stmt.setInt(4, this.getItems().get("steroids").intValue());
			stmt.setInt(5, this.getItems().get("morphine").intValue());
			stmt.setInt(6, this.getItems().get("epinephrine").intValue());
			stmt.setInt(7, this.getID());
			stmt.execute();
			System.out.println("Updated SQL database for user with ID:" + this.getID());
		} catch(Exception e) {
			e.printStackTrace();
			//lock.unlock();
			return false;
		} finally {
			//lock.unlock();
			return true;
		}
	}
	
	public boolean fetch() {
		//lock.lock();
		Connection con = establishConnection();
		if(con == null) {
			//badddd
			//lock.unlock();
			return false;
		}
		try {
			Statement stmt = con.createStatement();
			if(con == null) {
				System.out.println("Unable to establish connection");
				//lock.unlock();
				return false;
			}
			ResultSet results = stmt.executeQuery("SELECT wins, total_games, money, steroids, morphine, epinephrine FROM users WHERE id = " + this.getID() + ";");
			if(results == null) {
				System.out.println("There were no results");
				//lock.unlock();
				return false;
			}
			results.next();
			this.setWins( results.getInt("wins") );
			this.setLosses( results.getInt("total_games") - this.getWins() );
			this.setMoney( results.getInt("money") );
			this.getItems().put("steroids", results.getInt("steroids"));
			this.getItems().put("morphine", results.getInt("morphine"));
			this.getItems().put("epinephrine", results.getInt("epinephrine"));
			
		} catch(Exception e) {
			e.printStackTrace();
			//lock.unlock();
			return false;
		} finally {
			//lock.unlock();
			return false;
		}
	}
	
	public void startBattle(ServerUser opponent, boolean start) {
		try {
			System.out.println("Starting a battle");
			Pokemon myPokemon =  this.getCurrentPokemon();
			Pokemon oPokemon = opponent.getCurrentPokemon();
			this.opponent = opponent;
			BattleData startData = new BattleData(this.getID(), myPokemon.getName(), oPokemon.getName(), myPokemon.getHealthPoints(), oPokemon.getHealthPoints(), myPokemon.getStrength(), oPokemon.getStrength());
			if(!start) {
				startData.setId(opponent.getID());
			} 
			out.writeObject(startData);
			this.setInBattle(true);
			opponent.setInBattle(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void parse(Message msg) {
		if(msg instanceof Login) {
			boolean succeeded = this.login(((Login) msg).getUsername(), ((Login) msg).getPassword(), null);
			try {
				out.writeObject(new LoginAuthenticated(succeeded, false));
				UserUpdate uu = new UserUpdate(this.getID(), this.getUsername(), this.getMoney(), this.getWins(), this.getLosses(), this.getOpponentID(), this.getItemQuantity("steroids"), this.getItemQuantity("morphine"), this.getItemQuantity("epinephrine"));
				out.writeObject(uu);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(msg instanceof NewUser) {
			boolean succeeded = this.createUser(((NewUser) msg).getUsername(), ((NewUser) msg).getPassword());
			try {
				out.writeObject(new LoginAuthenticated(succeeded, true));
				UserUpdate uu = new UserUpdate(this.getID(), this.getUsername(), this.getMoney(), this.getWins(), this.getLosses(), this.getOpponentID(), this.getItemQuantity("steroids"), this.getItemQuantity("morphine"), this.getItemQuantity("epinephrine"));
				out.writeObject(uu);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(msg instanceof PurchaseUpdate) {
			System.out.println("User with ID " + this.getID() + " purchased an item");
			processPurchase((PurchaseUpdate)msg);
		} else if(msg instanceof ChatMessage){
			System.out.println("Received chat message from " + this.getUsername());
			ChatMessage messageReceived = (ChatMessage)msg;
			// forward message to all other users
			System.out.println("Forwarding message onto all other users");
			for(int i = 0; i < allUsers.size(); ++i){
				if(!(allUsers.get(i) == this)){
					try {
						allUsers.get(i).out.writeObject(messageReceived);
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else if(msg instanceof QueueMe) {
			System.out.println("User with ID " + this.getID() + " added to battle queue");
			this.server.addToQueue(this);
		} else if(msg instanceof PokemonUpdate) {
			System.out.println("Received pokemon from user with ID " + this.getID());
			this.getPokemons().clear();
			String[] names = ((PokemonUpdate)msg).getPokemon();
			for(String name : names) {
				if(name != null) {
					this.addPokemon(name);
				}
			}
			if(names[0] != null) {
				this.setCurrentPokemon(this.getPokemon(names[0]));
			}
		} else if(msg instanceof Item) {
			System.out.println("User with ID " + this.getID() + " used an item in battle");
			processItem((Item)msg);
		} else if(msg instanceof Attack) {
			System.out.println("User with ID " + this.getID() + " attacked in battle");
			processAttack((Attack)msg);
		} else if(msg instanceof Switch) {
			System.out.println("User with ID " + this.getID() + " switched pokemon in battle");
			processSwitch((Switch)msg);
		}
	}

//Three methods for recognition of moves coming from client
	
	public void processItem(Item item) {
		Pokemon myP = this.getCurrentPokemon();
		Pokemon oP = this.opponent.getCurrentPokemon();
		
		if(item.getType().equals("steroids")) {
			//appropriate math for steroids
			myP.setStrength(myP.getStrength() + 50);
			this.updateItem("steroids", -1);
			BattleData steroidData = new BattleData(this.getID(), "steroids", myP.getName(), myP.getHealthPoints(), myP.getStrength());
			updateClients(steroidData);
		} else if(item.getType().equals("morphine")) {
			myP.setHealthPoints(myP.getHealthPoints() + 200);
			this.updateItem("morphine", -1);
			BattleData morphineData = new BattleData(this.getID(), "morphine", myP.getName(), myP.getHealthPoints(), myP.getStrength());
			updateClients(morphineData);
		} else  if(item.getType().equals("epinephrine")) {
			Pokemon targetP = this.getPokemon(item.getPokemon());
			targetP.setHealthPoints(targetP.getTotalHealthPoints());
			if(targetP.isDead()) {
				targetP.setDead(false);
			}
			this.updateItem("epinephrine", -1);
			BattleData eData = new BattleData(this.getID(), "epinephrine", targetP.getName(), targetP.getHealthPoints(), targetP.getStrength());
			updateClients(eData);
		}
	}
	
	public void processAttack(Attack attack) {
		Pokemon myP = this.getCurrentPokemon();
		Pokemon oP = this.opponent.getCurrentPokemon();
		boolean allDead = false;
		
		oP.setHealthPoints( (int)(oP.getHealthPoints() - dc.getTotalDamage(attack.getName(), myP)) );
		if(oP.getHealthPoints() < 0) {
			allDead = true;
			oP.setHealthPoints(0);
			oP.setDead(true);
			for(Pokemon p : this.opponent.getPokemons()) {
				if(!p.isDead()) {
					allDead = false;
				}
			}
		}
		BattleData attackData = new BattleData(this.getID(), attack.getName(), myP.getName(), oP.getHealthPoints());
		this.updateClients(attackData);
		if(allDead) {
			endGame();
		}
	}
	
	public void processSwitch(Switch switchObj) {
		this.setCurrentPokemon(this.getPokemon(switchObj.getNewPokemon()));
		Pokemon myP = this.getCurrentPokemon();
		BattleData switchData = new BattleData(this.getID(), myP.getName(), myP.getHealthPoints(), myP.getStrength());
		this.updateClients(switchData);
	}
	
//one method to forward BattleData to clients
	
	public void updateClients(BattleData bd) {
		this.sendMessageToClient(bd);
		if(this.opponent != null) {
			this.opponent.sendMessageToClient(bd);
		}
	}
	
//to notify winners 
	
	public void endGame() {
		GameOver go = new GameOver(this.getID());
		this.sendMessageToClient(go);
		this.opponent.sendMessageToClient(go);
		
		this.setInBattle(false);
		this.setWins(this.getWins() + 1);
		this.setMoney(this.getMoney() + 200);
		this.update();
		UserUpdate uu = new UserUpdate(this.getID(), this.getUsername(), this.getMoney(), this.getWins(), this.getLosses(), this.getOpponentID(), this.getItemQuantity("steroids"), this.getItemQuantity("morphine"), this.getItemQuantity("epinephrine"));
		this.sendMessageToClient(uu);
		
		
		this.opponent.lose();
		this.opponent = null;
	}
	
	public void lose() {
		this.setInBattle(false);
		this.setLosses(this.getLosses() + 1);
		
		this.update();
		UserUpdate uu = new UserUpdate(this.getID(), this.getUsername(), this.getMoney(), this.getWins(), this.getLosses(), this.getOpponentID(), this.getItemQuantity("steroids"), this.getItemQuantity("morphine"), this.getItemQuantity("epinephrine"));
		this.sendMessageToClient(uu);
		
		this.opponent = null;
		
	}

	private void processPurchase(PurchaseUpdate pu) {
		int total = 0;
		if(pu.getSteroids() != 0) {
			total += pu.getSteroids() * STEROIDS_PRICE;
		}
		if(pu.getMorphine() != 0) {
			total += pu.getMorphine() * MORPHINE_PRICE;
		}
		if(pu.getEpinephrine() != 0) {
			total += pu.getEpinephrine() * EPINEPHRINE_PRICE;
		}
		if(this.getMoney() >= total) {
			this.setMoney(this.getMoney() - total);
			this.updateItem("steroids", pu.getSteroids());
			this.updateItem("morphine", pu.getMorphine());
			this.updateItem("epinephrine", pu.getEpinephrine());
			if(!this.update()) {
				//trouble connecting to server
				try {
					out.writeObject(new PurchaseUpdate(this.getID(), 0, false, 0, 0, 0));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					out.writeObject(new PurchaseUpdate(this.getID(), this.getMoney(), true, this.getItemQuantity("steroids"), this.getItemQuantity("morphine"), this.getItemQuantity("epinephrine")));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// didn't have enough money, send failed message
		else{
			try {
				out.writeObject(new PurchaseUpdate(this.getID(), this.getMoney(), false, 0, 0, 0));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendMessageToClient(Object obj) {
		try {
			this.out.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			ObjectInputStream in  = new ObjectInputStream(userSocket.getInputStream());
			while(true){
				Object objectReceived = in.readObject();
				if(objectReceived instanceof Message){
					Message messageReceived = (Message)objectReceived;
					parse(messageReceived);
				}
			}
			
		} catch(IOException eofe) {
			if(this.isInBattle()) {
				this.opponent.sendMessageToClient(new GameOver(this.opponent.getID()));
				this.opponent.setInBattle(false);
				System.out.println("Player disconnected. Send game over to his opponent");
				//exit battle
			}
			allUsers.remove(this);
			System.out.println(this.getUsername() + " quit");

		} catch (Exception ioe) {
			System.out.println("General exception in ServerUser run method: " + ioe.getMessage());
		} 
	}

}
