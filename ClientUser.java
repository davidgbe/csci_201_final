import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;


public class ClientUser extends User implements Runnable{

	Socket mySocket;
	ObjectInputStream in;
	ObjectOutputStream out;
	PokemonFrame pk; 
	
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
		
						
					}
						
				}
			}
			
			catch(IOException | ClassNotFoundException e){
			
			}		
		}
		
	}
	
	

}
