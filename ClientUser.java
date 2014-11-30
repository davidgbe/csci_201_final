import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;


public class ClientUser extends User implements Runnable{

	Socket mySocket;
	ObjectInputStream in;
	ObjectOutputStream out;
	PokemonFrame pk; 
	
	String opponentPokemon;
	int opponentHealth;
	double opponentStrength;
	
	boolean myTurn = false;
	
	public ClientUser(){
		super();
		
		try {
			//TODO: DO NOT HARDCODE LOCALHOST
			mySocket = new Socket("127.0.0.1", 1234);
			in = new ObjectInputStream(mySocket.getInputStream());
			out = new ObjectOutputStream(mySocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setPokemonFrame(PokemonFrame pk) {
		this.pk = pk;
	}
	
	public ObjectOutputStream getOutputStream(){
		return out;
	}
	
	public void purchaseSteroids() {
		try {
			out.writeObject(new PurchaseUpdate(this.getID(), 0, true, 1, 0, 0));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void purchaseMorphine() {
		try {
			out.writeObject(new PurchaseUpdate(this.getID(), 0, true, 0, 1, 0));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void purchaseEpinephrine() {
		try {
			out.writeObject(new PurchaseUpdate(this.getID(), 0, true, 0, 0, 1));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void update(UserUpdate user) {
		
		this.setID(user.getID());
		this.setUsername(user.getUsername());
		this.setMoney(user.getMoney());
		this.setWins(user.getWins());
		this.setLosses(user.getLosses());
		this.setOpponentID(user.getOpponentID());
		if(this.getItems() == null) {
			this.setItems(new HashMap<String, Integer>());
		}
		this.updateItem("steroids", user.getSteroids());
		this.updateItem("morphine", user.getMorphine());
		this.updateItem("epinephrine", user.getEpinephrine());
	}
	
	private void processBattleData(BattleData bd) {
		if(!this.isInBattle()) {
			this.setInBattle(true);
			if(bd.getId() == this.getID()) {
				myTurn = true;
			}
			this.opponentPokemon = bd.getOpponentPokemon();
			this.opponentHealth = bd.getOpponentHealth(); 
			this.opponentStrength = bd.getOpponentStrength();
			this.setCurrentPokemon(this.getPokemon(bd.getMyPokemon()));
			this.pk.showBattle();
			if(this.myTurn) {
				this.pk.currentBattle.toggle();
			}
		} else {
			if(bd.getId() == this.getID()) {
				processOwnMove(bd);
			} else {
				processOpponentMove(bd);
			}
			pk.currentBattle.updateBattleUI();
		}
	}
	
	private void processOpponentMove(BattleData bd) {
		Pokemon myP = this.getCurrentPokemon();
		if(bd.getType().equals("attack")) {
			myP.setHealthPoints(bd.getOpponentHealth());
			myP.setStrength(bd.getOpponentStrength());
			pk.currentBattle.updateBattleUI();
			pk.currentBattle.setStatus(bd.getOpponentPokemon() + " used " + bd.getAttackName());
			if(myP.getHealthPoints() == 0) {
				this.getCurrentPokemon().setDead(true);
				pk.currentBattle.forceSwitchPokemon();
			}
		} else if(bd.getType().equals("item")) {
			if(bd.getItemName().equals("morphine")){
				this.opponentHealth = bd.getMyHealth();
				pk.currentBattle.setStatus(bd.getMyPokemon() + " used morphine! Health increased by 200");
				pk.currentBattle.updateBattleUI();
			} else if(bd.getItemName().equals("steroids")) {
				this.opponentStrength = bd.getMyStrength();
				System.out.println("opp new strength: " + bd.getMyStrength());
				pk.currentBattle.setStatus(bd.getMyPokemon() + " used steroids! Attack increased by 50");
			}
			else if(bd.getItemName().equals("epinephrine")) {
				if(bd.getMyPokemon().equals(this.opponentPokemon)) {
					this.opponentHealth = bd.getMyHealth();
				}
			}
		} else if(bd.getType().equals("switch")) {
			this.opponentPokemon = bd.getMyPokemon();
			this.opponentHealth = bd.getMyHealth();
			this.opponentStrength = bd.getMyStrength();
		}
		this.pk.currentBattle.toggle();
	}
	
	private void processOwnMove(BattleData bd) {
		Pokemon myP = this.getCurrentPokemon();
		if(bd.getType().equals("attack")) {
			this.opponentHealth = bd.getOpponentHealth();
			this.opponentStrength = bd.getOpponentStrength();
			pk.currentBattle.updateBattleUI();
			pk.currentBattle.setStatus(bd.getOpponentPokemon() + " used " + bd.getAttackName());
		} else if(bd.getType().equals("item")) {
			if(bd.getItemName().equals("morphine")){
				this.updateItem(bd.getItemName(), -1);
				myP.setHealthPoints(bd.getMyHealth());
				pk.currentBattle.setStatus(bd.getMyPokemon() + " used morphine! Health increased by 200");
				pk.currentBattle.updateBattleUI();
			} else if(bd.getItemName().equals("steroids")) {
				myP.setStrength(bd.getMyStrength());
				System.out.println("my new strength: " + bd.getMyStrength());
				pk.currentBattle.setStatus(bd.getMyPokemon() + " used steroids! Attack increased by 50");
			}
			else if(bd.getItemName().equals("epinephrine")) {
				Pokemon targetP = this.getPokemon(bd.getMyPokemon());
				targetP.setHealthPoints(bd.getMyHealth());
				targetP.setDead(false);
				pk.currentBattle.setStatus(bd.getMyPokemon() + " used epinephrine! It was revived!");
			}
		} else if(bd.getType().equals("switch")) {
			this.setCurrentPokemon(this.getPokemon(bd.getMyPokemon()));
		}
		this.pk.currentBattle.toggle();
	}

	public void sendMessageToServer(Object obj) {
		try {
			this.out.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		
		System.out.println("Client started listening for messages from server");
		
		while(true){
			try{
				// send a message every second
				while(true){
					
					Object objectReceived = in.readObject();
					if(objectReceived instanceof LoginAuthenticated){
						System.out.println("Verifying...");
						LoginAuthenticated messageReceived = (LoginAuthenticated)objectReceived;
						if(messageReceived.getAuthenticated()){
							System.out.println("Login verified");	
							this.pk.userHasLoggedIn();
						}
						else{
							
							if(messageReceived.triedToAuthenticateFromSignUp()){
								JOptionPane.showMessageDialog(pk,
									    "That username is already in use.",
									    "Error",
									    JOptionPane.ERROR_MESSAGE);
							}
							else{
								System.out.println("Login failed");
								JOptionPane.showMessageDialog(pk,
									    "Invalid username or password",
									    "Error",
									    JOptionPane.ERROR_MESSAGE);
							}
						}
						
					} else if(objectReceived instanceof UserUpdate) {
						this.update((UserUpdate)objectReceived);
					} else if(objectReceived instanceof PurchaseUpdate) {
						if(((PurchaseUpdate) objectReceived).isSuccessful()) {
							this.setMoney(((PurchaseUpdate) objectReceived).getMoney());
							this.setItemQuantity("steroids", ((PurchaseUpdate) objectReceived).getSteroids());
							this.setItemQuantity("morphine", ((PurchaseUpdate) objectReceived).getMorphine());
							this.setItemQuantity("epinephrine", ((PurchaseUpdate) objectReceived).getEpinephrine());
							this.pk.storePanel.update();
								JOptionPane.showMessageDialog(pk,
									    "Successfully purchased",
									    "",
									    JOptionPane.INFORMATION_MESSAGE);
							
						} else {
								JOptionPane.showMessageDialog(pk,
									    "You do not have enough gold",
									    "Error",
									    JOptionPane.ERROR_MESSAGE);
				
						}
					} else if(objectReceived instanceof ChatMessage){
						pk.addTextToChat((ChatMessage)objectReceived);
					} else if(objectReceived instanceof BattleData) {
						processBattleData((BattleData)objectReceived);
					}else if(objectReceived instanceof SendOpponentId) {
						SendOpponentId msg = (SendOpponentId)objectReceived;
						this.setOpponentID(msg.getIDOfOpponent());
						System.out.println("Opponent id:" + this.getOpponentID());
					} else if(objectReceived instanceof GameOver) {
						if( ((GameOver)objectReceived).getWinnerID() == this.getID()) {
							//I LOST!
							pk.showWinLossScreen(false);
						} else {
							//I WON!
							pk.showWinLossScreen(true);
						}
					}
					
						
				}
			}
			
			catch(IOException | ClassNotFoundException e){
			
			}		
		}
		
	}
	
	

}
