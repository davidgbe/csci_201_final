import javax.swing.ImageIcon;


public class Scyther extends Pokemon{
	public Scyther(){
		super("scyther", 300, new ImageIcon("images/scyther.png"), 120.0);
		allAttacks[0] = "thunder punch";
		allAttacks[1] = "headbutt";
		allAttacks[2] = "surf";
		allAttacks[3] = "hydro cannon";
	}
}
