
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;
import java.io.*;

public class Server {
	
	private Vector<ServerUser> allUsers = new Vector<ServerUser>();
	private Vector<ServerUser> queue = new Vector<ServerUser>();
	
	private ReentrantLock lock = new ReentrantLock();
	
	
	public Server() {
		try {
			ServerSocket ss= new ServerSocket(1234);
			while(true){
				Socket userSocket = ss.accept();
				ServerUser ut = new ServerUser(userSocket, allUsers, this);
				allUsers.add(ut);
				new Thread(ut).start();
			}
		} catch (IOException ioe) {
			System.out.println("IOException in Server constructor: " + ioe.getMessage());
		}
	}
	
	public ServerUser getUserByID(int id) {
		for(int i = 0; i < allUsers.size(); i++) {
			if(allUsers.get(i).getID() == id) {
				return allUsers.get(i);
			}
		}
		return null;
	}
	
	public ServerUser getUserByUsername(String username) {
		for(int i = 0; i < allUsers.size(); i++) {
			if(allUsers.get(i).getUsername().equals(username)) {
				return allUsers.get(i);
			}
		}
		return null;
	}
	
	public void addToQueue(ServerUser su) {
		lock.lock();
		try {
			queue.add(su);
			if(queue.size() >= 2) {
				ServerUser user1 = queue.get(0);
				ServerUser user2 = queue.get(1);
				user1.setOpponentID(user2.getID());
				user2.setOpponentID(user1.getID());
				user1.startBattle(user2, true);
				user2.startBattle(user1, false);
				user1.sendMessageToClient(new SendOpponentId(user2.getID()));
				user2.sendMessageToClient(new SendOpponentId(user1.getID()));
				queue.remove(1);
				queue.remove(0);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String [] args) {
		Server server = new Server();
	}
}