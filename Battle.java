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
	
	Battle(ClientUser user) { 
		setLayout(new BorderLayout());
		setSize(800, 720); 

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
		JLabel imageLabel1 = new JLabel(); 
		ImageIcon myPokemonImage = new ImageIcon(); 
		myPokemonImage = user.getCurrentPokemon().getPokemonImage(); 
		imageLabel1.setIcon(myPokemonImage); 
		
		//display opponent image 
		JLabel imageLabel2 = new JLabel(); 
		ImageIcon opponentImage = new ImageIcon("images/" + user.opponentPokemon + ".png"); 
		//opponentImage = new ImageIcon//opponent.getCurrentPokemon().getPokemonImage(); 
		imageLabel2.setIcon(opponentImage);
		
		//display user health
		JLabel health1 = new JLabel(); 
		double percentage = (user.getCurrentPokemon().getHealthPoints())/(user.getCurrentPokemon().getStrength());
		String healthpoints1 = Double.toString(percentage); 
		health1.setText(user.getCurrentPokemon().getName() + " "+  healthpoints1+"%");
		health1.setFont(new Font("Serif", Font.BOLD, 25));
		
		//display opponent health
		JLabel health2 = new JLabel(); 
		double percentage2 = (user.opponentHealth/user.opponentStrength);
		String healthpoints2 = Double.toString(percentage2); 
		health2.setText(user.opponentPokemon + " " + healthpoints2+"%"); 
		health2.setFont(new Font("Serif", Font.BOLD,25)); 

		battleStatus.setText("What will " + user.opponentPokemon + " do?"); 
		//THIS IS WHERE THE OPPONENT'S ATTACK WILL DISPLAY
		
		//Attacks Panel just displaying for now.
		// get pokemon's attacks
		attacksPanel.setLayout(new GridLayout(2, 2));
		JButton attack1 = new JButton("Attack1");
		JButton attack2 = new JButton("Attack2");
		JButton attack3 = new JButton("Attack3");
		JButton attack4 = new JButton("Attack4");
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
			this.user.sendMessageToServer(new Attack(button.getText(), 10));
			// opponent needs to receive user's attack
			// user needs to wait for opponent to send an attack.
			// user needs to receive attack
		}
	}	

}