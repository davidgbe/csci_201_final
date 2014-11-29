import javax.swing.ImageIcon;


public class Charizard extends Pokemon{
	public Charizard(){
		super("charizard", 500, new ImageIcon("images/charizard.png"), 100.0);
		allAttacks[0] = "blaze kick";
		allAttacks[1] = "flamethrower";
		allAttacks[2] = "fire blast";
		allAttacks[3] = "headbutt";
	}
}
