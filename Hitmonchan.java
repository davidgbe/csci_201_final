import javax.swing.ImageIcon;


public class Hitmonchan extends Pokemon {
	public Hitmonchan(){
		super("hitmonchan", 550, new ImageIcon("images/hitmonchan.png"), 90.0);
		allAttacks[0] = "blaze kick";
		allAttacks[1] = "thunder punch";
		allAttacks[2] = "headbutt";
		allAttacks[3] = "blast burn";
	}
}
