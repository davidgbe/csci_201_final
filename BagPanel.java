import java.awt.Dimension;
import java.awt.Image;
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
	    
	    int myGold = mcu.getMoney(); // get client's gold
	    String goldTxt = Integer.toString(myGold) + " Gold";
	    myGoldLabel.setText(goldTxt);
	    ImageIcon goldIcon = new ImageIcon("images/gold.png");
		Image image4 = goldIcon.getImage(); 
		Image newimg4 = image4.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH); 
		ImageIcon actualImageForLabelGold = new ImageIcon(newimg4);
		myGoldLabel.setIcon(actualImageForLabelGold);
	    
	    
	    bv.add(myGoldLabel);

		// if client has steroids
		int numSteroids = mcu.getItemQuantity("steroids"); // get client's steroid num
		String steroidTxt = Integer.toString(numSteroids) + " Steroids - boost health";
		steroidLabel.setText(steroidTxt);
		ImageIcon roidsIcon = new ImageIcon("images/roids.gif");
		Image image = roidsIcon.getImage(); 
		Image newimg = image.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH); 
		ImageIcon actualImageForLabelRoids = new ImageIcon(newimg);
		steroidLabel.setIcon(actualImageForLabelRoids);
		
		
		// if client has morphine
		int numMorphine = mcu.getItemQuantity("morphine"); 
		String morphineTxt = Integer.toString(numMorphine) + " Morhphine - boost health";
		morphineLabel.setText(morphineTxt);
		ImageIcon morphineIcon = new ImageIcon("images/morphine.gif");
		Image image2 = morphineIcon.getImage(); 
		Image newimg2 = image2.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH); 
		ImageIcon actualImageForLabelMorphine = new ImageIcon(newimg2);
		morphineLabel.setIcon(actualImageForLabelMorphine);
		
		// if client has epi
		int numEpi = mcu.getItemQuantity("epinephrine"); // get client's epi num
		String epiTxt = Integer.toString(numEpi) + " Epinephrine - boost health";
		epiLabel.setText(epiTxt);	
		ImageIcon epiIcon = new ImageIcon("images/epi.gif");
		Image image3 = epiIcon.getImage(); 
		Image newimg3 = image3.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH); 
		ImageIcon actualImageForLabelEpi = new ImageIcon(newimg3);
		epiLabel.setIcon(actualImageForLabelEpi);
		
		bv.add(steroidLabel);
		bv.add(morphineLabel);
		bv.add(epiLabel);
		
		PokemonFrame.myBagPanel.add(bv);
	}
	
	public void update() {
		// updates based of client
		int myGold = myClientUser.getMoney(); // get client's gold
	    String goldTxt = Integer.toString(myGold) + " Gold";
	    myGoldLabel.setText(goldTxt);
	    
	    int numSteroids = myClientUser.getItemQuantity("steroids"); // get client's steroid num
		String steroidTxt = Integer.toString(numSteroids) + " Steroids - boost health";
		steroidLabel.setText(steroidTxt);
		
		int numMorphine = myClientUser.getItemQuantity("morphine"); 
		String morphineTxt = Integer.toString(numMorphine) + " Morhphine - boost health";
		morphineLabel.setText(morphineTxt);
		
		int numEpi = myClientUser.getItemQuantity("epinephrine"); // get client's epi num
		String epiTxt = Integer.toString(numEpi) + " Epinephrine - boost health";
		epiLabel.setText(epiTxt);	
		
		
		System.out.println("updating bag");
		this.revalidate();
	}
}
