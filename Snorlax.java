import javax.swing.ImageIcon;


public class Snorlax extends Pokemon{
	public Snorlax(){
		super("snorlax", 1000, new ImageIcon("images/snorlax.png"), 100.0);
		allAttacks[0] = "headbutt";
		allAttacks[1] = "earthquake";
		allAttacks[2] = "blaze kick";
		allAttacks[3] = "surf";
	}
}
