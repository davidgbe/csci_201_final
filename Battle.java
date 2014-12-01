import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Battle extends JPanel {
	
	private boolean disabled = false;

	private JPanel battlePanel = new JPanel(); 
	JPanel opponentPanel = new JPanel(); 
	JPanel bottomPanel = new JPanel();
	JPanel PokemonPanel1 = new JPanel(); 
	JPanel InfoPanel1 = new JPanel(); 
	JPanel PokemonPanel2 = new JPanel(); 
	JPanel InfoPanel2 = new JPanel(); 
	
	
	JPanel attacksPanel = new JPanel();
	JLabel battleStatus = new JLabel(); 
	JPanel statusPanel = new JPanel();
	JPanel leftPanel = new JPanel(new CardLayout()); 
	CardLayout cl = (CardLayout)(leftPanel.getLayout());
	
	private JButton choosePokemon;
	private JButton viewBag;
	private JButton fightButton;

	
	JPanel rightPanel = new JPanel(new CardLayout());
	CardLayout cl2 = (CardLayout)(rightPanel.getLayout());
	
	private static final long serialVersionUID = 1L;
	
	// data for updating UI
	JLabel imageLabel1; 
	ImageIcon myPokemonImage;
	JLabel imageLabel2;
	ImageIcon opponentImage;
	JLabel health1;
	double percentage;
	String healthpoints1; 
	JLabel health2;
	double percentage2;
	String healthpoints2; 
	JButton attack1;
	JButton attack2;
	JButton attack3;
	JButton attack4;
	
	JButton useSteroidsButton;
	JButton useMorphineButton;
	JButton useEpinephrineButton;
	
	JButton backToSelectionButton3;
	
	JPanel bagPanel = new JPanel();
	JPanel selectionPanel = new JPanel();
	JPanel changePokemonPanel = new JPanel();
	JPanel epinephrineUsePanel = new JPanel();
	
	ClientUser user;
	PokemonFrame pk;
	
	Battle(ClientUser user, PokemonFrame pk) { 
		setLayout(new BorderLayout());
		setSize(800, 720); 
		this.user = user;
		this.pk = pk;

		battlePanel.setBackground(Color.RED); 
		opponentPanel.setBackground(Color.BLUE);
		bottomPanel.setBackground(Color.GRAY);
	
		battlePanel.setPreferredSize(new Dimension(490, 500)); 
		InfoPanel1.setPreferredSize(new Dimension(490, 300)); 
		InfoPanel2.setPreferredSize(new Dimension(490, 300));
		PokemonPanel1.setPreferredSize(new Dimension(490, 200)); 
		PokemonPanel2.setPreferredSize(new Dimension(800, 300));
		opponentPanel.setPreferredSize(new Dimension(300, 500)); 
		bottomPanel.setPreferredSize(new Dimension(800, 200));
		leftPanel.setPreferredSize(new Dimension(390, 200)); 
		rightPanel.setPreferredSize(new Dimension(390, 200));
		bagPanel.setPreferredSize(new Dimension(390, 200));
		changePokemonPanel.setPreferredSize(new Dimension(390, 200));
		epinephrineUsePanel.setPreferredSize(new Dimension(390, 200));
		
		add(battlePanel, BorderLayout.WEST);
		add(opponentPanel, BorderLayout.EAST);
		add(bottomPanel, BorderLayout.SOUTH); 
		battlePanel.add(InfoPanel1, BorderLayout.NORTH); 
		battlePanel.add(PokemonPanel1, BorderLayout.SOUTH);
		opponentPanel.add(PokemonPanel2, BorderLayout.NORTH);
		opponentPanel.add(InfoPanel2, BorderLayout.SOUTH); 
		bottomPanel.add(leftPanel, BorderLayout.EAST); 
		bottomPanel.add(rightPanel, BorderLayout.WEST); 
		
		//display user image                                 
		imageLabel1 = new JLabel(); 
		myPokemonImage = new ImageIcon(); 
		myPokemonImage = user.getCurrentPokemon().getPokemonImage(); 
		imageLabel1.setIcon(myPokemonImage); 
		
		//display opponent image 
		imageLabel2 = new JLabel(); 
		opponentImage = new ImageIcon("images/" + user.opponentPokemon + ".png"); 
		//opponentImage = new ImageIcon//opponent.getCurrentPokemon().getPokemonImage(); 
		imageLabel2.setIcon(opponentImage);
		
		//display user health
		health1 = new JLabel(); 
		percentage = (user.getCurrentPokemon().getHealthPoints()/user.getCurrentPokemon().getTotalHealthPoints());
		healthpoints1 = Double.toString(percentage*100.0); 
		health1.setText(user.getCurrentPokemon().getName() + " "+  healthpoints1 + "%");
		health1.setFont(new Font("Serif", Font.BOLD, 25));
		
		//display opponent health
		health2 = new JLabel(); 
		double totalHealthOfOpponentsPokemon = Pokemon.getPokemonObjectFromName(user.opponentPokemon).getTotalHealthPoints();
		percentage2 = (user.opponentHealth/totalHealthOfOpponentsPokemon);
		healthpoints2 = Double.toString(percentage2*100.0); 
		health2.setText(user.opponentPokemon + " " + healthpoints2 + "%"); 
		health2.setFont(new Font("Serif", Font.BOLD,25)); 

		battleStatus.setText("Waiting on your opponents move..."); 
		
		//Attacks Panel just displaying for now.
		// get pokemon's attacks
		attacksPanel.setLayout(new GridLayout(2, 2));
		attack1 = new JButton(user.getCurrentPokemon().allAttacks[0]);
		attack2 = new JButton(user.getCurrentPokemon().allAttacks[1]);
		attack3 = new JButton(user.getCurrentPokemon().allAttacks[2]);
		attack4 = new JButton(user.getCurrentPokemon().allAttacks[3]);
		attacksPanel.add(attack1);
		attacksPanel.add(attack2);
		attacksPanel.add(attack3);
		attacksPanel.add(attack4);
		// all the same actionlistener and write message?
		AttackListener al = new AttackListener(user);
		attack1.addActionListener(al);
		attack2.addActionListener(al);
		attack3.addActionListener(al);
		attack4.addActionListener(al);
		
		bagPanel.setLayout(new BoxLayout(bagPanel, BoxLayout.Y_AXIS));
		changePokemonPanel.setLayout(new BoxLayout(changePokemonPanel, BoxLayout.Y_AXIS));
		epinephrineUsePanel.setLayout(new BoxLayout(epinephrineUsePanel, BoxLayout.Y_AXIS));
		
		useSteroidsButton = new JButton("Steroids x" + user.getItems().get("steroids"));
		useMorphineButton = new JButton("Morphine x" + user.getItems().get("morphine"));
		useEpinephrineButton = new JButton("Epinephrine x" + user.getItems().get("epinephrine"));
		JButton backToSelectionButton = new JButton("<- Back");
		JButton backToSelectionButton2 = new JButton("<- Back");
		backToSelectionButton3 = new JButton("<- Back");
		bagPanel.add(useSteroidsButton);
		bagPanel.add(useMorphineButton);
		bagPanel.add(useEpinephrineButton);
		bagPanel.add(backToSelectionButton);
		
		// populate epinephrine use panel
		for(int i = 0; i < user.getPokemons().size(); ++i){
			ImageIcon imageForButton = new ImageIcon("images/" + user.getPokemons().get(i).getName() + ".png");
			Image image = imageForButton.getImage(); 
			Image newimg = image.getScaledInstance(15, 15,  java.awt.Image.SCALE_SMOOTH); 
			ImageIcon actualImageForButton = new ImageIcon(newimg);
			JButton tempButton = new JButton(user.getPokemons().get(i).getName(), actualImageForButton);
			// add action listener here
			tempButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					user.getItems().put("epinephrine", user.getItemQuantity("epinephrine") - 1);
					System.out.println("Used 1 epinephrine. " + user.getItemQuantity("epinephrine") + " left.");
					String pokemonToRevive = tempButton.getText();
					user.sendMessageToServer(new Item("epinephrine", pokemonToRevive));
					cl2.show(rightPanel, "Selection");
					cl.show(leftPanel, "Status");
				}
			});
			
			epinephrineUsePanel.add(tempButton);
		}
		
		epinephrineUsePanel.add(backToSelectionButton2);
		
		updateChoosePokemonPanel(false);
		
		this.choosePokemon = new JButton("Choose Pokemon"); 
		selectionPanel.add(choosePokemon); 
		this.viewBag = new JButton("View Bag"); 
		selectionPanel.add(viewBag); 
		this.fightButton = new JButton("FIGHT"); 
		selectionPanel.add(fightButton);
		
		// ACTION LISTENERS
		fightButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(leftPanel, "Attacks");
			}
		});
		choosePokemon.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				updateChoosePokemonPanel(false);
				cl2.show(rightPanel, "ChangePokemon");
				
	
			}
		});
		viewBag.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cl2.show(rightPanel, "Bag");
			}
		});
		
		backToSelectionButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cl2.show(rightPanel, "Selection");
			}
		});
		backToSelectionButton2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cl2.show(rightPanel, "Selection");
			}
		});
		backToSelectionButton3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cl2.show(rightPanel, "Selection");
			}
		});
		
		useMorphineButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(user.getItems().get("morphine") > 0){
					// SEND MESSAGE TO SERVER
					user.sendMessageToServer(new Item("morphine"));
//					user.getCurrentPokemon().setHealthPoints(user.getCurrentPokemon().getHealthPoints() + 200);
//					setStatus("You used morphine. Health increased by 200 points.");
////					BattleData bdToSend = new BattleData(user.getOpponentID(), "morphine", user.getCurrentPokemon().getName(), user.getCurrentPokemon().getHealthPoints());
////					user.sendMessageToServer(bdToSend);
//					System.out.println("Used 1 morphine. " + user.getItemQuantity("morphine") + " left.");
//					updateBattleUI();
					cl2.show(rightPanel, "Selection");
					cl.show(leftPanel, "Status");

				}
				else{
					JOptionPane.showMessageDialog(pk,
						    "You don't have any morphine to use!",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
				}
				
				
				
			}
		});
		useSteroidsButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(user.getItems().get("steroids") > 0){
					// SEND MESSAGE TO SERVER
					System.out.println("CALLED!");
					user.sendMessageToServer(new Item("steroids"));
					user.getItems().put("steroids", user.getItemQuantity("steroids") - 1);
//					System.out.println("Used 1 steroid. " + user.getItemQuantity("steroids") + " left.");
					cl2.show(rightPanel, "Selection");
					cl.show(leftPanel, "Status");
				}
				else{
					JOptionPane.showMessageDialog(pk,
						    "You don't have any steroids to use!",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		useEpinephrineButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(user.getItems().get("epinephrine") > 0){
					// SEND MESSAGE TO SERVER
					cl2.show(rightPanel, "Epinephrine");
					cl.show(leftPanel, "Status");
				}
				else{
					JOptionPane.showMessageDialog(pk,
						    "You don't have any epinephrine to use!",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		 
	
		InfoPanel1.add(health1, BorderLayout.EAST); 
		InfoPanel2.add(health2);
		PokemonPanel1.add(imageLabel1); 
		PokemonPanel2.add(imageLabel2); 
		statusPanel.add(battleStatus); 
		
		
		// flow of leftPanel
		
		leftPanel.add(statusPanel, "Status");
		leftPanel.add(attacksPanel, "Attacks");
		
		rightPanel.add(bagPanel, "Bag");
		rightPanel.add(selectionPanel, "Selection");
		rightPanel.add(changePokemonPanel, "ChangePokemon");
		rightPanel.add(epinephrineUsePanel, "Epinephrine");
		
		cl.show(leftPanel, "Status");
		cl2.show(rightPanel, "Selection");
		this.toggle();
		
		System.out.println("my health from bottom constr:" + user.getCurrentPokemon().getHealthPoints());
	}
	
	public void toggle() {
		if(disabled) {
			this.fightButton.setEnabled(true);
			this.viewBag.setEnabled(true);
			this.choosePokemon.setEnabled(true);
		} else {
			this.fightButton.setEnabled(false);
			this.viewBag.setEnabled(false);
			this.choosePokemon.setEnabled(false);
		}
		this.disabled = !this.disabled;
		this.repaint();
	}
	
	class AttackListener implements ActionListener{
		ClientUser user;
		public AttackListener(ClientUser user) {
			this.user = user;
		}
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			this.user.sendMessageToServer(new Attack(button.getText()));
			cl.show(leftPanel, "Status");
			cl2.show(rightPanel, "Selection");

			// opponent needs to receive user's attack
			// user needs to wait for opponent to send an attack.
			// user needs to receive attack
		}
	}
	
	public void updateBattleUI(){
		myPokemonImage = user.getCurrentPokemon().getPokemonImage(); 
		imageLabel1.setIcon(myPokemonImage); 
		
		opponentImage = new ImageIcon("images/" + user.opponentPokemon + ".png");  
		imageLabel2.setIcon(opponentImage);
		double mHealth = user.getCurrentPokemon().getHealthPoints();
		double mTotalHealth = user.getCurrentPokemon().getTotalHealthPoints();
		percentage = mHealth/mTotalHealth;
		DecimalFormat df2 = new DecimalFormat("#.00");
        percentage = Double.valueOf(df2.format(percentage));
        System.out.println("My health percentage: " + percentage);
		healthpoints1 = Double.toString(percentage*100.0); 
	
		health1.setText(user.getCurrentPokemon().getName() + " "+  healthpoints1 + "%");

		double totalHealthOfOpponentsPokemon = Pokemon.getPokemonObjectFromName(user.opponentPokemon).getTotalHealthPoints();
		percentage2 = (user.opponentHealth/totalHealthOfOpponentsPokemon);
		percentage2 = Double.valueOf(df2.format(percentage2));
		healthpoints2 = Double.toString(percentage2*100.0);
		health2.setText(user.opponentPokemon + " " + healthpoints2 + "%");   

		attack1.setText(user.getCurrentPokemon().allAttacks[0]);
		attack2.setText(user.getCurrentPokemon().allAttacks[1]);
		attack3.setText(user.getCurrentPokemon().allAttacks[2]);
		attack4.setText(user.getCurrentPokemon().allAttacks[3]);
		
		updateBagView();
		
		this.repaint();
	}
	
	public void updateBagView(){
		useSteroidsButton.setText("Steroids x" + user.getItemQuantity("steroids"));
		useMorphineButton.setText("Morphine x" + user.getItemQuantity("morphine"));
		useEpinephrineButton.setText("Epinephrine x" + user.getItemQuantity("epinephrine"));
	}
	
	public void updateChoosePokemonPanel(boolean backDisabled){
		
		changePokemonPanel.removeAll();
		changePokemonPanel.repaint();
		changePokemonPanel.revalidate();
		// populate choose pokemon panel
		for(int i = 0; i < user.getPokemons().size(); ++i){
			if(user.getPokemons().get(i).isDead() || user.getPokemons().get(i).getName().equals(user.getCurrentPokemon().getName())){
				continue;
			}
			ImageIcon imageForButton = new ImageIcon("images/" + user.getPokemons().get(i).getName() + ".png");
			Image image = imageForButton.getImage(); 
			Image newimg = image.getScaledInstance(15, 15,  java.awt.Image.SCALE_SMOOTH); 
			ImageIcon actualImageForButton = new ImageIcon(newimg);
			JButton tempButton = new JButton(user.getPokemons().get(i).getName(), actualImageForButton);
			// add action listener here
			tempButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					String pokemonToSwitchTo = tempButton.getText();
					user.sendMessageToServer(new Switch(pokemonToSwitchTo));
					cl2.show(rightPanel, "Selection");
					cl.show(leftPanel, "Status");
					backToSelectionButton3.setEnabled(true);
				}
			});
			
			changePokemonPanel.add(tempButton);
		}
		if(backDisabled) {
			backToSelectionButton3.setEnabled(false);
		}
		changePokemonPanel.add(backToSelectionButton3);
		changePokemonPanel.repaint();
		changePokemonPanel.revalidate();
		
	}
	
	public void setStatus(String text){
		battleStatus.setText(text);
	}
	
	public void forceSwitchPokemon() {
		updateChoosePokemonPanel(true);
		cl2.show(rightPanel, "ChangePokemon");
	}

}