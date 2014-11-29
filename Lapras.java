import javax.swing.ImageIcon;


public class Lapras extends Pokemon {
	public Lapras(){
		super("lapras", 800, new ImageIcon("images/lapras.png"), 60.0);
		allAttacks[0] = "waterfall";
		allAttacks[1] = "surf";
		allAttacks[2] = "sheer cold";
		allAttacks[3] = "earthquake";
	}
}
