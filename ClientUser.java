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
	
	public ClientUser(String ipOfServer){
		super();
		
		try {
			//TODO: DO NOT HARDCODE LOCALHOST
			mySocket = new Socket(ipOfServer, 1234);
			in = new ObjectInputStream(mySocket.getInputStream());
			out = new ObjectOutputStream(mySocket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Could not connect to server");
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
		System.out.println("money now:" + user.getMoney());
		this.setWins(user.getWins());
		this.setLosses(user.getLosses());
		this.setOpponentID(user.getOpponentID());
		if(this.getItems() == null) {
			this.setItems(new HashMap<String, Integer>());
		}
		this.setItemQuantity("steroids", user.getSteroids());
		this.setItemQuantity("morphine", user.getMorphine());
		this.setItemQuantity("epinephrine", user.getEpinephrine());
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
				pk.currentBattle.setStatus("What will you do?");
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
			
			boolean attackMissed = false;
			if(myP.getHealthPoints() == bd.getOpponentHealth()){
				attackMissed = true;
			}
			myP.setHealthPoints(bd.getOpponentHealth());
			myP.setStrength(bd.getOpponentStrength());
			pk.currentBattle.updateBattleUI();
			if(attackMissed){
				pk.currentBattle.setStatus(bd.getOpponentPokemon() + " used " + bd.getAttackName() + " but it missed!");
			}
			else{
				pk.currentBattle.setStatus(bd.getOpponentPokemon() + " used " + bd.getAttackName());
			}
			if(myP.getHealthPoints() == 0) {
				this.getCurrentPokemon().setDead(true);
				pk.currentBattle.setStatus(this.getCurrentPokemon().getName() + " fainted! Choose a pokemon");
				pk.currentBattle.forceSwitchPokemon();
			}
		} else if(bd.getType().equals("item")) {
			if(bd.getItemName().equals("morphine")){
				this.opponentHealth = bd.getMyHealth();
				pk.currentBattle.setStatus(bd.getMyPokemon() + " used morphine! Health increased by 200");
				pk.currentBattle.updateBattleUI();
			} else if(bd.getItemName().equals("steroids")) {
				this.opponentStrength = bd.getMyStrength();
				System.out.println("Opponent's new strength: " + bd.getMyStrength());
				pk.currentBattle.setStatus(bd.getMyPokemon() + " used steroids! Attack increased");
			}
			else if(bd.getItemName().equals("epinephrine")) {
				if(bd.getMyPokemon().equals(this.opponentPokemon)) {
					this.opponentHealth = bd.getMyHealth();
					pk.currentBattle.setStatus("Opponent fully restored " + bd.getMyPokemon());
				}
			}
		} else if(bd.getType().equals("switch")) {
			this.opponentPokemon = bd.getMyPokemon();
			this.opponentHealth = bd.getMyHealth();
			this.opponentStrength = bd.getMyStrength();
			pk.currentBattle.setStatus("Opponent sent out " + bd.getMyPokemon() + "!");
		}
		this.pk.currentBattle.toggle();
	}
	
	private void processOwnMove(BattleData bd) {
		Pokemon myP = this.getCurrentPokemon();
		if(bd.getType().equals("attack")) {
			boolean attackMissed = false;
			boolean killedOpponent = false;
			if(this.opponentHealth == bd.getOpponentHealth()){
				attackMissed = true;
			}
			if(bd.getOpponentHealth() <= 0){
				killedOpponent = true;
			}
			this.opponentHealth = bd.getOpponentHealth();
			this.opponentStrength = bd.getOpponentStrength();
			pk.currentBattle.updateBattleUI();
			if(attackMissed){
				pk.currentBattle.setStatus(bd.getOpponentPokemon() + " used " + bd.getAttackName() + " but it missed!");
			}
			else if(killedOpponent){
				pk.currentBattle.setStatus(bd.getOpponentPokemon() + " used " + bd.getAttackName() + ". " + this.opponentPokemon + " fainted!");
			}
			else{
				pk.currentBattle.setStatus(bd.getOpponentPokemon() + " used " + bd.getAttackName());
			}
			
		} else if(bd.getType().equals("item")) {
			if(bd.getItemName().equals("morphine")){
				this.updateItem(bd.getItemName(), -1);
				myP.setHealthPoints(bd.getMyHealth());
				pk.currentBattle.setStatus(bd.getMyPokemon() + " used morphine! Health increased by 200");
				pk.currentBattle.updateBattleUI();
			} else if(bd.getItemName().equals("steroids")) {
				myP.setStrength(bd.getMyStrength());
				System.out.println("My new strength: " + bd.getMyStrength());
				pk.currentBattle.setStatus(bd.getMyPokemon() + " used steroids! Attack increased");
			}
			else if(bd.getItemName().equals("epinephrine")) {
				Pokemon targetP = this.getPokemon(bd.getMyPokemon());
				targetP.setHealthPoints(bd.getMyHealth());
				targetP.setDead(false);
				pk.currentBattle.setStatus(bd.getMyPokemon() + " used epinephrine! It was fully restored!");
			}
		} else if(bd.getType().equals("switch")) {
			this.setCurrentPokemon(this.getPokemon(bd.getMyPokemon()));
			pk.currentBattle.setStatus("I choose you " + bd.getMyPokemon() + "!");
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
		
		while(true){
			try{
				// send a message every second
				while(true){
					if(mySocket == null){
						pk.showCouldNotConnect();
						return;
					}
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
						System.out.println("Opponent's ID:" + this.getOpponentID());
					} else if(objectReceived instanceof GameOver) {
						if( ((GameOver)objectReceived).getWinnerID() == this.getID()) {
							//I LOST!
							pk.showWinLossScreen(true);
						} else {
							//I WON!
							pk.showWinLossScreen(false);
						}
					}
					
						
				}
			}
			
			catch(IOException | ClassNotFoundException e){
			
			}		
		}
		
	}
	
	

}
