import javax.swing.ImageIcon;


public class Rhydon extends Pokemon{
	public Rhydon( ){
		super("rhydon", 900, new ImageIcon("images/rhydon.png"), 90.0);
		allAttacks[0] = "earthquake";
		allAttacks[1] = "headbutt";
		allAttacks[2] = "blaze kick";
		allAttacks[3] = "thunder punch";
	}
}
