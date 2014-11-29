import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

	
	JPanel rightPanel = new JPanel(); 
	
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
	
	ClientUser user;
	
	Battle(ClientUser user) { 
		setLayout(new BorderLayout());
		setSize(800, 720); 
		this.user = user;

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

		battleStatus.setText("What will " + user.opponentPokemon + " do?"); 
		//THIS IS WHERE THE OPPONENT'S ATTACK WILL DISPLAY
		
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
		

		
		this.choosePokemon = new JButton("Choose Pokemon"); 
		rightPanel.add(choosePokemon); 
		this.viewBag = new JButton("View Bag"); 
		rightPanel.add(viewBag); 
		this.fightButton = new JButton("FIGHT"); 
		fightButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(leftPanel, "Attacks");
			}
		});
		rightPanel.add(fightButton); 
	
		InfoPanel1.add(health1, BorderLayout.EAST); 
		InfoPanel2.add(health2);
		PokemonPanel1.add(imageLabel1); 
		PokemonPanel2.add(imageLabel2); 
		statusPanel.add(battleStatus); 
		
		
		// flow of leftPanel
		
		leftPanel.add(statusPanel, "Status");
		leftPanel.add(attacksPanel, "Attacks");
		
		cl.show(leftPanel, "Status");
		this.toggle();
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
			battleStatus.setText(user.getCurrentPokemon().getName() + " used " + button.getText());
			cl.show(leftPanel, "Status");
			//this.user.sendMessageToServer(new Attack(button.getText(), 10));
			// opponent needs to receive user's attack
			// user needs to wait for opponent to send an attack.
			// user needs to receive attack
		}
	}
	
	public void updateBattleUI(){
		// IS NEVER CALLED YET, call after each battle message from server is parsed
		
		myPokemonImage = user.getCurrentPokemon().getPokemonImage(); 
		imageLabel1.setIcon(myPokemonImage); 
		
		opponentImage = new ImageIcon("images/" + user.opponentPokemon + ".png");  
		imageLabel2.setIcon(opponentImage);
		 
		percentage = (user.getCurrentPokemon().getHealthPoints()/user.getCurrentPokemon().getTotalHealthPoints());
		healthpoints1 = Double.toString(percentage*100.0); 
		health1.setText(user.getCurrentPokemon().getName() + " "+  healthpoints1 + "%");

		double totalHealthOfOpponentsPokemon = Pokemon.getPokemonObjectFromName(user.opponentPokemon).getTotalHealthPoints();
		percentage2 = (user.opponentHealth/totalHealthOfOpponentsPokemon);
		healthpoints2 = Double.toString(percentage2*100.0);
		health2.setText(user.opponentPokemon + " " + healthpoints2 + "%");  

		battleStatus.setText("What will " + user.opponentPokemon + " do?"); 

		attack1.setText(user.getCurrentPokemon().allAttacks[0]);
		attack2.setText(user.getCurrentPokemon().allAttacks[1]);
		attack3.setText(user.getCurrentPokemon().allAttacks[2]);
		attack4.setText(user.getCurrentPokemon().allAttacks[3]);
	}

}