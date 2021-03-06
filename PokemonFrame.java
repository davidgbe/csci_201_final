//Group Members:
//Brent Johnson - Crowley
//Isaac Chien - Miller
//Aneesha Gupta - Miller
//Lauren Howe - Miller
//David Bell - Miller
//James Perng - Miller


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.TitledBorder;



public class PokemonFrame extends JFrame {
	
	ClientUser myClientUser;
	PokemonFrame pk;
	Battle currentBattle;
	
	public static JPanel outerPanel = new JPanel(new CardLayout());

	// INSTANTIATE CARDS
	JPanel signUpPanel = new JPanel();
	JPanel loginPanel = new JPanel();
	JPanel startPanel = new JPanel();
	JPanel menuPanel = new JPanel();
	JPanel chatPanel = new JPanel();
	JPanel battlePanel = new JPanel();
	static JPanel myBagPanel = new JPanel();
	
	JTextArea txt;
	JTextField write;
	
	public static CardLayout cl = (CardLayout)(outerPanel.getLayout());
	
    ImageIcon logo = new ImageIcon("./images/logo.jpg");
    
	//LAUREN ADDED NEW CARDS 
	BagPanel bagPanel;
	StorePanel storePanel;
	JPanel waitingPanel = new JPanel();
	JPanel choosePokemonPanel = new JPanel();
	
	JFrame chatFrame;
	
	
	public static String [] pokemonNames = {"venusaur", "blastoise", "charizard", "dragonite", "gyarados", "hitmonchan",
		"lapras", "mewtwo", "onix", "pidgeot", "pikachu", "rapidash", "rhydon", "scyther", "snorlax"};
	
	JButton [] arrButtons;
    
	Map<String, ImageIcon> pokemonImages = new HashMap<String, ImageIcon>();

	static int chosenPokemonCounter = 0;

	public PokemonFrame(String ipOfServer) {
		super("Pokemon");
		setSize(800,740);
		setLocation(300, 100);
		setMinimumSize(new Dimension(800,720));
		setMaximumSize(new Dimension(800, 740));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pk = this;
		
		// CODE
		myClientUser = new ClientUser(ipOfServer);
		Thread clientThread = new Thread(myClientUser);
		myClientUser.setPokemonFrame(this);
		clientThread.start();
		
		
		//populating pokemon image library
		for (int i = 0; i < 15; i++){
			String filename = "./images/" + pokemonNames[i] + ".png";
			ImageIcon img = new ImageIcon(filename);
			pokemonImages.put(pokemonNames[i], img);
		}
	
		
		chatFrame = new JFrame();
		chatFrame.add(chatPanel);
		chatFrame.setSize(800, 740);
		
		// CREATE CARD FUNCTIONS
		createChatPanel();
		createSignUpPanel();
		createStartPanel();
		createLoginPanel();
		createMenuPanel();
		
		
		// ADD CARDS TO OUTERPANEL
		outerPanel.add(battlePanel, "Battle");
		//outerPanel.add(chatPanel, "Chat");
		outerPanel.add(signUpPanel, "Sign Up");
		outerPanel.add(menuPanel, "Main Menu");
		outerPanel.add(startPanel, "Start");
		outerPanel.add(loginPanel, "Login");
		
		
		//lauren adding cards
		createBagPanel();
		createStorePanel();
		outerPanel.add(storePanel, "Store");
		outerPanel.add(myBagPanel, "Bag");
		noOpponentPanel();
		outerPanel.add(waitingPanel, "Waiting");
		choosePokemonPanel();
		outerPanel.add(choosePokemonPanel, "ChooseFromMain");
		

		
		
		add(outerPanel);
		cl.show(outerPanel, "Start");

		
		setVisible(true);
		
	}
	
	public void userHasLoggedIn() {
		cl.show(outerPanel, "Main Menu");
	}
	
	private void createBagPanel(){
		this.bagPanel = new BagPanel(myClientUser);
	}
	private void createStorePanel(){  //Very ugly, but can't find out how to make it look better 
		this.storePanel = new StorePanel(myClientUser, this);
	}

	
	private void noOpponentPanel(){
		
		JLabel waiting = new JLabel("Waiting for Opponent...");
		waitingPanel.setLayout(null);
		
		Dimension button1Dimensions = waiting.getPreferredSize();
		waiting.setBounds(this.getWidth()/2-57, this.getHeight()/2, button1Dimensions.width, button1Dimensions.height);
		waitingPanel.add(waiting);
	}
	
	public void showBattle() {
		if(currentBattle != null) {
			battlePanel.remove(currentBattle);
		}
		currentBattle = new Battle(myClientUser, this);
		battlePanel.add(currentBattle);
		battlePanel.revalidate();
		cl.show(outerPanel, "Battle");
	}
	
	private void sendMessageToServer(Object objectToSend){
		try {
			myClientUser.getOutputStream().writeObject(objectToSend);
		} catch (IOException e1) {

			e1.printStackTrace();
		}
	}
	private void choosePokemonPanel(){ //cant get image to show, but rough outline is there. I have thoughts on how to make the selection of only 6 work...see below
		
		JPanel inner = new JPanel();
		JScrollPane scrollPane = new JScrollPane(inner);
		inner.setLayout(new GridLayout(15, 1)); 
		inner.setMinimumSize(new Dimension(780, 650));
		inner.setPreferredSize(new Dimension(780, 650));
		inner.setMaximumSize(new Dimension(780, 650));
		
		arrButtons = new JButton [15];
		
		for (int i=0; i<15; i++) {
			ImageIcon myTest = pokemonImages.get(pokemonNames[i]);
			Image image = myTest.getImage(); // transform it 
			Image newimg = image.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			myTest = new ImageIcon(newimg);  // transform it back

			//For making sure they only select 6, we should just have a counter and the actionlistener 
			//should just reshow the screen minus the selected button if it is less than 6, and then proceed if more than

			final JButton button = new JButton(pokemonNames[i].toUpperCase(), myTest);
			arrButtons[i] = button;
			button.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if (chosenPokemonCounter < 6 && button.getBackground() != Color.BLUE){
						chosenPokemonCounter++;
						button.setBackground(Color.BLUE);
						button.setOpaque(true);
						String tempName = button.getText().toLowerCase();
						myClientUser.addPokemon(tempName);
					} else if (button.getBackground() == Color.BLUE) {
						chosenPokemonCounter--;
						button.setBackground(null);
						button.setOpaque(false);
						String tempName = button.getText().toLowerCase();
						myClientUser.removePokemon(tempName);
					}
				}
			});
			inner.add(button);
		}
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessageToServer(new PokemonUpdate(myClientUser.getPokemonNames()));
				cl.show(outerPanel, "Main Menu");
			}

		});
		choosePokemonPanel.add(submit, BorderLayout.NORTH);
		choosePokemonPanel.add(inner, BorderLayout.CENTER);
		
		add(choosePokemonPanel);
	}
	private void createChatPanel() {
		JButton back = new JButton("Close"); 
		txt = new JTextArea();
		write = new JTextField(1);
		JButton sendMessageButton = new JButton("Send");
        JScrollPane sp = new JScrollPane(txt);
        JScrollPane spwrite = new JScrollPane(write);
        txt.setLineWrap(true);
        txt.setAutoscrolls(true);
        txt.setEditable(false);

        sp.setPreferredSize(new Dimension(780,580));
        spwrite.setPreferredSize(new Dimension(700,28));

		chatPanel.add(sp, BorderLayout.CENTER);
		JPanel jp = new JPanel();
		jp.add(spwrite);
		jp.add(sendMessageButton);
		chatPanel.add(jp, BorderLayout.SOUTH);
		chatPanel.add(back, BorderLayout.SOUTH);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//cl.show(outerPanel, "Main Menu");
				chatFrame.setVisible(false);
			}
		});
		class SendMessage implements ActionListener {
			public SendMessage(){
				
			}
		    public void actionPerformed(ActionEvent e) {
		    	String text = write.getText();
		        ChatMessage messageToSend = new ChatMessage(write.getText(), myClientUser.getUsername());
		        sendMessageToServer(messageToSend);
		        txt.append(myClientUser.getUsername() + ": " + text + "\n");
		        write.setText("");
		    }
		}
		sendMessageButton.addActionListener(new SendMessage());
		SwingUtilities.getRootPane(sendMessageButton).setDefaultButton(sendMessageButton);
	}
	
	public void addTextToChat(ChatMessage chatmsg){
		txt.append(chatmsg.getUsernameMessageIsFrom() + ": " + chatmsg.getMessageContents() + "\n");
		
	}
	private void createSignUpPanel() {
		JPanel jp = new JPanel();
        jp.setLayout(new GridBagLayout());
        jp.setBorder(new TitledBorder("Sign Up"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        jp.add(new JLabel("Username:"), gbc);
        gbc.gridy++;
        jp.add(new JLabel("Password:"), gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        final JTextField loginField = new JTextField(10);
        jp.add(loginField, gbc);
        gbc.gridy++;
        final JTextField passwordField = new JTextField(10);
        jp.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JButton signUpButton = new JButton("Sign Up");
        jp.add(signUpButton, gbc);
        gbc.gridx++;
        JButton cancelButton = new JButton("Cancel");
        jp.add(cancelButton, gbc);
        
        // if Sign Up successful
        signUpButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// SEND LOGIN MESSAGE
				NewUser newLoginMessage = new NewUser(loginField.getText(), passwordField.getText());
				try {
					myClientUser.getOutputStream().writeObject(newLoginMessage);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				System.out.println("Username:" +  newLoginMessage.getUsername());
				System.out.println("Password:" + newLoginMessage.getPassword());

			}
        });
        cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(outerPanel, "Start");
			}
        });
        signUpPanel.add(jp, BorderLayout.CENTER);
	}
	private void createMenuPanel() {
		JButton choose = new JButton("Choose Pokemon"); 
		JButton store = new JButton("Go to item store"); 
		JButton bag = new JButton("View Bag"); 
		JButton join = new JButton("Join Game");
		JButton chat = new JButton("Chat"); 
		
		chat.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {	
				chatFrame.setVisible(true);
			}
		});
		JLabel logoLabel = new JLabel(logo); 
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
		menuPanel.add(logoLabel); 
		
		choose.setSize(800, this.getHeight()/10);
		choose.setMaximumSize(choose.getSize());
		choose.setAlignmentX(Component.CENTER_ALIGNMENT); 
	
		store.setSize(this.getWidth(), this.getHeight()/10);
		store.setMaximumSize(store.getSize());
		store.setAlignmentX(Component.CENTER_ALIGNMENT); 
		bag.setSize(this.getWidth(), this.getHeight()/10);
		bag.setMaximumSize(bag.getSize());
		bag.setAlignmentX(Component.CENTER_ALIGNMENT); 
		join.setSize(this.getWidth(), this.getHeight()/10);
		join.setMaximumSize(join.getSize());
		join.setAlignmentX(Component.CENTER_ALIGNMENT); 
		chat.setSize(this.getWidth(), this.getHeight()/10);
		chat.setMaximumSize(chat.getSize());
		chat.setAlignmentX(Component.CENTER_ALIGNMENT); 
		
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
		menuPanel.add(choose); 
		menuPanel.add(store); 
		menuPanel.add(bag); 
		menuPanel.add(join); 
		menuPanel.add(chat); 
		
		bag.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				bagPanel.update();
				cl.show(outerPanel, "Bag");
			}
		});
        choose.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(outerPanel, "ChooseFromMain");
			}
        });
        store.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(outerPanel, "Store");
				storePanel.update();
			}
        });
        join.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// while another opponent is not available
//				cl.show(outerPanel, "Waiting");
				if(myClientUser.getPokemons().size() <= 0){
					JOptionPane.showMessageDialog(pk,
						    "You must select at least one pokemon before going into battle!",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
				cl.show(outerPanel, "Waiting");
				sendMessageToServer(new QueueMe());
			}
        });
    }
	private void createLoginPanel() {
		JPanel jp = new JPanel();
        jp.setLayout(new GridBagLayout());
        jp.setBorder(new TitledBorder("Login"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        jp.add(new JLabel("Username:"), gbc);
        gbc.gridy++;
        jp.add(new JLabel("Password:"), gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        final JTextField usernameField = new JTextField(10);
        jp.add(usernameField, gbc);
        gbc.gridy++;
        final JTextField passwordField = new JTextField(10);
        jp.add(passwordField, gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JButton loginButton = new JButton("Login");
        jp.add(loginButton, gbc);
        gbc.gridx++;
        JButton cancelButton = new JButton("Cancel");
        jp.add(cancelButton, gbc);
        
        // if login successful
        loginButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Login loginMessage = new Login();
				loginMessage.setUsername(usernameField.getText());
				loginMessage.setPassword(passwordField.getText());
				try {
					myClientUser.getOutputStream().writeObject(loginMessage);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				System.out.println("Username:" +  loginMessage.getUsername());
				System.out.println("Password:" + loginMessage.getPassword());
			}
        });
        cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(outerPanel, "Start");
			}
        });
        loginPanel.add(jp, BorderLayout.CENTER);
	}
	private void createStartPanel() {
		startPanel.setLayout(new BorderLayout());
		JButton loginButton = new JButton("Login");
		// if login successful
		loginButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(outerPanel, "Login");
			}
		});

		JButton registerButton = new JButton("Sign Up");
		registerButton.setLocation(400, 500);
		registerButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(outerPanel, "Sign Up");
			}
		});
		
        JLabel imageLabel = new JLabel( logo );

        JPanel buttons = new JPanel();
        buttons.add(loginButton);
        buttons.add(registerButton);
        startPanel.add(imageLabel, BorderLayout.NORTH);
        startPanel.add(buttons, BorderLayout.CENTER);
	}
	
	public void showWinLossScreen(boolean iWon){
		if(iWon){
			JOptionPane.showMessageDialog(pk,
				    "You won!",
				    "Congratulations",
				    JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(pk,
				    "You lost!",
				    "",
				    JOptionPane.INFORMATION_MESSAGE);
		}
		
		// empty pokemon/current pokemon
		// reset pokemon select screen (reset background color)
		cl.show(outerPanel, "Main Menu");
		myClientUser.getPokemons().clear();
		myClientUser.setCurrentPokemon(null);
		myClientUser.setInBattle(false);
		myClientUser.myTurn = false;
		chosenPokemonCounter = 0;
		for(int i = 0; i < 15; ++i){
			arrButtons[i].setBackground(null);
			arrButtons[i].setOpaque(false);
		}
		
	}
	
	public void showCouldNotConnect(){
		JOptionPane.showMessageDialog(pk,
			    "Could not connect to the server. Try again later",
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}
        
        
     
	public static void main (String args []){
		if (args.length!= 1) {
			System.out.println("USAGE: java PokemonFrame [ip of server]");
			return;
		}
		new PokemonFrame(args[0]);
	}
}
