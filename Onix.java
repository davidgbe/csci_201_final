import javax.swing.ImageIcon;


public class Onix extends Pokemon{
	public Onix(){
		super("onix", 950, new ImageIcon("images/onix.png"), 55.0);
		allAttacks[0] = "headbutt";
		allAttacks[1] = "surf";
		allAttacks[2] = "earthquake";
		allAttacks[3] = "fire blast";
	}
}
