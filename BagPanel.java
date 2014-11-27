import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class BagPanel extends JPanel {
	private ClientUser myClientUser;
	private JLabel myGoldLabel = new JLabel();
	private JLabel steroidLabel = new JLabel();
	private JLabel morphineLabel = new JLabel();
	private JLabel epiLabel = new JLabel();

	private JPanel mainPanel;
	
	public BagPanel(ClientUser mcu){
		this.myClientUser = mcu;
		
	    Box bv = Box.createVerticalBox();
	    JButton home = new JButton("Home");
	    home.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				PokemonFrame.cl.show(PokemonFrame.outerPanel, "Main Menu");
			}
	    });
	    bv.add(home);
	    
	    int myGold = 1; // get client's gold
	    String goldTxt = Integer.toString(myGold) + " Gold";
	    myGoldLabel.setText(goldTxt);
	    bv.add(myGoldLabel);

		// if client has steroids
		int numSteroids = 1; // get client's steroid num
		String steroidTxt = Integer.toString(numSteroids) + " Steroids - boost health";
		steroidLabel.setText(steroidTxt);
		
		
		// if client has morphine
		int numMorphine = 1; 
		String morphineTxt = Integer.toString(numMorphine) + " Morhphine - boost health";
		morphineLabel.setText(morphineTxt);
		
		// if client has epi
		int numEpi = 1; // get client's epi num
		String epiTxt = Integer.toString(numEpi) + " Epinephrine - boost health";
		epiLabel.setText(epiTxt);	
		
		bv.add(steroidLabel);
		bv.add(morphineLabel);
		bv.add(epiLabel);
		
		PokemonFrame.myBagPanel.add(bv);
	}
	
	public void update() {
		// updates based of client
		
		
		System.out.println("getting called");
		this.revalidate();
	}
}
