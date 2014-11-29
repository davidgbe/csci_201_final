import javax.swing.ImageIcon;


public class Rapidash extends Pokemon{
	public Rapidash(){
		super("rapidash", 500, new ImageIcon("images/rapidash.png"), 69.5);
		allAttacks[0] = "blaze kick";
		allAttacks[1] = "flamethrower";
		allAttacks[2] = "fire blast";
		allAttacks[3] = "blast burn";
	}
}
