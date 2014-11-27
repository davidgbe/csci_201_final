import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class StorePanel extends JPanel {
	private ClientUser myClientUser;
	private JLabel myGold;
	private PokemonFrame parent;
	private JPanel mainPanel;
	
	public StorePanel(ClientUser mcu, PokemonFrame pk) {
		this.myClientUser = mcu;
		this.parent = pk;
		
		this.setLayout(new BorderLayout());
		
		this.mainPanel = new JPanel();
		JButton home = new JButton("Home");
		home.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				PokemonFrame.cl.show(PokemonFrame.outerPanel, "Main Menu");
			}
		});
		mainPanel.add(home);
		myGold = new JLabel("XXXX gold");
		
		JButton boost = new JButton("Steroids (Boost) - 15 Gold");
		JButton heal = new JButton("Morphine (Heal) - 25 Gold");
		JButton revive = new JButton("Epinephrine (Revive) - 50 Gold");
		myGold.setAlignmentX(Component.CENTER_ALIGNMENT); 

		mainPanel.add(myGold); 
		
		boost.setSize(parent.getWidth()*3/4, parent.getHeight()/5);
		boost.setMaximumSize(boost.getSize());
		boost.setAlignmentX(Component.CENTER_ALIGNMENT); 
		boost.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				myClientUser.purchaseSteroids();
			}
		});
	
		heal.setSize(parent.getWidth()*3/4, parent.getHeight()/5);
		heal.setMaximumSize(heal.getSize());
		heal.setAlignmentX(Component.CENTER_ALIGNMENT); 
		heal.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				myClientUser.purchaseMorphine();
			}
		});
		
		revive.setSize(parent.getWidth()*3/4, parent.getHeight()/5);
		revive.setMaximumSize(revive.getSize());
		revive.setAlignmentX(Component.CENTER_ALIGNMENT); 
		revive.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				myClientUser.purchaseEpinephrine();
			}
		});
		
		
		this.mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		mainPanel.add(boost); 
		mainPanel.add(heal); 
		mainPanel.add(revive); 
		
		this.add(mainPanel);
		parent.add(this);
	}
	
	public void update() {
		this.myGold.setText(myClientUser.getMoney() + " Gold");
		System.out.println("getting called");
		this.revalidate();
	}
}
