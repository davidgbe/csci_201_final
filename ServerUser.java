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
import java.util.concurrent.locks.ReentrantLock;


public class ServerUser extends User implements Runnable {
	
	private Socket userSocket;
	ObjectOutputStream out;
	
	public static final String DB_ADDRESS = "jdbc:mysql://localhost/";
	public static final String DB_NAME = "group_db";
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String USER = "root";
	public static final String PASSWORD = "";
	
	public static final int STEROIDS_PRICE = 50;
	public static final int MORPHINE_PRICE = 50;
	public static final int EPINEPHRINE_PRICE = 50;
	
	private static ReentrantLock lock = new ReentrantLock();
	
	public ServerUser(Socket s) {
		super(-1, "", 0, 0, 0, new HashMap<String, Integer>());
		this.userSocket = s;
		try {
			out = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean login(String username, String pass, Connection con) {
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
			PreparedStatement stmt = con.prepareStatement("INSERT INTO users (wins, total_games, money, steroids, morphine, epinephrine) VALUES (?, ?, ?, ?, ?, ?) WHERE id = ?;");
			stmt.setInt(1, this.getWins());
			stmt.setInt(2, this.getWins() + this.getLosses());
			stmt.setInt(3, this.getMoney());
			stmt.setInt(4, this.getItems().get("steroids").intValue());
			stmt.setInt(5, this.getItems().get("morphine").intValue());
			stmt.setInt(6, this.getItems().get("epinephrine").intValue());
			stmt.setInt(7, this.getID());
			stmt.execute();
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
			System.out.println("steroids: " + results.getInt("steroids"));
			System.out.println("epinephrine: " + results.getInt("epinephrine"));
			System.out.println("epinephrine: " + results.getInt("epinephrine"));
			
		} catch(Exception e) {
			e.printStackTrace();
			//lock.unlock();
			return false;
		} finally {
			//lock.unlock();
			return false;
		}
	}
	
	private void parse(Message msg) {
		if(msg instanceof Login) {
			boolean succeeded = this.login(((Login) msg).getUsername(), ((Login) msg).getPassword(), null);
			try {
				out.writeObject(new LoginAuthenticated(succeeded, false));
				out.writeObject(new UserUpdate(this.getID(), this.getUsername(), this.getMoney(), this.getWins(), this.getLosses(), this.getOpponentID(), this.getItemQuantity("steroids"), this.getItemQuantity("morphine"), this.getItemQuantity("epinephrine")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(msg instanceof NewUser) {
			boolean succeeded = this.createUser(((NewUser) msg).getUsername(), ((NewUser) msg).getPassword());
			try {
				out.writeObject(new LoginAuthenticated(succeeded, true));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(msg instanceof PurchaseUpdate) {
			processPurchase((PurchaseUpdate)msg);
		}
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
			
		} catch (Exception ioe) {
			System.out.println("IOException in ServerUser run method: " + ioe.getMessage());
			// don't print stack trace for a user simply quiting the program
			if(!(ioe instanceof SocketException)){
				ioe.printStackTrace();
			}
		}
	}

}
