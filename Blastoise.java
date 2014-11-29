import javax.swing.ImageIcon;


public class Blastoise extends Pokemon {
	public Blastoise(){
		super("blastoise", 1100, new ImageIcon("images/blastoise.png"), 45.0);
		allAttacks[0] = "waterfall";
		allAttacks[1] = "hydro pump";
		allAttacks[2] = "sheer cold";
		allAttacks[3] = "earthquake";
	}
}
